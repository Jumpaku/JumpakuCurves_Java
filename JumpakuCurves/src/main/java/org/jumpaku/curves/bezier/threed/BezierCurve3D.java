/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier.threed;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.jumpaku.curves.bezier.BezierCurve;
import org.jumpaku.curves.domain.Domain;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author Jumpaku
 */
public class BezierCurve3D implements BezierCurve<Euclidean3D, Vector3D>{

    private final BezierCurve<Euclidean3D, Vector3D> curve;
    
    private BezierCurve3D(BezierCurve<Euclidean3D, Vector3D> curve) {
        this.curve = curve;
    }
    
    public static BezierCurve3D create(List<Vector3D> cp){
        return new BezierCurve3D(BezierCurve.<Euclidean3D, Vector3D>create(cp));
    }
    
    public static BezierCurve3D create(Vector3D cp, Vector3D... cps){
        LinkedList<Vector3D> tmp = new LinkedList<>(Arrays.asList(cps));
        tmp.addFirst(cp);
        return BezierCurve3D.create(tmp);
    }
    
    @Override
    public final Domain getDomain() {
        return curve.getDomain();
    }

    @Override
    public final List<Vector3D> getControlPoints() {
        return curve.getControlPoints();
    }

    @Override
    public final Integer getDegree() {
        return curve.getDegree();
    }

    @Override
    public final BezierCurve3D elevate() {
        return new BezierCurve3D(curve.elevate());
    }

    @Override
    public final BezierCurve<Euclidean3D, Vector3D> reduce() {
        return new BezierCurve3D(curve.reduce());
    }
    
    @Override
    public final List<BezierCurve3D> divide(Double t) {
        return new LinkedList<BezierCurve3D>(){
            {
                add(new BezierCurve3D(curve.divide(t).get(0)));
                add(new BezierCurve3D(curve.divide(t).get(1)));
            }
        };
    }

    @Override
    public final BezierCurve3D transform(Transform<Euclidean3D, Vector3D> transform) {
        return new BezierCurve3D(curve.transform(transform));
    }

    @Override
    public final BezierCurve3D reverse() {
        return new BezierCurve3D(curve.reverse());
    }

    @Override
    public final Vector3D computeTangent(Double t) {
        return curve.computeTangent(t);
    }

    @Override
    public final Vector3D evaluate(Double t) {
        return curve.evaluate(t);
    }
}
