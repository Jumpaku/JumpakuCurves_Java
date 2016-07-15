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
public class WeightedPoint3D extends Point3D implements WeightedPoint{
    
    private final Double weight;
    
    public WeightedPoint3D(Double w, Double x, Double y, Double z) {
        this(w, new Point3D(x, y, z));
    }
    
    public WeightedPoint3D(Double w, Point3D p){
        super(p);
        weight = w;
    }

    @Override
    public Double getWeight() {
        return weight;
    }
    
    @Override
    public Point3D getProduct() {
        return new Point3D(WeightedPoint.super.getProduct());
    }
}
