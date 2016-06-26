/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier.threed;

import javaslang.collection.Array;
import org.jumpaku.curves.bezier.BezierCurve;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Vec3;

/**
 *
 * @author Jumpaku
 */
public class BezierCurve3D implements BezierCurve<Vec3>{

    private final BezierCurve<Vec3> curve;
    
    public BezierCurve3D(BezierCurve<Vec3> curve) {
        this.curve = curve;
    }
    
    public static BezierCurve3D create(Array<Vec3> cp){
        return new BezierCurve3D(BezierCurve.<Vec3>create(cp));
    }
    
    public static BezierCurve3D create(Vec3... cps){
        return BezierCurve3D.create(Array.of(cps));
    }
    
    @Override
    public final Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public final Array<Vec3> getControlPoints() {
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
    public final BezierCurve<Vec3> reduce() {
        return new BezierCurve3D(curve.reduce());
    }
    
    @Override
    public final Array<BezierCurve3D> divide(Double t) {
        return Array.of(new BezierCurve3D(curve.divide(t).get(0)),
                new BezierCurve3D(curve.divide(t).get(1)));
    }

    /*@Override
    public final BezierCurve3D transform(Transform<Euclidean3D, Vector3D> transform) {
        return new BezierCurve3D(curve.transform(transform));
    }*/

    @Override
    public final BezierCurve3D reverse() {
        return new BezierCurve3D(curve.reverse());
    }

    @Override
    public final Vec3 computeTangent(Double t) {
        return curve.computeTangent(t);
    }

    @Override
    public final Vec3 evaluate(Double t) {
        return curve.evaluate(t);
    }
}
