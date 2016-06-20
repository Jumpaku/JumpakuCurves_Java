/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.domain.ClosedOpen;
import org.jumpaku.curves.domain.Interval;

/**
 *
 * @author Jumpaku
 * @param <S>
 * @param <V>
 */
public final class BSplineCurveDeBoor<S extends Space, V extends Vector<S>> extends AbstractBSplineCurve<S, V> {
    
    private final Interval domain;
    public BSplineCurveDeBoor(Array<Double> knots, Array<V> controlPoints, Integer degree) {
        super(knots, controlPoints, degree);
        domain = new ClosedOpen(super.getDomain().getFrom(), super.getDomain().getTo());
    }

    @Override
    public Interval getDomain(){
        return domain;
    }
    
    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t is out of domain, t = " + t);
                
        Integer l = Stream.ofAll(getKnots()).lastIndexWhere(knot -> knot <= t);
        
        Object[] result = new Object[getControlPoints().size()];
        for(int i = 0; i < getControlPoints().size(); ++i){
            result[i] = getControlPoints().get(i);
        }
        
        Integer n = getDegree();
        Array<Double> knots = getKnots();
        
        for(int k = 1; k <= n; ++k){
            for(int i = l; i >= l-n+k; --i){
                Double aki = (t - knots.get(i)) / (knots.get(i+n+1-k) - knots.get(i));
                V cp = (V)((V)result[i-1]).scalarMultiply(1.0-aki).add(((V)result[i]).scalarMultiply(aki));
                result[i] = cp;
            }
        }
        
        return (V)result[l];
    }
}
