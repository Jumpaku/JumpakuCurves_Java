/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point2D;
import org.jumpaku.curves.vector.Vec2;

/**
 *
 * @author ito tomohiko
 */
public class BSplineCurve2D implements BSplineCurve{

    BSplineCurve curve;
    
    public static BSplineCurve2D create(Array<Double> knots, Array<Point2D> controlPoints, Integer degree){
        return new BSplineCurve2D(new BSplineCurveDeBoor(knots, controlPoints.map(p2 -> p2), degree, 2));
    }

    private BSplineCurve2D(BSplineCurve curve) {
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
    public BSplineCurve2D insertKnot(Double u) {
        return new BSplineCurve2D(curve.insertKnot(u));
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
