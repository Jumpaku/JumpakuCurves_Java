/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import java.util.LinkedList;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.FuzzyCurve;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Reversible;

/**
 *
 * @author jumpaku
 */

public final class Polyline implements FuzzyCurve, Reversible<Polyline> {
    
    public static final Double DEFAULT_ABSOLUTE_ACCURACY = 1.0;
    
    public static Polyline create(Point... ps){
        return create(Stream.of(ps));
    }
    
    public static Polyline create(Iterable<? extends Point> ps){
        if(Stream.ofAll(ps).isEmpty()) {
            throw new IllegalArgumentException("ps must contain some elements");
        }
        
        return new Polyline(ps);

    }
    
    public static Polyline approximate(Curve curve, Integer n, Double accuracy){
        Array<Double> ts = curve.getDomain().sample(n);
        LinkedList<Point> points = new LinkedList<>();
        for (Tuple2<Double, Double> seg : ts.zip(ts.tail())) {
            double a = seg._1();
            double b = seg._2();
            Point pa = curve.evaluate(a);
            Point pb = curve.evaluate(b);
            points.addAll(toLines(curve, pa, a, pb, b, accuracy));
        }
        points.add(curve.evaluate(ts.last()));
        return create(points);
    }

    public static LinkedList<Point> toLines(Curve curve, Point pa, Double a, Point pb, Double b, Double accuracy){
        double c = a + (b - a)/2;
        Point pc = curve.evaluate(c);
        LinkedList<Point> points = new LinkedList<>();
        if(pc.toCrisp().dist(pa.toCrisp(), pb.toCrisp()) <= accuracy){
            points.add(pa);
            points.add(pb);
            return points;
        }
        else{
            points.addAll(toLines(curve, pa, a, pc, c, accuracy));
            points.removeLast();
            points.addAll(toLines(curve, pc, c, pb, b, accuracy));
            return points;
        }
    }

    public static String toJson(Polyline polyline){
        return JsonPolyline.CONVERTER.toJson(polyline);
    }
    
    public static Option<Polyline> fromJson(String json){
        return JsonPolyline.CONVERTER.fromJson(json);
    }

    private final Array<Double> parameters;

    private final Interval interval;

    private final Array<Point> points;

    public Polyline(Iterable<? extends Point> ps) {
        this.points = Array.ofAll(ps);

        Double[] params = new Double[points.size()];
        params[0] = 0.0;
        for (int i = 1; i < points.size(); ++i) {
            params[i] = params[i-1] + points.get(i).toCrisp().dist(points.get(i-1).toCrisp());
        }

        this.parameters = Array.of(params);
        this.interval = Interval.of(0.0, parameters.last());
    }

    public Array<Point> getPoints(){
            return points;
        }

    public Interval getDomain() {
            return interval;
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

        Array<Point> ps = getPoints();
        int a = 0;
        int b = parameters.size()-1;
        while(b-a > 1){
            int c = a + FastMath.floorDiv(b - a, 2);
            if(s < parameters.get(c)){
                b = c;
            }
            else if(parameters.get(c) <= s){
                a = c;
            }
        }

        double begin = parameters.get(a);
        double end = parameters.get(b);

        return ps.get(a).divide((s - begin)/(end-begin), ps.get(b));
    }

    @Override
    public Array<Point> evaluateAllByArcLengthParams(Integer n) {
        Array<Point> ps = getPoints();
        if(points.isSingleValued()){
            return Stream.continually(ps.head()).take(n).toArray();
        }

        Array<Double> ss = getDomain().sample(n);
        Stream<Point> evaluated = Stream.of();
        int indexP = 1;
        for(Double s : ss.init()){
            indexP = parameters.indexWhere(param -> s < param, indexP);
            double r = (s - parameters.get(indexP-1))/(parameters.get(indexP)-parameters.get(indexP-1));
            evaluated = evaluated.append(ps.get(indexP-1).divide(r, ps.get(indexP)));
        }
        evaluated = evaluated.append(ps.last());

        return evaluated.toArray();
    }

    @Override
    public Polyline reverse() {
        return create(getPoints().reverse());
    }
}
