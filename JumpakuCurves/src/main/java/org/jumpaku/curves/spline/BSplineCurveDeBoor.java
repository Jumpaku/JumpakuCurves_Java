/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import java.util.ArrayList;
import java.util.List;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author ito tomohiko
 * @param <S>
 * @param <V>
 */
public final class BSplineCurveDeBoor<S extends Space, V extends Vector<S>> extends AbstractBSplineCurve<S, V> {
    
    public BSplineCurveDeBoor(List<Double> knots, List<V> controlPoints, Integer degree) {
        super(knots, controlPoints, degree);
    }
    
    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t is out of domain, t = " + t);
                
        Integer l = Stream.ofAll(getKnots()).lastIndexWhere(knot -> knot <= t);
        
        List<V> result = new ArrayList<>(getControlPoints());
        Integer n = getDegree();
        List<Double> knots = getKnots();
        
        for(int k = 1; k <= n; ++k){
            for(int i = l; i >= l-n+k; --i){
                Double aki = (t - knots.get(i)) / (knots.get(i+n+1-k) - knots.get(i));
                V cp = (V)result.get(i-1).scalarMultiply(1.0-aki).add(result.get(i).scalarMultiply(aki));
                result.set(i, cp);
            }
        }
        
        return result.get(l);
    }
}
