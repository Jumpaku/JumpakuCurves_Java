/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier.twod;

import javaslang.collection.Array;
import org.jumpaku.curves.bezier.BezierCurve;
import org.jumpaku.curves.domain.Domain;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.jumpaku.curves.domain.Interval;

/**
 *
 * @author Jumpaku
 */
public class BezierCurve2D implements BezierCurve<Euclidean2D, Vector2D>{

    private final BezierCurve<Euclidean2D, Vector2D> curve;
    
    private BezierCurve2D(BezierCurve<Euclidean2D, Vector2D> curve) {
        this.curve = curve;
    }
    
    public static BezierCurve2D create(Array<Vector2D> cp){
        return new BezierCurve2D(BezierCurve.create(cp));
    }
    
    public static BezierCurve2D create(Vector2D... cps){
        return BezierCurve2D.create(Array.of(cps));
    }
    
    @Override
    public final Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public final Array<Vector2D> getControlPoints() {
        return curve.getControlPoints();
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
        return Array.of(new BezierCurve2D(curve.divide(t).get(0)), new BezierCurve2D(curve.divide(t).get(1)));
    }

    @Override
    public final BezierCurve2D transform(Transform<Euclidean2D, Vector2D> transform) {
        return new BezierCurve2D(curve.transform(transform));
    }

    @Override
    public final BezierCurve2D reverse() {
        return new BezierCurve2D(curve.reverse());
    }

    @Override
    public final Vector2D computeTangent(Double t) {
        return curve.computeTangent(t);
    }

    @Override
    public final Vector2D evaluate(Double t) {
        return curve.evaluate(t);
    }
}
