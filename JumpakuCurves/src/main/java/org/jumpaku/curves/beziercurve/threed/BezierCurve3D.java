/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.beziercurve.threed;

import java.util.LinkedList;
import java.util.List;
import org.jumpaku.curves.beziercurve.BezierCurve;
import org.jumpaku.curves.beziercurve.BezierCurveByBernstein;
import org.jumpaku.curves.beziercurve.BezierCurveByDeCasteljau;
import org.jumpaku.curves.domain.Domain;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author ito
 */
public class BezierCurve3D implements BezierCurve<Euclidean3D, Vector3D>{

    private final BezierCurve<Euclidean3D, Vector3D> curve;
    private BezierCurve3D(BezierCurve<Euclidean3D, Vector3D> curve) {
        this.curve = curve;
    }
    
    public static BezierCurve3D createBernstein(List<Vector3D> cp){
        return new BezierCurve3D(new BezierCurveByBernstein<>(cp));
    }
    
    public static BezierCurve3D createDeCasteljau(List<Vector3D> cp){
        return new BezierCurve3D(new BezierCurveByDeCasteljau<>(cp));
    }

    @Override
    public Domain getDomain() {
        return curve.getDomain();
    }

    @Override
    public List<Vector3D> getControlPoints() {
        return curve.getControlPoints();
    }

    @Override
    public Integer getDegree() {
        return curve.getDegree();
    }

    @Override
    public BezierCurve3D elevate() {
        return new BezierCurve3D(curve.elevate());
    }

    @Override
    public List<BezierCurve3D> divide(Double t) {
        return new LinkedList<BezierCurve3D>(){
                {
                    add(new BezierCurve3D(curve.divide(t).get(0)));
                    add(new BezierCurve3D(curve.divide(t).get(1)));
                }
        };
    }

    @Override
    public BezierCurve3D transform(Transform<Euclidean3D, Vector3D> transform) {
        return new BezierCurve3D(curve.transform(transform));
    }

    @Override
    public BezierCurve3D reverse() {
        return new BezierCurve3D(curve.reverse());
    }

    @Override
    public Vector3D computeTangent(Double t) {
        return curve.computeTangent(t);
    }

    @Override
    public Vector3D evaluate(Double t) {
        return curve.evaluate(t);
    }
    
}
