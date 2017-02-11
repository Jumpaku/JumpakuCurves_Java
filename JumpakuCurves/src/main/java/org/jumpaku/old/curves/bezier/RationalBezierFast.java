/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.old.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public class RationalBezierFast extends AbstractRationalBezier {

    public RationalBezierFast(Array<? extends Point> cps, Array<Double> ws, Integer dimention) {
        super(cps, ws, dimention);
    }
    
    @Override
    public Point evaluate(Double t) {
        if(!getDomain().contains(t))
            throw new IllegalArgumentException("Parameter t out of domain [0,1]");
        
        return Point.of(getProductBezier().evaluate(t).getVec().scale(1.0/getWeightBezier().evaluate(t).getX()));
    }
}
