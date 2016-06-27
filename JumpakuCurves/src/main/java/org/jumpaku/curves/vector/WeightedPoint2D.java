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
public class WeightedPoint2D implements WeightedPoint{

    private final Double weight;
    
    private final Point2D point;
    
    public WeightedPoint2D(Double x, Double y, Double w) {
        this(new Point2D(x, y), w);
    }
    
    public WeightedPoint2D(Point2D p, Double w){
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
