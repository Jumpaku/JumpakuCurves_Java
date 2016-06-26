/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import org.jumpaku.curves.Curve;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;

/**
 * <p>Spline曲線のインターフェイス Interface of Spline Curve.</p>
 * 
 * @author Jumpaku
 */
public interface SplineCurve extends Curve{
   
    /**
     * {@inheritDoc }
     * @return
     */
    @Override
    Interval getDomain();

    /**{@inheritDoc }*/
    @Override
    public Integer getDimention();
    
    /**
     * 
     * @return 
     */
    Array<? extends Point> getControlPoints();
    
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
     * @param t
     * @return 
     */
    @Override
    Point evaluate(Double t);
}
