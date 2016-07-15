/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public interface WeightedPoint extends Point{
    
    public static WeightedPoint of(Double w, Point p){
        return new WeightedPoint() {
            @Override
            public Double getWeight() {
                return w;
            }

            @Override
            public Vec getVec() {
                return p.getVec();
            }
        };
    }
    
    public static WeightedPoint of(Double w, Double... elements){
        return WeightedPoint.of(w, Point.of(elements));
    }
    
    Double getWeight();
    
    default Point getProduct(){
        return Point.of(getVec().scale(getWeight()));
    }

    default Boolean equals(WeightedPoint p, Double eps){
        return Point.super.equals(p, eps) && Precision.equals(getWeight(), p.getWeight(), eps);
    }
    
    default Boolean equals(WeightedPoint p, Integer ulp){
        return Point.super.equals(p, ulp) && Precision.equals(getWeight(), p.getWeight(), ulp);
    }
    
    
}
