/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point3D;

/**
 *
 * @author Jumpaku
 */
public class BSplineCurve3D implements BSplineCurve{

    BSplineCurve curve;
    
    public static BSplineCurve3D create(Array<Double> knots, Array<Point3D> controlPoints, Integer degree){
        return new BSplineCurve3D(new BSplineCurveDeBoor(knots, controlPoints.map(p->p), degree, 2));
    }

    private BSplineCurve3D(BSplineCurveDeBoor curve) {
        this.curve = curve;
    }

    private BSplineCurve3D(BSplineCurve curve) {
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
    public BSplineCurve3D insertKnot(Double u) {
        return new BSplineCurve3D(curve.insertKnot(u));
    }

    @Override
    public Integer getDimention() {
        return 3;
    }
    
}