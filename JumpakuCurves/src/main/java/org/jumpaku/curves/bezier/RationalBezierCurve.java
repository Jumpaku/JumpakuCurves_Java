/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.Curve;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Domain;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Vec;
import org.jumpaku.curves.vector.WeightedPoint;

/**
 *
 * @author Jumpaku
 */
public interface RationalBezierCurve extends Curve{
    
    Interval DOMAIN = new Closed(0.0, 1.0);
    
    Array<Double> getWeights();
    
    Array<? extends WeightedPoint> getWeightedPoints();

    Array<? extends Point> getControlPoints();

    default Integer getDegree(){
        return getControlPoints().size() - 1;
    }

    @Override
    Integer getDimention();

    @Override
    default Interval getDomain(){
        return DOMAIN;
    }

    @Override
    Point evaluate(Double t);

    Vec computeTangent(Double t);

    Array<? extends RationalBezierCurve> divide(Double t);

    RationalBezierCurve elevate();

    RationalBezierCurve reduce();

    RationalBezierCurve reverse();
}
