/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import mpc.jumpaku.curves.domain.Domain;
import java.util.List;
import mpc.jumpaku.curves.Curve;
import org.apache.commons.math3.geometry.Vector;
import mpc.jumpaku.curves.transform.Transform;

/**
 *
 * @author ito
 * @param <V>
 */
public abstract interface BezierCurve<V extends Vector> extends Curve<V>{
    Domain getDomain();
    
    List<V> getControlPoints();
    
    Integer getDegree();
    
    BezierCurve<V> elevate();
    
    List<BezierCurve<V>> divide(Double t);
    
    BezierCurve<V> transform(Transform<V> transform);
    
    BezierCurve<V> reverse();
    
    V computeTangent(Double t);
    
    @Override
    public abstract V evaluate(Double t);
}
