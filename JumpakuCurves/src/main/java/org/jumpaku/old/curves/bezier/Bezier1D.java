/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.old.curves.domain.Interval;
import org.jumpaku.old.curves.vector.Point1D;
import org.jumpaku.old.curves.vector.Vec1;

/**
 *
 * @author Jumpaku
 */
public class Bezier1D implements Bezier{

    private final Bezier curve;
    
    public Bezier1D(Bezier curve) {
        if(curve.getDimention() != 1)
            throw new IllegalArgumentException("dimention is not 1");

        this.curve = curve;
    }
    
    public static Bezier1D create(Iterable<Point1D> cp){
        return new Bezier1D(Bezier.create(Array.ofAll(cp), 1));
    }
    
    public static Bezier1D create(Point1D... cps){
        return Bezier1D.create(Array.of(cps));
    }
    
    @Override
    public final Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public final Array<Point1D> getControlPoints() {
        return curve.getControlPoints().map(Point1D::new);
    }

    @Override
    public final Integer getDegree() {
        return curve.getDegree();
    }

    @Override
    public final Bezier1D elevate() {
        return new Bezier1D(curve.elevate());
    }

    @Override
    public final Bezier1D reduce() {
        return new Bezier1D(curve.reduce());
    }
    
    @Override
    public final Array<Bezier1D> subdivide(Double t) {
        Array<? extends Bezier> divided = curve.subdivide(t);
        return Array.of(new Bezier1D(divided.head()), new Bezier1D(divided.last()));
    }

    /*@Override
    public final Bezier1D transform(Transform<Euclidean1D, Vector1D> transform) {
        return new Bezier1D(curve.transform(transform));
    }*/

    @Override
    public final Bezier1D reverse() {
        return new Bezier1D(curve.reverse());
    }

    @Override
    public final Vec1 computeTangent(Double t) {
        return new Vec1(curve.computeTangent(t));
    }

    @Override
    public final Point1D evaluate(Double t) {
        return new Point1D(curve.evaluate(t));
    }

    @Override
    public Integer getDimention() {
        return 1;
    }

    @Override
    public Bezier1D differentiate() {
        return new Bezier1D(curve.differentiate());
    }
}
