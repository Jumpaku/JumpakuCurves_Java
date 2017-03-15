/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.List;
import javaslang.collection.Stream;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Interval;

/**
 *
 * @author Jumpaku
 */
public final class Decasteljau implements Bezier{

    private final Array<Point> controlPoints;
    
    private final Interval domain;

    public Decasteljau(Iterable<? extends Point> controlPoints, Interval domain) {
        this.controlPoints = Array.ofAll(controlPoints);
        this.domain = domain;
    }
    
    public static Array<Point> decasteljau(Double t, Array<Point> cps){
        return cps.zipWith(cps.tail(), (p0, p1) -> p0.divide(t, p1));
    }

    @Override public final Point evaluate(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

        Array<Point> cps = getControlPoints();
        while(cps.size() > 1){
            cps = decasteljau(t, cps);
        }
        return cps.head();
    }

    @Override public final Interval getDomain() {
        return domain;
    }
    
    @Override public final Vector.Crisp differentiate(Double t) {
        return Bezier.super.differentiate(t);
    }

    @Override public final BezierDerivative differentiate() {
        Array<? extends Point.Crisp> cp = getControlPoints().map(Point::toCrisp);
        Array<Vector.Crisp> vs = cp.zipWith(cp.tail(), (pre, post) -> post.diff(pre).scale(getDegree().doubleValue()));
        return BezierDerivative.create(getDomain(), vs);
    }

    @Override public final Bezier restrict(Interval i) {
        if(!getDomain().includes(i))
            throw new IllegalArgumentException("Interval i must be a subset of this domain");
        
        return Bezier.create(i, getControlPoints());
    }

    @Override public final Bezier restrict(Double begin, Double end) {
        return Bezier.super.restrict(begin, end);
    }
    
    @Override public final Bezier reverse() {
        return Bezier.create(Interval.of(1-getDomain().getEnd(), 1-getDomain().getBegin()),
                getControlPoints().reverse());
    }

    @Override public final Array<Point> getControlPoints() {
        return controlPoints;
    }

    @Override public final Bezier elevate(){
        return Bezier.create(getDomain(), createElevatedControlPoints());
    }
    
    private Array<Point> createElevatedControlPoints() {
        Integer n = getDegree();
        Array<Point> cp = getControlPoints();
        
        return Stream.rangeClosed(0, n+1)
                .map(i ->
                        i == 0   ? cp.head() :
                        i == n+1 ? cp.last() :
                                   cp.get(i).divide(i/(double)(n+1), cp.get(i-1)))
                .toArray();
    }

    @Override public final Bezier reduce(){
        if(getDegree() < 1)
            throw new IllegalStateException("degree is too small");
        
        return Bezier.create(getDomain(), createReducedControlPoints());
    }
    
    private Array<Point> createReducedControlPoints() {
        int n = getControlPoints().size() - 1;
        int m = n + 1;
            
        Array<Point> cp = getControlPoints();
        
        if(m == 2){
            return Array.of(cp.get(0).divide(0.5, cp.get(1)));
        }
        else if(m%2==0){
            int r = (m-2)/2;
            
            Array<Point> first = Stream.iterate(Tuple.of(cp.head(), 0), 
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1/(1.0-qi._2()/n), qi._1()),
                                    qi._2()+1))
                    .take(r).map(Tuple2::_1).toArray();
                    
            Array<Point> second = Stream.iterate(Tuple.of(cp.last(), n-1),
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1.0/qi._2()/n, qi._1()),
                                    qi._2()-1))
                    .take(r).map(Tuple2::_1).reverse().toArray();
            
            double al = r/(m-1.0);
            Point pl = cp.get(r).divide(-al/(1.0-al), first.last());
            double ar = (r+1)/(m-1.0);
            Point pr = cp.get(r+1).divide((-1.0+ar)/ar, second.head());
            Stream<Point> middle = Stream.of(pl.divide(0.5, pr));
            
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
    public final Tuple2<Bezier, Bezier> subdivide(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain().toString() + ", but t = ");
        
        return createDividedControlPointsArray(t)
                .map(cp -> Bezier.create(Interval.of(getDomain().getBegin()*t, 1.0), cp),
                        cp -> Bezier.create(Interval.of(0.0, getDomain().getEnd()*t), cp));
    }
    
    private Tuple2<Array<Point>, Array<Point>> createDividedControlPointsArray(Double t) {
        Array<Point> cp = getControlPoints();
        List<Point> first = List.of(cp.head());
        List<Point> second = List.of(cp.last());

        while(cp.size() > 1){
            cp = decasteljau(t, cp);
            first = first.prepend(cp.head());
            second = second.prepend(cp.last());
        }
        
        return Tuple.of(first.reverse().toArray(), second.toArray());
    }    
    
    @Override final public String toString(){
        return Bezier.toJson(this);
    }
}
