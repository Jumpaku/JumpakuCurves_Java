/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point1D;
import org.jumpaku.curves.vector.Vec1;

/**
 *
 * @author Jumpaku
 */
public class BezierCurve1D implements BezierCurve{

    private final BezierCurve curve;
    
    public BezierCurve1D(BezierCurve curve) {
        this.curve = curve;
    }
    
    public static BezierCurve1D create(Array<Point1D> cp){
        return new BezierCurve1D(BezierCurve.create(cp.map(p1 -> p1), 1));
    }
    
    public static BezierCurve1D create(Point1D... cps){
        return BezierCurve1D.create(Array.of(cps));
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
    public final BezierCurve1D elevate() {
        return new BezierCurve1D(curve.elevate());
    }

    @Override
    public final BezierCurve1D reduce() {
        return new BezierCurve1D(curve.reduce());
    }
    
    @Override
    public final Array<BezierCurve1D> divide(Double t) {
        Array<? extends BezierCurve> divided = curve.divide(t);
        return Array.of(new BezierCurve1D(divided.head()), new BezierCurve1D(divided.last()));
    }

    /*@Override
    public final BezierCurve1D transform(Transform<Euclidean1D, Vector1D> transform) {
        return new BezierCurve1D(curve.transform(transform));
    }*/

    @Override
    public final BezierCurve1D reverse() {
        return new BezierCurve1D(curve.reverse());
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
}
