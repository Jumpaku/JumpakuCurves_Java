/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public final class BSplineCurveReduction extends AbstractBSplineCurve{

    public BSplineCurveReduction(Array<Double> knots, Array<Point> controlPoints, Integer degree, Integer dimention) {
        super(knots, controlPoints, degree, dimention);
    }
    
    @Override
    public Point evaluate(Double t) {
        if(!getDomain().contains(t))
            throw new IllegalArgumentException("t is out of domain");
        
        return Point.of(Stream.ofAll(getControlPoints()).zipWithIndex().map(cpi -> cpi.transform(
                        (cp, i) -> cp.getVec().scale(BSplineCurve.bSplineBasis(getDegree(), i.intValue(), t, getKnots()))))
                .reduce((v1, v2) -> v1.add(v2)));
    }
}
