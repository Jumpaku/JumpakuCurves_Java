/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.List;
import javaslang.collection.Stream;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.bezier.BezierDerivative;

/**
 *
 * @author jumpaku
 */
public class Decasteljau implements RationalBezier{

    private final Array<WeightedPoint> controlPoints;
    
    private final Interval domain;

    public Decasteljau(Interval domain, Iterable<? extends WeightedPoint> controlPoints) {
        this.controlPoints = Array.ofAll(controlPoints);
        this.domain = domain;
    }
    
    @Override public FuzzyPoint evaluate(Double t){
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
        
        Array<WeightedPoint> wcps = getWeightedControlPoints();
        while(wcps.size() > 1){
            wcps = decasteljau(t, wcps);
        }
        return wcps.head().getPoint();
    }

    @Override public final Interval getDomain() {
        return domain;
    }
    
    public static Array<WeightedPoint> decasteljau(Double t,  Array<WeightedPoint> wcps){
        return wcps.zipWith(wcps.tail(), (wcp0, wcp1)->wcp0.divide(t, wcp1));
    }
    
    public static Double weightBezier(Double t, Array<Double> ws){
        while(ws.size() > 1){
            ws = ws.zipWith(ws.tail(), (w0, w1) -> (1-t)*w0 + t*w1);
        }
        return ws.head();
    }
    
    @Override public final Derivative differentiate() {
        int n = getDegree();
        Array<Double> ws = getWeights();
        Array<Double> dws = getWeights().zipWith(ws.tail(), (a, b)->n*(b-a));
        BezierDerivative dp = BezierDerivative.create(getDomain(), 
                getWeightedControlPoints().map(wp -> wp.getPoint().toVector().scale(wp.getW())));
        
        return new Derivative() {
            @Override public Vector evaluate(Double t) {
                if(!getDomain().includes(t))
                    throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
            
                double wt = weightBezier(t, ws);
                double dwt = weightBezier(t, dws);
                Vector dpt = dp.evaluate(t);
                Vector rt = Decasteljau.this.evaluate(t).toVector();
                
                return dpt.sub(dwt, rt).scale(1/wt);
            }

            @Override public Interval getDomain() {
                return Decasteljau.this.getDomain();
            }
        };
    }

    @Override public final RationalBezier restrict(Interval i) {
        if(!getDomain().includes(i))
            throw new IllegalArgumentException("Interval i must be a subset of this domain");
        
        return RationalBezier.create(i, getWeightedControlPoints());
    }

    @Override public final RationalBezier restrict(Double begin, Double end) {
        return RationalBezier.super.restrict(begin, end);
    }
    
    @Override public final RationalBezier reverse() {
        return RationalBezier.create(Interval.of(1-getDomain().getEnd(), 1-getDomain().getBegin()),
                getWeightedControlPoints().reverse());
    }

    @Override public final Array<WeightedPoint> getWeightedControlPoints() {
        return controlPoints;
    }

    @Override public final RationalBezier elevate(){
        return RationalBezier.create(getDomain(), createElevatedControlPoints());
    }
    
    private Array<WeightedPoint> createElevatedControlPoints() {
        Integer n = getDegree();
        Array<WeightedPoint> cp = getWeightedControlPoints();
        
        return Stream.rangeClosed(0, n+1)
                .map(i ->
                        i == 0   ? cp.head() :
                        i == n+1 ? cp.last() :
                                   cp.get(i).divide(i/(double)(n+1), cp.get(i-1)))
                .toArray();
    }

    @Override public final RationalBezier reduce(){
        if(getDegree() < 1)
            throw new IllegalStateException("degree is too small");
        
        return RationalBezier.create(getDomain(), createReducedControlPoints());
    }
    
    private Array<WeightedPoint> createReducedControlPoints() {
        int n = getControlPoints().size() - 1;
        int m = n + 1;
            
        Array<WeightedPoint> cp = getWeightedControlPoints();
        
        if(m == 2){
            return Array.of(cp.get(0).divide(0.5, cp.get(1)));
        }
        else if(m%2==0){
            int r = (m-2)/2;
            
            Array<WeightedPoint> first = Stream.iterate(Tuple.of(cp.head(), 0), 
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1/(1.0-qi._2()/n), qi._1()),
                                    qi._2()+1))
                    .take(r).map(Tuple2::_1).toArray();
                    
            Array<WeightedPoint> second = Stream.iterate(Tuple.of(cp.last(), n-1),
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1.0/qi._2()/n, qi._1()),
                                    qi._2()-1))
                    .take(r).map(Tuple2::_1).reverse().toArray();
            
            double al = r/(m-1.0);
            WeightedPoint pl = cp.get(r).divide(-al/(1.0-al), first.last());
            double ar = (r+1)/(m-1.0);
            WeightedPoint pr = cp.get(r+1).divide((-1.0+ar)/ar, second.head());
            Stream<WeightedPoint> middle = Stream.of(pl.divide(0.5, pr));
            
            return Stream.concat(first, middle, second).toArray();
        }
        else{
            int r = (m-3)/2;
            
            return Stream.concat(
                    Stream.iterate(Tuple.of(cp.head(), 0), 
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1/(1.0-qi._2()/n), qi._1()),
                                    qi._2()+1))
                            .take(r+1),
                    Stream.iterate(Tuple.of(cp.last(), n-1),
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1.0/qi._2()/n, qi._1()),
                                    qi._2()-1))
                            .take(r+1)
                            .reverse())
                    .map(Tuple2::_1)
                    .toArray();
        }
    }
    
    @Override
    public final Tuple2<RationalBezier, RationalBezier> subdivide(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain().toString() + ", but t = ");
        
        return createDividedControlPointsArray(t)
                .map(cp -> RationalBezier.create(Interval.of(getDomain().getBegin()*t, 1.0), cp),
                        cp -> RationalBezier.create(Interval.of(0.0, getDomain().getEnd()*t), cp));
    }
    
    private Tuple2<Array<WeightedPoint>, Array<WeightedPoint>> createDividedControlPointsArray(Double t) {
        Array<WeightedPoint> cp = getWeightedControlPoints();
        List<WeightedPoint> first = List.of(cp.head());
        List<WeightedPoint> second = List.of(cp.last());

        while(cp.size() > 1){
            cp = decasteljau(t, cp);
            first = first.prepend(cp.head());
            second = second.prepend(cp.last());
        }
        
        return Tuple.of(first.reverse().toArray(), second.toArray());
    }    
    
    @Override final public String toString(){
        return RationalBezier.toJson(this);
    }
}
