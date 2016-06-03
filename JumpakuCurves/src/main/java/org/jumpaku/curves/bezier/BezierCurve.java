/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import java.util.Arrays;
import org.jumpaku.curves.domain.Domain;
import java.util.List;
import org.jumpaku.curves.Curve;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.Space;
import org.jumpaku.curves.utils.GeomUtils;

/**
 *
 * @author Jumpaku
 * @param <S>
 * @param <V>
 */
public abstract interface BezierCurve<S extends Space, V extends Vector<S>> extends Curve<S, V>{
    
    public static <S extends Space, V extends Vector<S>> BezierCurve<S, V> create(List<V> cp){
        if(cp == null)
            throw new IllegalArgumentException("control points are null");
        
        if(cp.isEmpty())
            throw new IllegalArgumentException("control points are empty");
        
        if(cp.stream().anyMatch(p -> p == null))
            throw new IllegalArgumentException("control points contain null");
        
        switch (cp.size()) {
            case 1:
                return new AbstractBezierCurve<S, V>(cp) {
                    @Override
                    public V evaluate(Double t) {
                        return getControlPoints().get(0);
                    }
                };
            case 2:
                return new AbstractBezierCurve<S, V>(cp) {
                    @Override
                    public V evaluate(Double t) {
                        List<V> cp = getControlPoints();
                        return GeomUtils.internallyDivide(t, cp.get(0), cp.get(1));
                    }
                };
            case 3:
                return new AbstractBezierCurve<S, V>(cp) {
                    @Override
                    public V evaluate(Double t) {
                        List<V> cp = getControlPoints();
                        return (V) cp.get(0).scalarMultiply((1-t)*(1-t)).add(2*t*(1-t), cp.get(1)).add(t*t, cp.get(2));
                    }
                };
            case 4:
                return new AbstractBezierCurve<S, V>(cp) {
                    @Override
                    public V evaluate(Double t) {
                        List<V> cp = getControlPoints();
                        return (V) cp.get(0).scalarMultiply((1-t)*(1-t)*(1-t)).add(3*t*(1-t)*(1-t), cp.get(1)).add(3*t*t*(1-t), cp.get(2)).add(t*t*t, cp.get(3));
                    }
                };
            default:
                return new BezierCurveByBernstein<>(cp);
        }
    }
    Domain getDomain();
    
    List<V> getControlPoints();
    
    Integer getDegree();
    
    BezierCurve<S, V> elevate();
    
    BezierCurve<S, V> reduce();
    
    List<? extends BezierCurve<S, V>> divide(Double t);
    
    BezierCurve<S, V> transform(Transform<S, V> transform);
    
    BezierCurve<S, V> reverse();
    
    V computeTangent(Double t);
    
    @Override
    V evaluate(Double t);
}
