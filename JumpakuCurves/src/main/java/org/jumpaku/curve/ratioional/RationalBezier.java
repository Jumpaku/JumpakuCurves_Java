/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioional;

import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.DefinedOnInterval;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.Interval;

/**
 *
 * @author Jumpaku
 */
public interface RationalBezier extends Curve, Differentiable, DefinedOnInterval<RationalBezier>{
    
    @Override Interval getDomain();
    
    @Override Point evaluate(Double t);

    @Override Derivative differentiate();

    @Override RationalBezier restrict(Interval i);

    RationalBezier reverse();

    Array<? extends Point> getControlPoints();
    
    Array<Double> getWeights();
    
    Array<Tuple2<? extends Point, Double>> getWeightedPoints();

    Integer getDegree();
    
    RationalBezier elevate();
    
    RationalBezier reduce();
    
    Tuple2<? extends RationalBezier, ? extends RationalBezier> subdivide(Double t);
}
