/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.control.Option;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.DefinedOnInterval;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Differentiable;

/**
 *
 * @author Jumpaku
 */
public interface Bezier extends Curve, Differentiable, DefinedOnInterval<Bezier>{
    
    public static class Point extends AbstractBezier{

        public Point(org.jumpaku.affine.Point cp, Interval interval) {
            super(Array.of(cp), interval);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return cp.get(0);
        }
    }
    
    public static class LineSegment extends AbstractBezier{

        public LineSegment(org.jumpaku.affine.Point cp0, org.jumpaku.affine.Point cp1, Interval interval) {
            super(Array.of(cp0, cp1), interval);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return cp.get(0).divide(t, cp.get(1));
        }
    }
    
    public static class Quadratic extends AbstractBezier{

        public Quadratic(org.jumpaku.affine.Point cp0, org.jumpaku.affine.Point cp1, org.jumpaku.affine.Point cp2, Interval interval) {
            super(Array.of(cp0, cp1, cp2), interval);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return org.jumpaku.affine.Point.of(cp.get(0).getVector().scale((1-t)*(1-t))
                    .add(2*t*(1-t), cp.get(1).getVector())
                    .add(t*t, cp.get(2).getVector()));
        }
    }

    public static class Cubic extends AbstractBezier{

        public Cubic(org.jumpaku.affine.Point cp0, org.jumpaku.affine.Point cp1, org.jumpaku.affine.Point cp2, org.jumpaku.affine.Point cp3, Interval interval) {
            super(Array.of(cp0, cp1, cp2, cp3), interval);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return org.jumpaku.affine.Point.of(cp.get(0).getVector().scale((1-t)*(1-t)*(1-t))
                    .add(3*t*(1-t)*(1-t), cp.get(1).getVector())
                    .add(3*t*t*(1-t), cp.get(2).getVector())
                    .add(t*t*t, cp.get(3).getVector()));
        }
    }
    
    public static class Decasteljau extends AbstractBezier{
        public Decasteljau(Array<? extends org.jumpaku.affine.Point> cps, Interval interval) {
            super(cps, interval);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            Array<org.jumpaku.affine.Point> cps = getControlPoints();
            while(cps.size() > 1){
                cps = decasteljau(t, cps);
            }
            return cps.head();
        }
    }

    public static Bezier create(Array<? extends org.jumpaku.affine.Point> controlPoints, Interval domain){
        Array<? extends org.jumpaku.affine.Point> cps = controlPoints;
        
        if(cps.isEmpty())
            throw new IllegalArgumentException("control points are empty");
        
        if(cps.exists(p -> p == null))
            throw new IllegalArgumentException("control points contain null");
     
        return cps.size() == 1 ? new Point(cps.head(), domain) :
                cps.size() == 2 ? new LineSegment(cps.head(), cps.last(), domain) :
                cps.size() == 3 ? new Quadratic(cps.get(0), cps.get(1), cps.get(2), domain) :
                cps.size() == 4 ? new Cubic(cps.get(0), cps.get(1), cps.get(2), cps.get(3), domain) :
                                  new Decasteljau(cps, domain);
    }
    
    static Bezier create(org.jumpaku.affine.Point... controlPoints){
        return create(Array.of(controlPoints), Interval.ZERO_ONE);
    }
    
    public static String toJson(Bezier bezier){
        return JsonBezier.CONVERTER.toJson(bezier);
    }
    
    public static Option<Bezier> fromJson(String json){
        return JsonBezier.CONVERTER.fromJson(json);
    }
    
    @Override Interval getDomain();
    
    @Override org.jumpaku.affine.Point evaluate(Double t);

    @Override BezierDerivative differentiate();

    @Override Bezier restrict(Interval i);

     Bezier reverse();

    Array<? extends org.jumpaku.affine.Point> getControlPoints();

    Integer getDegree();
    
    Bezier elevate();
    
    Bezier reduce();
    
    Tuple2<? extends Bezier, ? extends Bezier> subdivide(Double t);
}
