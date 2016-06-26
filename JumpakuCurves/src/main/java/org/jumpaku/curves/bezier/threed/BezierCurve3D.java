/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier.threed;

import javaslang.collection.Array;
import org.jumpaku.curves.bezier.BezierCurve;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point3D;
import org.jumpaku.curves.vector.Vec;
import org.jumpaku.curves.vector.Vec3;

/**
 *
 * @author Jumpaku
 */
public class BezierCurve3D implements BezierCurve{

    private final BezierCurve curve;
    
    public BezierCurve3D(BezierCurve curve) {
        this.curve = curve;
    }
    
    public static BezierCurve3D create(Array<Point3D> cp){
        return new BezierCurve3D(BezierCurve.create(cp.map(p3 -> p3), 3));
    }
    
    public static BezierCurve3D create(Point3D... cps){
        return BezierCurve3D.create(Array.of(cps));
    }
    
    @Override
    public final Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public final Array<Point3D> getControlPoints() {
        return curve.getControlPoints().map(p -> new Point3D(p.get(0), p.get(1), p.get(2)));
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
    public final BezierCurve reduce() {
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
        Vec tangent = curve.computeTangent(t);
        return new Vec3(tangent.get(0), tangent.get(1), tangent.get(2));
    }

    @Override
    public final Point3D evaluate(Double t) {
        Point p = curve.evaluate(t);
        return new Point3D(p.get(0), p.get(1), p.get(2));
    }

    @Override
    public Integer getDimention() {
        return 3;
    }
}
