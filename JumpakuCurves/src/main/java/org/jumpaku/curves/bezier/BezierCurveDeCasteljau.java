/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 * @param <V> {@link BezierCurveDeCasteljau#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link BezierCurveDeCasteljau#evaluate(java.lang.Double)}.
 */
public class BezierCurveDeCasteljau <V extends Vec> extends AbstractBezierCurve<V> {
    public BezierCurveDeCasteljau(Array<V> cp) {
        super(cp);
    }
    
    public BezierCurveDeCasteljau(V... cp) {
        this(Array.of(cp));
    }
    
    @Override
    final public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t must be in domain [0,1], but t = " + t);
        
        Object[] cp = getControlPoints().toJavaArray();
        for(int n = getDegree(); n > 0; --n){
            for(int i = 0; i < n; ++i){
                cp[i] = (V)(((V)cp[i]).scale(1-t).add(t, (V)cp[i+1]));
            }
        }
        return (V)cp[0];
    }
}
