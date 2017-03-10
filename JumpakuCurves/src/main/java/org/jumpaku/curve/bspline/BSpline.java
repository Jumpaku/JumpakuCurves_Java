/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bspline;

import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.DefinedOnInterval;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.bezier.Bezier;

/**
 *
 * @author Jumpaku
 */
public interface BSpline extends Curve, Differentiable, DefinedOnInterval<BSpline>{
        
    @Override Interval getDomain();
    
    @Override Point evaluate(Double t);

    @Override Derivative differentiate();

    @Override BSpline restrict(Interval i);

    BSpline reverse();

    Array<? extends Point> getControlPoints();

    Array<Double> getKnots();
    
    Integer getDegree();
    
    BSpline insertKnot(Double t);
    
    Array<? extends Bezier> toBeziers();
    
    Tuple2<? extends Bezier, ? extends Bezier> subdivide(Double t);
}
