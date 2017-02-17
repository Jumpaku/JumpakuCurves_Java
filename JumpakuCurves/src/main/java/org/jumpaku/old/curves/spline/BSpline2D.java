/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.spline;

import javaslang.collection.Array;
import org.jumpaku.old.curves.domain.Interval;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Point2D;
import org.jumpaku.old.curves.vector.Vec2;

/**
 *
 * @author ito Jumpaku
 */
public class BSpline2D implements BSpline{

    BSpline curve;
    
    public static BSpline2D create(Array<Double> knots, Array<Point2D> controlPoints, Integer degree){
        return new BSpline2D(new BSplineDeBoor(knots, controlPoints.map(p2 -> p2), degree, 2));
    }

    private BSpline2D(BSpline curve) {
        this.curve = curve;
    }

    @Override
    public Point2D evaluate(Double t) {
        Point p = curve.evaluate(t);
        return new Point2D(p.get(0), p.get(1));
    }

    @Override
    public Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public Array<Point2D> getControlPoints() {
        return curve.getControlPoints().map(p -> new Point2D(p.get(0), p.get(1)));
    }

    @Override
    public Array<Double> getKnots() {
        return curve.getKnots();
    }

    @Override
    public Integer getDegree() {
        return curve.getDegree();
    }

    @Override
    public BSpline2D insertKnot(Double u) {
        return new BSpline2D(curve.insertKnot(u));
    }

    @Override
    public Integer getDimention() {
        return 2;
    }

    @Override
    public Vec2 computeTangent(Double t) {
        return new Vec2(curve.computeTangent(t));
    }
    
}
