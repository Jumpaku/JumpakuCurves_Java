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
public class WeightedPoint2D extends Point2D implements WeightedPoint{

    private final Double weight;
    
    public WeightedPoint2D(Double w, Double x, Double y) {
        this(w, new Point2D(x, y));
    }
    
    public WeightedPoint2D(Double w, Point2D p){
        super(p);
        weight = w;
    }

    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public Point2D getProduct() {
        return new Point2D(WeightedPoint.super.getProduct());
    }
}
