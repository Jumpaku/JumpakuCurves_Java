/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.DefinedOnInterval;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Differentiable;

/**
 *
 * @author tomohiko
 */
public interface Bezier extends Curve, Differentiable, DefinedOnInterval<Bezier>{
    
    public static class Point extends AbstractBezier{

        public Point(org.jumpaku.affine.Point cp) {
            super(Array.of(cp), Interval.ZERO_ONE);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return cp.get(0);
        }
    }
    
    public static class LineSegment extends AbstractBezier{

        public LineSegment(org.jumpaku.affine.Point cp0, org.jumpaku.affine.Point cp1) {
            super(Array.of(cp0, cp1), Interval.ZERO_ONE);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return cp.get(0).divide(t, cp.get(1));
        }
    }
    
    public static class Quadratic extends AbstractBezier{

        public Quadratic(org.jumpaku.affine.Point cp0, org.jumpaku.affine.Point cp1, org.jumpaku.affine.Point cp2) {
            super(Array.of(cp0, cp1, cp2), Interval.ZERO_ONE);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return cp.get(0).divide(2*t*(1-t), cp.get(1)).divide(t*t, cp.get(2));
        }
    }

    public static class Cubic extends AbstractBezier{

        public Cubic(org.jumpaku.affine.Point cp0, org.jumpaku.affine.Point cp1, org.jumpaku.affine.Point cp2, org.jumpaku.affine.Point cp3) {
            super(Array.of(cp0, cp1, cp2, cp3), Interval.ZERO_ONE);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            Array<org.jumpaku.affine.Point> cp = getControlPoints();
            return cp.get(0)
                    .divide(3*t*(1-t)*(1-t), cp.get(1))
                    .divide(3*t*t*(1-t), cp.get(2))
                    .divide(t*t*t, cp.get(3));
        }
    }
    
    public static class Decasteljau extends AbstractBezier{
        public Decasteljau(Array<? extends org.jumpaku.affine.Point> cps) {
            super(cps, Interval.ZERO_ONE);
        }

        @Override public org.jumpaku.affine.Point evaluate(Double t) {
            Array<org.jumpaku.affine.Point> cps = getControlPoints();
            while(cps.size() > 1){
                cps = decasteljau(t, getControlPoints());
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
     
        return cps.size() == 1 ? new Point(cps.head()) :
                cps.size() == 2 ? new LineSegment(cps.head(), cps.last()) :
                cps.size() == 3 ? new Quadratic(cps.get(0), cps.get(1), cps.get(2)) :
                cps.size() == 4 ? new Cubic(cps.get(0), cps.get(1), cps.get(2), cps.get(3)) :
                                  new Decasteljau(cps);
    }
    
    static Bezier create(org.jumpaku.affine.Point... controlPoints){
        return create(Array.of(controlPoints), Interval.ZERO_ONE);
    }
    
    
    public static String toString(Bezier bezier){
        return null;
    }
    
    @Override Interval getDomain();
    
    @Override org.jumpaku.affine.Point evaluate(Double t);
    
    @Override Vector differentiate(Double t);

    @Override BezierDerivative differentiate();

    @Override Bezier restrict(Interval i);

    @Override Bezier reverse();

    Array<org.jumpaku.affine.Point> getControlPoints();

    Integer getDegree();
    
    Bezier elevate();
    
    Bezier reduce();
    
    Tuple2<? extends Bezier, ? extends Bezier> subdivide(Double t);
}
