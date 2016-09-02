/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.vector.WeightedPoint;
import org.jumpaku.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public class RationalBezierCurveBernstein extends AbstractRationalBezierCurve {

    public RationalBezierCurveBernstein(Array<? extends WeightedPoint> wcps, Integer dimention) {
        super(wcps, dimention);
    }

    public RationalBezierCurveBernstein(Array<? extends Point> cps, Array<Double> weights, Integer dimention) {
        this(cps.zip(weights).map(cpw -> WeightedPoint.of(cpw._2(), cpw._1())), dimention);
    }
    
    @Override
    public Point evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("Parameter t out of domain [0,1]");
        
        return Point.of(getProductBezier().evaluate(t).getVec().scale(1.0/getWeightBezier().evaluate(t).getX()));
    }
}
