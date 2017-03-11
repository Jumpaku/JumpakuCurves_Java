/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.FuzzyCurve;
import org.jumpaku.curve.Interval;

/**
 *
 * @author Jumpaku
 */
public interface RationalBezier extends FuzzyCurve, Differentiable{
    
    @Override Interval getDomain();
    
    @Override FuzzyPoint evaluate(Double t);

    @Override Derivative differentiate();

    @Override RationalBezier restrict(Interval i);

    RationalBezier reverse();

    Array<FuzzyPoint> getControlPoints();
    
    Array<Double> getWeights();
    
    Array<Tuple2<FuzzyPoint, Double>> getWeightedPoints();

    Integer getDegree();
    
    RationalBezier elevate();
    
    RationalBezier reduce();
    
    Tuple2<RationalBezier, RationalBezier> subdivide(Double t);
}
