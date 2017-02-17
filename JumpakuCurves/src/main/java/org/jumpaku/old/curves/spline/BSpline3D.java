/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.spline;

import javaslang.collection.Array;
import org.jumpaku.old.curves.domain.Interval;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Point3D;
import org.jumpaku.old.curves.vector.Vec3;

/**
 *
 * @author Jumpaku
 */
public class BSpline3D implements BSpline{

    BSpline curve;
    
    public static BSpline3D create(Array<Double> knots, Array<Point3D> controlPoints, Integer degree){
        return new BSpline3D(new BSplineDeBoor(knots, controlPoints.map(p->p), degree, 2));
    }

    private BSpline3D(BSplineDeBoor curve) {
        this.curve = curve;
    }

    private BSpline3D(BSpline curve) {
        this.curve = curve;
    }

    @Override
    public Point3D evaluate(Double t) {
        Point p = curve.evaluate(t);
        return new Point3D(p.get(0), p.get(1), p.get(2));
    }

    @Override
    public Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public Array<Point3D> getControlPoints() {
        return curve.getControlPoints().map(p -> new Point3D(p.get(0), p.get(1), p.get(2)));
    }

    @Override
    public Array<Double> getKnots() {
        return curve.getKnots();
    }

    @Override
    public Integer getDegree() {
        return curve.getDegree();
    }

    @Override
    public BSpline3D insertKnot(Double u) {
        return new BSpline3D(curve.insertKnot(u));
    }

    @Override
    public Integer getDimention() {
        return 3;
    }

    @Override
    public Vec3 computeTangent(Double t) {
        return new Vec3(curve.computeTangent(t));
    }
    
}