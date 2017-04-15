/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.spline;

import javaslang.collection.Array;
import main.old.curves.Curve;
import main.old.curves.domain.Interval;
import main.old.curves.vector.Point;
import main.old.curves.vector.Vec;

/**
 * <p>Spline曲線のインターフェイス Interface of Spline Curve.</p>
 * 
 * @author Jumpaku
 */
public interface Spline extends Curve{
   
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
    public Vec computeTangent(Double t); 
    
    /**
     * 
     * @param t
     * @return 
     */
    @Override
    Point evaluate(Double t);
}
