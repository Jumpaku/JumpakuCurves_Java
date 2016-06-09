/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author Jumpaku
 * @param <S> 座標空間の種類  Type of the space. 
 * @param <V> {@link BezierCurveDeCasteljau#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link BezierCurveDeCasteljau#evaluate(java.lang.Double)}.
 */
public class BezierCurveDeCasteljau<S extends Space, V extends Vector<S>> extends AbstractBezierCurve<S, V> {
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
                cp[i] = (V)GeomUtils.internallyDivide(t, (V)cp[i], (V)cp[i+1]);
            }
        }
        return (V)cp[0];
    }
}
