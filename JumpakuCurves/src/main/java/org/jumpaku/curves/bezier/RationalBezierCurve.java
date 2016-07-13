/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Vec;
import org.jumpaku.curves.vector.WeightedPoint;

/**
 *
 * @author Jumpaku
 */
public interface RationalBezierCurve extends BezierCurve {
    
    Array<Double> getWeights();
    
    Array<? extends WeightedPoint> getWeightedPoints();

    @Override
    public Array<? extends Point> getControlPoints();

    @Override
    public Integer getDegree();

    @Override
    public Integer getDimention();

    @Override
    public Interval getDomain();

    @Override
    public Point evaluate(Double t);

    @Override
    public Vec computeTangent(Double t);

    @Override
    public Array<? extends BezierCurve> divide(Double t);

    @Override
    public BezierCurve elevate();

    @Override
    public BezierCurve reduce();

    @Override
    public BezierCurve reverse();
    
    
}
