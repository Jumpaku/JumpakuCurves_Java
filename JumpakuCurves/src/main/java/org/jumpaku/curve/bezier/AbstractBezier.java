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
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Interval;

/**
 *
 * @author Jumpaku
 */
public abstract class AbstractBezier implements Bezier{

    private final Array<org.jumpaku.affine.Point> controlPoints;
    
    private final Interval domain;

    public AbstractBezier(Iterable<? extends org.jumpaku.affine.Point> controlPoints, Interval domain) {
        this.controlPoints = Array.ofAll(controlPoints);
        this.domain = domain;
    }
        
    public static Array<org.jumpaku.affine.Point> decasteljau(Double t, Array<org.jumpaku.affine.Point> cps){
        return cps.zipWith(cps.tail(), (p0, p1) -> p0.divide(t, p1));
    }

    @Override public abstract org.jumpaku.affine.Point evaluate(Double t);

    @Override
    public final org.jumpaku.affine.Point apply(Double t) {
        return Bezier.super.apply(t); 
    }

    @Override public final Interval getDomain() {
        return domain;
    }

    @Override public final Integer getDegree() {
        return getControlPoints().size() - 1;
    }
    
    @Override public final Vector differentiate(Double t) {
        return Bezier.super.differentiate(t);
    }

    @Override public final BezierDerivative differentiate() {
        Array<? extends org.jumpaku.affine.Point> cp = getControlPoints();
        Array<Vector> vs = cp.zipWith(cp.tail(), (pre, post) -> post.diff(pre).scale(getDegree().doubleValue()));
        return BezierDerivative.create(vs, getDomain());
    }

    @Override public final Bezier restrict(Interval i) {
        if(!getDomain().includes(i))
            throw new IllegalArgumentException("Interval i must be a subset of this domain");
        
        return Bezier.create(getControlPoints(), i);
    }

    @Override public final Bezier restrict(Double begin, Double end) {
        return Bezier.super.restrict(begin, end);
    }
    
    @Override public final Bezier reverse() {
        return Bezier.create(getControlPoints().reverse(), getDomain());
    }

    @Override public final Array<org.jumpaku.affine.Point> getControlPoints() {
        return controlPoints;
    }

    @Override public final Bezier elevate(){
        return Bezier.create(createElevatedControlPoints(), getDomain());
    }
    
    private Array<org.jumpaku.affine.Point> createElevatedControlPoints() {
        Integer n = getDegree();
        Array<org.jumpaku.affine.Point> cp = getControlPoints();
        
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
        
        return Bezier.create(createReducedControlPoints(), getDomain());
    }
    
    private Array<org.jumpaku.affine.Point> createReducedControlPoints() {
        int n = getControlPoints().size() - 1;
        int m = n + 1;
            
        Array<? extends org.jumpaku.affine.Point> cp = getControlPoints();
        
        if(m == 2){
            return Array.of(cp.get(0).divide(0.5, cp.get(1)));
        }
        else if(m%2==0){
            int r = (m-2)/2;
            
            Array<org.jumpaku.affine.Point> first = Stream.iterate(Tuple.of(cp.head(), 0), 
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1/(1.0-qi._2()/n), qi._1()),
                                    qi._2()+1))
                    .take(r).map(Tuple2::_1).toArray();
                    
            Array<org.jumpaku.affine.Point> second = Stream.iterate(Tuple.of(cp.last(), n-1),
                            qi -> Tuple.of(
                                    cp.get(qi._2()).divide(1-1.0/qi._2()/n, qi._1()),
                                    qi._2()-1))
                    .take(r).map(Tuple2::_1).reverse().toArray();
            
            double al = r/(m-1.0);
            org.jumpaku.affine.Point pl = cp.get(r).divide(-al/(1.0-al), first.last());
            double ar = (r+1)/(m-1.0);
            org.jumpaku.affine.Point pr = cp.get(r+1).divide((-1.0+ar)/ar, second.head());
            Stream<org.jumpaku.affine.Point> middle = Stream.of(pl.divide(0.5, pr));
            
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
    public final Tuple2<? extends Bezier, ? extends Bezier> subdivide(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain().toString() + ", but t = ");
        return createDividedControlPointsArray(t)
                .map(cp -> Bezier.create(cp, getDomain()), cp -> Bezier.create(cp, getDomain()));
    }
    
    private Tuple2<Array<org.jumpaku.affine.Point>, Array<org.jumpaku.affine.Point>> createDividedControlPointsArray(Double t) {
        Array<org.jumpaku.affine.Point> cp = getControlPoints();
        List<org.jumpaku.affine.Point> first = List.of(cp.head());
        List<org.jumpaku.affine.Point> second = List.of(cp.last());

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
