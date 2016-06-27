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
public class WeightedPoint3D implements WeightedPoint{
    
    private final Double weight;
    
    private final Point3D point;
    
    public WeightedPoint3D(Double x, Double y, Double z, Double w) {
        this(new Point3D(x, y, z), w);
    }
    
    public WeightedPoint3D(Point3D p, Double w){
        point = p;
        weight = w;
    }

    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public Point getPoint() {
        return point;
    }
}
