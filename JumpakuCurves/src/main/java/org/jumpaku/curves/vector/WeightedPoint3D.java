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
    
    public WeightedPoint3D(Double x, Double y, Double z, Double w) {
        this(new Point3D(x, y, z), w);
    }
    
    public WeightedPoint3D(Point3D p, Double w){
        super((Vec3)p.getVector());
        weight = w;
    }

    @Override
    public Double getWeight() {
        return weight;
    }
}
