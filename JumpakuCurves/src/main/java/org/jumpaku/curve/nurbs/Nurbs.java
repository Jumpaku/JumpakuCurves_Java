/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.nurbs;

import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.DefinedOnInterval;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.ratioional.RationalBezier;

/**
 *
 * @author Jumpaku
 */
public interface Nurbs extends Curve, Differentiable, DefinedOnInterval<Nurbs>{
        
    @Override Interval getDomain();
    
    @Override Point evaluate(Double t);

    @Override Derivative differentiate();

    @Override Nurbs restrict(Interval i);

    Nurbs reverse();

    Array<? extends Point> getControlPoints();

    Array<Double> getWeights();
    
    Array<Tuple2<? extends Point, Double>> getWeightedPoints();
    
    Array<Double> getKnots();
    
    Integer getDegree();
    
    Nurbs insertKnot(Double t);
    
    Array<? extends RationalBezier> toRationalBeziers();
    
    Tuple2<? extends RationalBezier, ? extends RationalBezier> subdivide(Double t);
}
