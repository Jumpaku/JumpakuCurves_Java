/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.FuzzyCurve;
import org.jumpaku.curve.Interval;

/**
 *
 * @author tomohiko
 */

public interface Polyline extends FuzzyCurve{
    
    public static final Double DEFAULT_ABSOLUTE_ACCURACY = 1.0;
    
    public static Polyline create(FuzzyPoint... ps){
        return create(Stream.of(ps));
    }
    
    public static Polyline create(Iterable<? extends FuzzyPoint> ps){
        if(Stream.ofAll(ps).isEmpty())
            throw new IllegalArgumentException("ps must contain some elements");
        
        return new PolylineImpl(ps);

    }
    
    public static Polyline approximate(Curve curve, Integer n, Double accuracy){
        FuzzyCurve fuzzy = curve instanceof FuzzyCurve ? (FuzzyCurve)curve : FuzzyCurve.crisp(curve);
        Array<Double> ts = curve.getDomain().sample(n);
        LinkedList<FuzzyPoint> points = new LinkedList<>();
        for (Tuple2<Double, Double> seg : ts.zip(ts.tail())) {
            double a = seg._1();
            double b = seg._2();
            FuzzyPoint pa = fuzzy.evaluate(a);
            FuzzyPoint pb = fuzzy.evaluate(b);
            points.addAll(toLines(fuzzy, pa, a, pb, b, accuracy));
        }
        points.add(fuzzy.evaluate(ts.last()));
        return create(points);
    }

    public static LinkedList<FuzzyPoint> toLines(FuzzyCurve curve, FuzzyPoint pa, Double a, FuzzyPoint pb, Double b, Double accuracy){
        double c = a + (b - a)/2;
        FuzzyPoint pc = curve.evaluate(c);
        LinkedList<FuzzyPoint> points = new LinkedList<>();
        if(pc.dist(pa, pb) <= accuracy){
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

    Array<? extends FuzzyPoint> getPoints();
    
    public static final class PolylineImpl implements Polyline{

        private final Array<Double> parameters;
        
        private final Interval interval;
        
        private final Array<FuzzyPoint> points;

        public PolylineImpl(Iterable<? extends FuzzyPoint> ps) {
            this.points = Array.ofAll(ps);

            Double[] params = new Double[points.size()];
            params[0] = 0.0;
            for (int i = 1; i < points.size(); ++i) {
                params[i] = params[i-1] + points.get(i).dist(points.get(i-1));
            }

            this.parameters = Array.of(params);
            this.interval = Interval.of(0.0, parameters.last());
        }
        
        @Override public Array<FuzzyPoint> getPoints(){
            return points;
        }

        @Override public Interval getDomain() {
            return interval;
        }
        
        @Override public String toString(){
            return toJson(this);
        }
        
        @Override public FuzzyPoint evaluate(Double s) {
            if(!getDomain().includes(s))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + s);

            Array<FuzzyPoint> ps = getPoints();
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
        public Array<FuzzyPoint> evaluateAllByArcLengthParams(Integer n) {
            Array<FuzzyPoint> ps = getPoints();
            if(points.isSingleValued()){
                return Stream.continually(ps.head()).take(n).toArray();
            }

            Array<Double> ss = getDomain().sample(n);
            Stream<FuzzyPoint> evaluated = Stream.of();
            int indexP = 1;
            for(Double s : ss.init()){
                indexP = parameters.indexWhere(param -> s < param, indexP);
                double r = (s - parameters.get(indexP-1))/(parameters.get(indexP)-parameters.get(indexP-1));
                evaluated = evaluated.append(ps.get(indexP-1).divide(r, ps.get(indexP)));
            }
            evaluated = evaluated.append(ps.last());

            return evaluated.toArray();
        }
    }
}
