/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.jumpaku.curves.domain.Interval;

/**
 *
 * @author ito tomohiko
 */
public class BSplineCurve2D implements BSplineCurve<Euclidean2D, Vector2D>{

    BSplineCurve<Euclidean2D, Vector2D> curve;
    
    public static BSplineCurve2D create(Array<Double> knots, Array<Vector2D> controlPoints, Integer degree){
        return new BSplineCurve2D(new BSplineCurveDeBoor<>(knots, controlPoints, degree));
    }

    private BSplineCurve2D(BSplineCurve<Euclidean2D, Vector2D> curve) {
        this.curve = curve;
    }

    @Override
    public Vector2D evaluate(Double t) {
        return curve.evaluate(t);
    }

    @Override
    public Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public Array<Vector2D> getControlPoints() {
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
    public BSplineCurve2D insertKnot(Double u) {
        return new BSplineCurve2D(curve.insertKnot(u));
    }
    
}
