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
public class Bezier3D implements Bezier{

    private final Bezier curve;
    
    public Bezier3D(Bezier curve) {
        if(curve.getDimention() != 3)
            throw new IllegalArgumentException("dimention is not 3");
        this.curve = curve;
    }
    
    public static Bezier3D create(Iterable<Point3D> cp){
        return new Bezier3D(Bezier.create(Array.ofAll(cp), 3));
    }
    
    public static Bezier3D create(Point3D... cps){
        return Bezier3D.create(Array.of(cps));
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
    public final Bezier3D elevate() {
        return new Bezier3D(curve.elevate());
    }

    @Override
    public final Bezier reduce() {
        return new Bezier3D(curve.reduce());
    }
    
    @Override
    public final Array<Bezier3D> subdivide(Double t) {
        return Array.of(new Bezier3D(curve.subdivide(t).head()),
                new Bezier3D(curve.subdivide(t).last()));
    }

    public final Bezier3D transform(Affine3D a) {
        return new Bezier3D(Bezier.create(getControlPoints().map(p -> a.apply(p)), 3));
    }

    @Override
    public final Bezier3D reverse() {
        return new Bezier3D(curve.reverse());
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
    public Bezier3D differentiate() {
        return new Bezier3D(curve.differentiate());
    }
}
