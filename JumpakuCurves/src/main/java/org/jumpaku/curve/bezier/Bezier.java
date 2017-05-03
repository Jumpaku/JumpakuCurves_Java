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
import javaslang.control.Option;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.*;

import java.util.Objects;

/**
 *
 * @author Jumpaku
 */
public final class Bezier implements FuzzyCurve, Differentiable, Reversible<Curve>, Restrictable<Curve> {
    
    public static Bezier create(Iterable<? extends Point> controlPoints){
        Array<? extends Point> cps = Stream.ofAll(controlPoints).toArray();
        
        if(cps.isEmpty()) {
            throw new IllegalArgumentException("control points are empty");
        }
        
        if(cps.exists(Objects::isNull)) {
            throw new IllegalArgumentException("control points contain null");
        }
     
        return new Bezier(cps);
    }
    
    public static Bezier create(Point... controlPoints){
        return create(Stream.of(controlPoints));
    }

    public static String toJson(Bezier bezier){
        return JsonBezier.CONVERTER.toJson(bezier);
    }
    
    public static Option<Bezier> fromJson(String json){
        return JsonBezier.CONVERTER.fromJson(json);
    }

    public static Array<Point> decasteljau(Double t, Array<Point> cps){
        return cps.zipWith(cps.tail(), (p0, p1) -> p0.divide(t, p1));
    }

    private final Array<Point> controlPoints;

    public Bezier(Iterable<? extends Point> controlPoints) {
        this.controlPoints = Array.ofAll(controlPoints);
    }


    @Override
    public String toString(){
        return Bezier.toJson(this);
    }

    @Override
    public Point evaluate(Double t) {
        if(!getDomain().includes(t)) {
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
        }

        Array<Point> cps = getControlPoints();
        while(cps.size() > 1){
            cps = decasteljau(t, cps);
        }
        return cps.head();
    }

    @Override
    public Interval getDomain() {
        return Interval.ZERO_ONE;
    }

    @Override
    public BezierDerivative differentiate() {
        Array<Point.Crisp> cp = getControlPoints().map(Point::toCrisp);
        Array<Vector.Crisp> vs = cp.zipWith(cp.tail(), (pre, post) -> post.diff(pre).scale(getDegree().doubleValue()));
        return BezierDerivative.create(vs);
    }

    @Override
    public Bezier restrict(Interval i) {
        return restrict(i.getBegin(), i.getEnd());
    }

    @Override
    public Bezier restrict(Double begin, Double end){
        if(!getDomain().includes(Interval.of(begin, end))) {
            throw new IllegalArgumentException("Interval i must be a subset of this domain");
        }

        return subdivide(end)._1().subdivide(begin/end)._2();
    }

    @Override
    public Bezier reverse() {
        return Bezier.create(getControlPoints().reverse());
    }

    public Array<Point> getControlPoints() {
        return controlPoints;
    }

    public Bezier elevate(){
        return Bezier.create(createElevatedControlPoints());
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

    public Bezier reduce(){
        if(getDegree() < 1) {
            throw new IllegalStateException("degree is too small");
        }

        return Bezier.create(createReducedControlPoints());
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

    public Tuple2<Bezier, Bezier> subdivide(Double t) {
        if(!getDomain().includes(t)) {
            throw new IllegalArgumentException("t must be in " + getDomain().toString() + ", but t = ");
        }

        return createDividedControlPointsArray(t)
                .map(Bezier::create, Bezier::create);
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

    public Integer getDegree(){
        return getControlPoints().size() - 1;
    }
}
