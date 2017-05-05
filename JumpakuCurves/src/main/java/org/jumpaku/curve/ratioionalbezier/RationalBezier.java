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
import javaslang.control.Option;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.affine.WeightedPoint;
import org.jumpaku.curve.*;
import org.jumpaku.curve.bezier.Bezier;
import org.jumpaku.curve.bezier.BezierDerivative;

/**
 *
 * @author jumpaku
 */
public final class RationalBezier implements FuzzyCurve, Differentiable, Reversible<Curve>, Restrictable<Curve> {

    public static Array<WeightedPoint> decasteljau(Double t,  Array<WeightedPoint> wcps){
        return wcps.zipWith(wcps.tail(), (wcp0, wcp1)->wcp0.divide(t, wcp1));
    }

    public static Double weightBezier(Double t, Array<Double> weights){
        Array<Double> ws = weights;
        while(ws.size() > 1){
            ws = ws.zipWith(ws.tail(), (w0, w1) -> (1-t)*w0 + t*w1);
        }
        return ws.head();
    }

    public static RationalBezier create(Iterable<WeightedPoint> weightedControlPoints){
        return new RationalBezier(weightedControlPoints);
    }

    public static RationalBezier create(WeightedPoint... weightedControlPoints){
        return create(Stream.of(weightedControlPoints));
    }

    public static RationalBezier create(Iterable<? extends Point> controlPoints, Iterable<Double> weights){
        Array<Double> ws = Array.ofAll(weights);
        Array<Point> cps = Array.ofAll(controlPoints);

        if(ws.size() != cps.size()) {
            throw new IllegalArgumentException("size closed weights must equal to size closed controlPoints,"
                    + " but size closed weights = " + ws.size() + ", size closed controlPoints = " + cps.size());
        }

        if(ws.isEmpty() || cps.isEmpty()) {
            throw new IllegalArgumentException("weights and controlPoints mustn't be empty");
        }

        return create(cps.zipWith(ws, WeightedPoint::new));
    }

    public static RationalBezier fromBezier(Bezier bezier){
        return create(bezier.getControlPoints(), Stream.fill(bezier.getDegree()+1, ()->1.0));
    }

    public static String toJson(RationalBezier bezier){
        return JsonRationalBezier.CONVERTER.toJson(bezier);
    }

    public static Option<RationalBezier> fromJson(String json){
        return JsonRationalBezier.CONVERTER.fromJson(json);
    }

    private final Array<WeightedPoint> controlPoints;
    
    public RationalBezier(Iterable<? extends WeightedPoint> controlPoints) {
        this.controlPoints = Array.ofAll(controlPoints);
    }
    
    @Override
    public Point evaluate(Double t){
        if(!getDomain().includes(t)) {
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
        }
        
        Array<WeightedPoint> wcps = getWeightedControlPoints();
        while(wcps.size() > 1){
            wcps = decasteljau(t, wcps);
        }
        return wcps.head().getPoint();
    }

    @Override
    public Interval getDomain() {
        return Interval.ZERO_ONE;
    }
    
    @Override
    public Derivative differentiate() {
        int n = getDegree();
        Array<Double> ws = getWeights();
        Array<Double> dws = ws.zipWith(ws.tail(), (a, b)->n*(b-a));
        BezierDerivative dp = BezierDerivative.create(
                getWeightedControlPoints().map(
                        wp -> wp.getPoint().toVector().scale(wp.getWeight()).toCrisp()))
                .differentiate();
        
        return new Derivative() {
            @Override
            public Vector.Crisp evaluate(Double t) {
                if(!getDomain().includes(t)) {
                    throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
                }
            
                double wt = weightBezier(t, ws);
                double dwt = weightBezier(t, dws);
                Vector dpt = dp.evaluate(t);
                Vector rt = RationalBezier.this.evaluate(t).toVector();
                
                return dpt.sub(dwt, rt).scale(1/wt).toCrisp();
            }

            @Override
            public Interval getDomain() {
                return RationalBezier.this.getDomain();
            }
        };
    }

    @Override
    public RationalBezier restrict(Interval i) {
        return restrict(i.getBegin(), i.getEnd());
    }

    @Override
    public RationalBezier restrict(Double begin, Double end){
        if(!getDomain().includes(Interval.closed(begin, end))) {
            throw new IllegalArgumentException("Interval i must be a subset closed this domain");
        }

        return subdivide(end)._1().subdivide(begin/end)._2();
    }

    
    @Override
    public RationalBezier reverse() {
        return RationalBezier.create(getWeightedControlPoints().reverse());
    }

    Array<Point> getControlPoints(){
        return getWeightedControlPoints().map(WeightedPoint::getPoint);
    }

    Array<Double> getWeights(){
        return getWeightedControlPoints().map(WeightedPoint::getWeight);
    }

    Integer getDegree(){
        return getWeightedControlPoints().size() - 1;
    }

    public Array<WeightedPoint> getWeightedControlPoints() {
        return controlPoints;
    }

    public RationalBezier elevate(){
        return RationalBezier.create(createElevatedControlPoints());
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

    public RationalBezier reduce(){
        if(getDegree() < 1) {
            throw new IllegalStateException("degree is too small");
        }
        
        return RationalBezier.create(createReducedControlPoints());
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
    
    public Tuple2<RationalBezier, RationalBezier> subdivide(Double t) {
        if(!getDomain().includes(t)) {
            throw new IllegalArgumentException("t must be in " + getDomain().toString() + ", but t = ");
        }
        
        return createDividedControlPointsArray(t)
                .map(RationalBezier::create, RationalBezier::create);
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

    @Override
    public String toString(){
        return RationalBezier.toJson(this);
    }
}
