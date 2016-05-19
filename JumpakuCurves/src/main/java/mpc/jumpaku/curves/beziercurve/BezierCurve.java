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
import org.apache.commons.math3.geometry.Space;

/**
 *
 * @author ito
 * @param <S>
 */
public abstract interface BezierCurve<S extends Space, V extends Vector<S>> extends Curve<S>{
    Domain getDomain();
    
    List<V> getControlPoints();
    
    Integer getDegree();
    
    BezierCurve<S, V> elevate();
    
    List<BezierCurve<S, V>> divide(Double t);
    
    BezierCurve<S, V> transform(Transform<V> transform);
    
    BezierCurve<S, V> reverse();
    
    V computeTangent(Double t);
    
    @Override
    public abstract V evaluate(Double t);
}
