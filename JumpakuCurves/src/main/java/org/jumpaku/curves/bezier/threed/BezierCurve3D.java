/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier.threed;

import javaslang.collection.Array;
import org.jumpaku.curves.bezier.BezierCurve;
import org.jumpaku.curves.domain.Domain;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Interval;

/**
 *
 * @author Jumpaku
 */
public class BezierCurve3D implements BezierCurve<Euclidean3D, Vector3D>{

    private final BezierCurve<Euclidean3D, Vector3D> curve;
    
    private BezierCurve3D(BezierCurve<Euclidean3D, Vector3D> curve) {
        this.curve = curve;
    }
    
    public static BezierCurve3D create(Array<Vector3D> cp){
        return new BezierCurve3D(BezierCurve.<Euclidean3D, Vector3D>create(cp));
    }
    
    public static BezierCurve3D create(Vector3D... cps){
        return BezierCurve3D.create(Array.of(cps));
    }
    
    @Override
    public final Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public final Array<Vector3D> getControlPoints() {
        return curve.getControlPoints();
    }

    @Override
    public final Integer getDegree() {
        return curve.getDegree();
    }

    @Override
    public final BezierCurve3D elevate() {
        return new BezierCurve3D(curve.elevate());
    }

    @Override
    public final BezierCurve<Euclidean3D, Vector3D> reduce() {
        return new BezierCurve3D(curve.reduce());
    }
    
    @Override
    public final Array<BezierCurve3D> divide(Double t) {
        return Array.of(new BezierCurve3D(curve.divide(t).get(0)),
                new BezierCurve3D(curve.divide(t).get(1)));
    }

    @Override
    public final BezierCurve3D transform(Transform<Euclidean3D, Vector3D> transform) {
        return new BezierCurve3D(curve.transform(transform));
    }

    @Override
    public final BezierCurve3D reverse() {
        return new BezierCurve3D(curve.reverse());
    }

    @Override
    public final Vector3D computeTangent(Double t) {
        return curve.computeTangent(t);
    }

    @Override
    public final Vector3D evaluate(Double t) {
        return curve.evaluate(t);
    }
}
