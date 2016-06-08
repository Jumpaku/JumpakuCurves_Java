/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import java.util.List;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;

/**
 *
 * @author Jumpaku
 * @param <S> 座標空間の種類  Type of the space. 
 * @param <V> {@link BezierCurveBernstein#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link BezierCurveBernstein#evaluate(java.lang.Double)}.
 */
public class BezierCurveBernstein<S extends Space, V extends Vector<S>> extends AbstractBezierCurve<S, V> {
    
    private final javaslang.collection.Array<Double> combinations;
    
    public BezierCurveBernstein(List<V> cp) {
        super(cp);
        final Integer degree = cp.size() - 1;
        combinations = javaslang.collection.Array.rangeClosed(0, degree)
                .map(i -> binomialCoefficientDouble(degree, i));
    }
    
    @Override
    public final V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("Parameter t out of domain [0,1]");
        
        List<V> cps = getControlPoints();
        Integer degree = getDegree();

        if(t.compareTo(0.0) == 0){
            return cps.get(0);
        }
        if(t.compareTo(1.0) == 0){
            return cps.get(degree);
        }
        
        Double ct = Math.pow(1-t, degree);
        Vector<S> result = cps.get(0).getZero();
        
        for(int i = 0; i <= degree; ++i){
            result = result.add(cps.get(i).scalarMultiply(combinations.get(i)*ct));
            ct *= (t / (1 - t));
        }
        
        return (V)result;
    }
}