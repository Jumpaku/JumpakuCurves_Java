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

/**
 *
 * @author Jumpaku
 */
public class BSplineCurve3D implements BSplineCurve<Euclidean3D, Vector3D>{

    BSplineCurve<Euclidean3D, Vector3D> curve;
    
    public static BSplineCurve3D create(Array<Double> knots, Array<Vector3D> controlPoints, Integer degree){
        return new BSplineCurve3D(new BSplineCurveDeBoor<>(knots, controlPoints, degree));
    }

    private BSplineCurve3D(BSplineCurveDeBoor<Euclidean3D, Vector3D> curve) {
        this.curve = curve;
    }

    private BSplineCurve3D(BSplineCurve<Euclidean3D, Vector3D> curve) {
        this.curve = curve;
    }

    @Override
    public Vector3D evaluate(Double t) {
        return curve.evaluate(t);
    }

    @Override
    public Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public Array<Vector3D> getControlPoints() {
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