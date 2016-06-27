/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point2D;
import org.jumpaku.curves.vector.Vec2;

/**
 *
 * @author Jumpaku
 */
public class BezierCurve2D implements BezierCurve{

    private final BezierCurve curve;
    
    public BezierCurve2D(BezierCurve curve) {
        this.curve = curve;
    }
    
    public static BezierCurve2D create(Array<Point2D> cp){
        return new BezierCurve2D(BezierCurve.create(cp.map(p2 -> p2), 2));
    }
    
    public static BezierCurve2D create(Point2D... cps){
        return BezierCurve2D.create(Array.of(cps));
    }
    
    @Override
    public final Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public final Array<Point2D> getControlPoints() {
        return curve.getControlPoints().map(Point2D::new);
    }

    @Override
    public final Integer getDegree() {
        return curve.getDegree();
    }

    @Override
    public final BezierCurve2D elevate() {
        return new BezierCurve2D(curve.elevate());
    }

    @Override
    public final BezierCurve2D reduce() {
        return new BezierCurve2D(curve.reduce());
    }
    
    @Override
    public final Array<BezierCurve2D> divide(Double t) {
        return Array.of(new BezierCurve2D(curve.divide(t).head()), new BezierCurve2D(curve.divide(t).last()));
    }

    /*@Override
    public final BezierCurve2D transform(Transform<Euclidean2D, Vector2D> transform) {
        return new BezierCurve2D(curve.transform(transform));
    }*/

    @Override
    public final BezierCurve2D reverse() {
        return new BezierCurve2D(curve.reverse());
    }

    @Override
    public final Vec2 computeTangent(Double t) {
        return new Vec2(curve.computeTangent(t));
    }

    @Override
    public final Point2D evaluate(Double t) {
        return new Point2D(curve.evaluate(t));
    }

    @Override
    public Integer getDimention() {
        return 2;
    }
}
