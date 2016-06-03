/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author Jumpaku
 * @param <S> 座標空間の種類  Type of the space. 
 * @param <V> {@link BezierCurveByDeCasteljau#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link BezierCurveByDeCasteljau#evaluate(java.lang.Double)}.
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
        
        List<V> cp = new ArrayList<>(getControlPoints());
        for(int n = getDegree(); n > 0; --n){
            for(int i = 0; i < n; ++i){
                cp.set(i, (V)GeomUtils.internallyDivide(t, cp.get(i), cp.get(i+1)));
            }
        }
        return (V)cp.get(0);
    }
}
