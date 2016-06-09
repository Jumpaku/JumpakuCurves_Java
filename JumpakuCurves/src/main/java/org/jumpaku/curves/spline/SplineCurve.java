/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.Curve;
import org.jumpaku.curves.domain.ClosedDomain;

/**
 * <p>Spline曲線のインターフェイス Interface of Spline Curve.</p>
 * 
 * @author Jumpaku
 * @param <S>
 * @param <V>
 */
public interface SplineCurve<S extends Space, V extends Vector<S>>extends Curve<S, V>{

    /**
     *
     * @return
     */
    ClosedDomain getDomain();
    
    /**
     * 
     * @return 
     */
    Array<V> getControlPoints();
    
    /**
     * 
     * @return 
     */
    Array<Double> getKnots();
    
    /**
     * 
     * @return 
     */
    Integer getDegree();
    
    /**
     * 
     * @param u
     * @return 
     */
    SplineCurve<S, V> insertKnot(Double u);
    
    /**
     * 
     * @param t
     * @return 
     */
    @Override
    V evaluate(Double t);
}
