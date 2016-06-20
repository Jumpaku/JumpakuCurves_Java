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

/**
 *
 * @author jumpaku
 * @param <S>
 * @param <V>
 */
public final class BSplineCurveReduction<S extends Space, V extends Vector<S>> extends AbstractBSplineCurve<S, V>{

    public BSplineCurveReduction(Array<Double> knots, Array<V> controlPoints, Integer degree) {
        super(knots, controlPoints, degree);
    }
    
    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t is out of domain");
        
        return (V) Stream.ofAll(getControlPoints()).zipWithIndex().map(cpi -> cpi.transform(
                        (cp, i) -> cp.scalarMultiply(BSplineCurve.bSplineBasis(getDegree(), i.intValue(), t, getKnots()))))
                .reduce((v1, v2) -> v1.add(v2));
    }
}
