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
import org.jumpaku.curves.vector.Vec2;

/**
 *
 * @author ito tomohiko
 */
public class BSplineCurve2D implements BSplineCurve<Vec2>{

    BSplineCurve<Vec2> curve;
    
    public static BSplineCurve2D create(Array<Double> knots, Array<Vec2> controlPoints, Integer degree){
        return new BSplineCurve2D(new BSplineCurveDeBoor<>(knots, controlPoints, degree));
    }

    private BSplineCurve2D(BSplineCurve<Vec2> curve) {
        this.curve = curve;
    }

    @Override
    public Vec2 evaluate(Double t) {
        return curve.evaluate(t);
    }

    @Override
    public Interval getDomain() {
        return curve.getDomain();
    }

    @Override
    public Array<Vec2> getControlPoints() {
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
