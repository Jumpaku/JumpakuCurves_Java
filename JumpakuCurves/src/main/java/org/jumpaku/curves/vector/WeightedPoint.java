/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

/**
 *
 * @author Jumpaku
 */
public interface WeightedPoint extends Point{
    
    public static WeightedPoint of(Point p, Double w){
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
    
    Double getWeight();

    default Point getPoint(){
        return this;
    }
    
    default Point getProduct(){
        return Point.of(getPoint().getVec().scale(getWeight()));
    }
}
