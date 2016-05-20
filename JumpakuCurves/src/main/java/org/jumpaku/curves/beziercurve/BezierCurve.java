/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.beziercurve;

import org.jumpaku.curves.domain.Domain;
import java.util.List;
import org.jumpaku.curves.Curve;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.Space;

/**
 *
 * @author ito
 * @param <S>
 * @param <V>
 */
public abstract interface BezierCurve<S extends Space, V extends Vector<S>> extends Curve<S, V>{
    Domain getDomain();
    
    List<V> getControlPoints();
    
    Integer getDegree();
    
    BezierCurve<S, V> elevate();
    
    List<? extends BezierCurve<S, V>> divide(Double t);
    
    BezierCurve<S, V> transform(Transform<S, V> transform);
    
    BezierCurve<S, V> reverse();
    
    V computeTangent(Double t);
    
    @Override
    public abstract V evaluate(Double t);
}
