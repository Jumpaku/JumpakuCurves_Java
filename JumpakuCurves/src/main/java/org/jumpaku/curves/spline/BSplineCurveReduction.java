/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import java.util.List;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author jumpaku
 * @param <S>
 * @param <V>
 */
public final class BSplineCurveReduction<S extends Space, V extends Vector<S>> extends AbstractBSplineCurve<S, V>{

    public BSplineCurveReduction(List<Double> knots, List<V> controlPoints, Integer degree) {
        super(knots, controlPoints, degree);
    }
    
    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t is out of domain");
        
        return (V) Stream.ofAll(getControlPoints()).zipWithIndex().map(cpi -> cpi.transform(
                        (cp, i) -> cp.scalarMultiply(bSplineBasis(i.intValue(), getDegree(), getKnots(), t))))
                .reduce((v1, v2) -> v1.add(v2));
    }

    private static Double coefficient(Double a, Double b, Double c, Double d){
        return c.compareTo(d) == 0 ? 0 : (a-b)/(c-d);
    }
    
    private static Double bSplineBasis(Integer j, Integer n, List<Double> knots, Double t){
        if(n == 0)
            return (knots.get(j).compareTo(t) <= 0 && knots.get(j+1).compareTo(t) > 0) ? 1.0 : 0.0;
        
        Double left = coefficient(t, knots.get(j), knots.get(j+n), knots.get(j));
        if(left.compareTo(0.0) != 0){
            left = left * bSplineBasis(j, n-1, knots, t);
        }
        Double right = coefficient(knots.get(j+n+1), t, knots.get(j+n+1), knots.get(j+1));
        if(right.compareTo(0.0) != 0){
            right = right * bSplineBasis(j+1, n-1, knots, t);
        }
        return left + right;
    }
    
}
