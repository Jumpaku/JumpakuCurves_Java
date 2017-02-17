/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.old.curves.affine.Affine2D;
import org.jumpaku.old.curves.domain.Interval;
import org.jumpaku.old.curves.vector.Point2D;
import org.jumpaku.old.curves.vector.Vec2;

/**
 *
 * @author Jumpaku
 */
public class Bezier2D implements Bezier{

    private final Bezier curve;
    
    public Bezier2D(Bezier curve) {
        if(curve.getDimention() != 2)
            throw new IllegalArgumentException("dimention is not 2");
        
        this.curve = curve;
    }
    
    public static Bezier2D create(Iterable<Point2D> cp){
        return new Bezier2D(Bezier.create(Array.ofAll(cp), 2));
    }
    
    public static Bezier2D create(Point2D... cps){
        return Bezier2D.create(Array.of(cps));
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
    public final Bezier2D elevate() {
        return new Bezier2D(curve.elevate());
    }

    @Override
    public final Bezier2D reduce() {
        return new Bezier2D(curve.reduce());
    }
    
    @Override
    public final Array<Bezier2D> subdivide(Double t) {
        Array<? extends Bezier> divided = curve.subdivide(t);
        return Array.of(new Bezier2D(divided.head()), new Bezier2D(divided.last()));
    }

    public final Bezier2D transform(Affine2D a) {
        return new Bezier2D(Bezier.create(getControlPoints().map(p -> a.apply(p)), 2));
    }

    @Override
    public final Bezier2D reverse() {
        return new Bezier2D(curve.reverse());
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

    @Override
    public Bezier2D differentiate() {
        return new Bezier2D(curve.differentiate());
    }
}
