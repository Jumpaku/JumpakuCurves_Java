/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.vector.WeightedPoint;

/**
 *
 * @author Jumpaku
 * @param <S>
 * @param <V>
 */
public interface RationalBezierCurve<S extends Space, V extends Vector<S>> extends BezierCurve<S, V>{
    
    Array<Double> getWeights();
    
    Array<WeightedPoint> getWeightedPoints();
    
}
