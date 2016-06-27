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
public class WeightedPoint1D implements WeightedPoint{

    private final Double weight;
    
    private final Point1D point;
    
    public WeightedPoint1D(Double x, Double w){
        this(new Point1D(x), w);
    }
    
    public WeightedPoint1D(Point1D p, Double w) {
        point = p;
        this.weight = w;
    }
    
    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public Point1D getPoint() {
        return point;
    }
}
