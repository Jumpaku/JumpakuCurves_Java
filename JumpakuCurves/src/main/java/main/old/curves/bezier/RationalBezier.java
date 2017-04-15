/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.bezier;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import main.old.curves.Curve;
import main.old.curves.domain.Closed;
import main.old.curves.domain.Interval;
import main.old.curves.vector.Point;
import main.old.curves.vector.Point1D;
import main.old.curves.vector.Point2D;
import main.old.curves.vector.Point3D;
import main.old.curves.vector.Vec;
import main.old.curves.vector.Vec2;

/**
 *
 * @author Jumpaku
 */
public interface RationalBezier extends Curve{
    
    Interval DOMAIN = new Closed(0.0, 1.0);
    
    public static RationalBezier create(Array<? extends Point> controlPoints, Array<Double> weights, Integer dimention){
        return new RationalBezierFast(controlPoints, weights, dimention);
    }
    
    public static RationalBezier create(Bezier bezier){
        return create(bezier.getControlPoints(), Array.fill(bezier.getDegree()+1, () -> 1.0), bezier.getDimention());
    }
    
    public static RationalBezier quadratic1D(Point1D p0, Double w, Point1D p1, Point1D p2){
        return create(Array.of(p0, p1, p2), Array.of(1.0, w, 1.0), 1);
    }
    
    public static RationalBezier quadratic2D(Point2D p0, Double w, Point2D p1, Point2D p2){
        return create(Array.of(p0, p1, p2), Array.of(1.0, w, 1.0), 2);
    }

    public static RationalBezier quadratic3D(Point3D p0, Double w, Point3D p1, Point3D p2){
        return create(Array.of(p0, p1, p2), Array.of(1.0, w, 1.0), 3);
    }
    
    public static Tuple2<RationalBezier, RationalBezier> circularArc(Point2D begin, Point2D p, Point2D end){
        Point2D m = begin.divide(0.5, end);
        Double l2 = m.distanceSquare(begin);
        Double h2 = m.distanceSquare(p);
        Double w = (l2 - h2)/(l2 + h2);
        
        Point2D p0 = begin, p1 = new Point2D(new Vec2(Vec.add((1+w)/w, p.getVec(), -1/w, m.getVec()))), p2 = end;
        
        return Tuple.of(quadratic2D(p0, w, p1, p2), quadratic2D(p0, -w, p1, p2));
    }

    Array<Double> getWeights();
    
    Array<? extends Point> getControlPoints();

    default Integer getDegree(){
        return getControlPoints().size() - 1;
    }

    @Override
    Integer getDimention();

    @Override
    default Interval getDomain(){
        return DOMAIN;
    }

    @Override
    Point evaluate(Double t);

    Vec computeTangent(Double t);

    Array<? extends RationalBezier> subdivide(Double t);

    RationalBezier elevate();

    RationalBezier reduce();

    RationalBezier reverse();
}
