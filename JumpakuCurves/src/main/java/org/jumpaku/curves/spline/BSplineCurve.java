/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author ito tomohiko
 * @param <S>
 * @param <V>
 */
public interface BSplineCurve<S extends Space, V extends Vector<S>> extends SplineCurve<S, V>{
    
    /**
     * 
     * @param n
     * @param j
     * @param t
     * @param knots
     * @return 
     */
    public static Double bSplineBasis(Integer n, Integer j, Double t, Array<Double> knots){
        if(n == 0)
            return (knots.get(j).compareTo(t) <= 0 && knots.get(j+1).compareTo(t) > 0) ? 1.0 : 0.0;
        
        
        Double left = bSplineBasisHelper(t, knots.get(j), knots.get(j+n), knots.get(j));
        if(left.compareTo(0.0) != 0){
            left = left * bSplineBasis(n-1, j, t, knots);
        }
        Double right = bSplineBasisHelper(knots.get(j+n+1), t, knots.get(j+n+1), knots.get(j+1));
        if(right.compareTo(0.0) != 0){
            right = right * bSplineBasis(n-1, j+1, t, knots);
        }
        return left + right;
    }
    static Double bSplineBasisHelper(Double a, Double b, Double c, Double d){
        return Double.isFinite(c-d) ? (a-b)/(c-d) : 0.0;
    }
    
    public static <S extends Space, V extends Vector<S>> BSplineCurve<S, V> create(Array<Double> knots, Array<V> controlPoints, Integer degree){
        return new BSplineCurveDeBoor(knots, controlPoints, degree);
    }
    
    BSplineCurve<S, V> insertKnot(Double u);
    
}
