/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import org.jumpaku.curves.bezier.AbstractBezierCurve;
import java.util.Arrays;
import java.util.List;
import org.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author Jumpaku
 * @param <V>
 */
public class BezierCurveByDeCasteljau<S extends Space, V extends Vector<S>> extends AbstractBezierCurve<S, V> {
    public BezierCurveByDeCasteljau(List<V> cp) {
        super(cp);
    }
    
    public BezierCurveByDeCasteljau(V... cp) {
        this(Arrays.asList(cp));
    }
    
    @Override
    final public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t must be in domain [0,1], but t = " + t);
        
        Object[] buffer = getControlPoints().toArray();
        for(int n = getDegree(); n > 0; --n){
            for(int i = 0; i < n; ++i){
                buffer[i] = GeomUtils.internallyDivide(t, (Vector<S>)buffer[i], (Vector<S>)buffer[i+1]);
            }
        }
        return (V)buffer[0];
    }
}
