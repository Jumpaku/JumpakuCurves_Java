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
public class WeightedPoint1D extends Point1D implements WeightedPoint{

    private final Double weight;
    
    public WeightedPoint1D(Double w, Double x){
        this(w, new Point1D(x));
    }
    
    public WeightedPoint1D(Double w, Point1D p) {
        super(p);
        this.weight = w;
    }
    
    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public Point1D getProduct() {
        return new Point1D(WeightedPoint.super.getProduct());
    }
    
}
