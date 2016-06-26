/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Vec3;

/**
 *
 * @author Jumpaku
 */
public class BSplineCurve3D implements BSplineCurve<Vec3>{

    BSplineCurve<Vec3> curve;
    
    public static BSplineCurve3D create(Array<Double> knots, Array<Vec3> controlPoints, Integer degree){
        return new BSplineCurve3D(new BSplineCurveDeBoor<>(knots, controlPoints, degree));
    }

    private BSplineCurve3D(BSplineCurveDeBoor<Vec3> curve) {
        this.curve = curve;
    }

    private BSplineCurve3D(BSplineCurve<Vec3> curve) {
        this.curve = curve;
    }

    @Override
    public Vec3 evaluate(Double t) {
        return curve.evaluate(t);
    }

    @Override
    public Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public Array<Vec3> getControlPoints() {
        return curve.getControlPoints();
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
    
}