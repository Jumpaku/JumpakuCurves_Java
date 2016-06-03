/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import java.util.List;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.Curve;
import org.jumpaku.curves.domain.Domain;

/**
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
    Domain getDomain();
    
    List<V> getControlPoints();
    
    List<Double> getKnots();
    
    Integer getDegree();
    
    @Override
    V evaluate(Double t);
}
