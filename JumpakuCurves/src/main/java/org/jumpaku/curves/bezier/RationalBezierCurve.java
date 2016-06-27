/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.Curve;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.WeightedPoint;

/**
 *
 * @author Jumpaku
 */
public interface RationalBezierCurve extends Curve {
    
    Array<Double> getWeights();
    
    Array<WeightedPoint> getWeightedPoints();

    public Array<? extends Point> getControlPoints();

    public Integer getDegree();

    @Override
    public Integer getDimention();

    @Override
    public Interval getDomain();

    @Override
    public Point evaluate(Double t);
}
