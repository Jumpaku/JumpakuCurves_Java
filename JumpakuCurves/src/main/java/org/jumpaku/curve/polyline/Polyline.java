/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.*;

import java.util.ArrayList;

/**
 *
 * @author jumpaku
 */

public final class Polyline implements FuzzyCurve, Reversible<Curve>, Restrictable<Curve> {
    
    public static Polyline create(Point... ps){
        return create(Stream.of(ps));
    }
    
    public static Polyline create(Iterable<? extends Point> points){
        Array<Point> ps = Array.ofAll(points);
        if(ps.isEmpty()) {
            throw new IllegalArgumentException("ps must contain some elements");
        }

        double[] ss = new double[ps.size()];
        ss[0] = 0;
        for (int i = 1; i < ps.size(); i++) {
            ss[i] = ss[i-1] + ps.get(i-1).toCrisp().dist(ps.get(i).toCrisp());
        }
        Array<Double> params = Array.ofAll(ss);

        return new Polyline(Interval.of(0.0, params.sum().doubleValue()), params, ps);
    }
    
    public static Polyline approximate(Curve curve, Integer n){
        return create(curve.getDomain().sample(n).map(curve));
    }

    public static String toJson(Polyline polyline){
        return JsonPolyline.CONVERTER.toJson(polyline);
    }
    
    public static Option<Polyline> fromJson(String json){
        return JsonPolyline.CONVERTER.fromJson(json);
    }

    private final Array<Point> points;

    private final Array<Double> parameters;

    private final Interval domain;

    public Polyline(Interval domain, Array<Double> parameters, Array<Point> ps) {
        this.points = Array.ofAll(ps);
        this.parameters = parameters;
        this.domain = domain;
    }

    @Override
    public String toString(){
        return toJson(this);
    }

    @Override
    public Point evaluate(Double s) {
        if(!getDomain().includes(s)) {
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + s);
        }

        int i = parameters.indexWhere(param -> s < param);
        return getPoints().get(i-1).divide((s - parameters.get(i-1))/(parameters.get(i) - parameters.get(i-1)), getPoints().get(i));
    }

    @Override
    public Array<Point> evaluateAllArcLength(Integer n) {
        Array<Point> ps = getPoints();
        if(ps.isSingleValued()){
            return Stream.continually(ps.head()).take(n).toArray();
        }

        Array<Double> ss = getDomain().sample(n);
        ArrayList<Point> evaluated = new ArrayList<>(ss.size());
        int i = 1;
        for(Double s : ss.init()){
            i = parameters.indexWhere(param -> s < param, i);
            double r = (s - parameters.get(i-1))/(parameters.get(i)-parameters.get(i-1));
            evaluated.add(ps.get(i-1).divide(r, ps.get(i)));
        }

        return Array.ofAll(evaluated);
    }

    @Override
    public Polyline reverse() {
        return create(getPoints().reverse());
    }

    @Override
    public Polyline restrict(Interval i) {
        return restrict(i.getBegin(), i.getEnd());
    }

    @Override
    public Polyline restrict(Double begin, Double end){
        if(!getDomain().includes(Interval.of(begin, end))) {
            throw new IllegalArgumentException("Interval i = [begin, end] must be a subset of this domain");
        }

        int ai = parameters.indexWhere(param -> begin < param);
        int bi = parameters.indexWhere(param -> end <= param, ai);

        Point ap = evaluate(begin);
        Point bp = evaluate(end);

        return new Polyline(Interval.of(0.0, end - begin),
                parameters.subSequence(ai, bi).append(end).prepend(begin).map(d->d-begin),
                getPoints().subSequence(ai, bi).prepend(ap).append(bp));
    }

    public Array<Point> getPoints(){
            return points;
        }

    public Interval getDomain() {
            return domain;
        }

    public Tuple2<Polyline, Polyline> subdivide(Double s){
        int i = parameters.indexWhere(param -> s < param);
        Point p = evaluate(s);
        return Tuple.of(new Polyline(Interval.of(0.0, s), parameters.take(i).append(s), getPoints().take(i).append(p)),
                new Polyline(Interval.of(0.0, getDomain().getEnd()-s), parameters.drop(i).map(d->d-s).prepend(0.0), getPoints().drop(i).prepend(p)));
    }
}
