/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.FuzzyCurve;
import org.jumpaku.curve.Restrictable;

/**
 *
 * @author Jumpaku
 */
public interface Bezier extends FuzzyCurve, Differentiable{
    
    public static class Decasteljau extends AbstractBezier{
        public Decasteljau(Array<? extends FuzzyPoint> cps, Interval interval) {
            super(cps, interval);
        }

        @Override public FuzzyPoint evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            Array<FuzzyPoint> cps = getControlPoints();
            while(cps.size() > 1){
                cps = decasteljau(t, cps);
            }
            return cps.head();
        }
    }

    public static Bezier create(Interval domain, Iterable<? extends org.jumpaku.affine.Point> controlPoints){
        Array<FuzzyPoint> cps = Stream.ofAll(controlPoints)
                .map(p -> p instanceof FuzzyPoint ? (FuzzyPoint)p : FuzzyPoint.crisp(p))
                .toArray();
        
        if(cps.isEmpty())
            throw new IllegalArgumentException("control points are empty");
        
        if(cps.exists(p -> p == null))
            throw new IllegalArgumentException("control points contain null");
     
        return new Decasteljau(cps, domain);
    }
    
    static Bezier create(Interval domain, org.jumpaku.affine.Point... controlPoints){
        return create(domain, Array.of(controlPoints));
    }
    
    static Bezier create(Iterable<? extends org.jumpaku.affine.Point> controlPoints){
        return create(Interval.ZERO_ONE, controlPoints);
    }

    static Bezier create(org.jumpaku.affine.Point... controlPoints){
        return create(Interval.ZERO_ONE, controlPoints);
    }

    public static String toJson(Bezier bezier){
        return JsonBezier.CONVERTER.toJson(bezier);
    }
    
    public static Option<Bezier> fromJson(String json){
        return JsonBezier.CONVERTER.fromJson(json);
    }
    
    @Override Interval getDomain();
    
    @Override FuzzyPoint evaluate(Double t);

    @Override BezierDerivative differentiate();

    @Override default Bezier restrict(Double begin, Double end){
        return restrict(Interval.of(begin, end));
    }

    @Override Bezier restrict(Interval i);

     Bezier reverse();

    Array<FuzzyPoint> getControlPoints();

    Integer getDegree();
    
    Bezier elevate();
    
    Bezier reduce();
    
    Tuple2<Bezier, Bezier> subdivide(Double t);
}
