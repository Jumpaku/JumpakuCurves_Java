/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.affine.Affine3D;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point3D;
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
    
    public static BezierCurve3D create(Iterable<Point3D> cp){
        return new BezierCurve3D(BezierCurve.create(Array.ofAll(cp), 3));
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
        return curve.getControlPoints().map(Point3D::new);
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
        return Array.of(new BezierCurve3D(curve.divide(t).head()),
                new BezierCurve3D(curve.divide(t).last()));
    }

    public final BezierCurve3D transform(Affine3D a) {
        return new BezierCurve3D(BezierCurve.create(getControlPoints().map(p -> a.apply(p)), 3));
    }

    @Override
    public final BezierCurve3D reverse() {
        return new BezierCurve3D(curve.reverse());
    }

    @Override
    public final Vec3 computeTangent(Double t) {
        return new Vec3(curve.computeTangent(t));
    }

    @Override
    public final Point3D evaluate(Double t) {
        return new Point3D(curve.evaluate(t));
    }

    @Override
    public Integer getDimention() {
        return 3;
    }

    @Override
    public BezierCurve3D differentiate() {
        return new BezierCurve3D(curve.differentiate());
    }
}
