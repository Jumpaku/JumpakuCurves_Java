/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

/**
 *
 * @author jumpaku
 */
public final class WeightedPoint implements Divideable<WeightedPoint>{
    
    private final Double weight;

    private final Point point;

    public WeightedPoint(Point point, Double weight) {
        this.weight = weight;
        this.point = point;
    }

    public Double getWeight(){
        return weight;
    }

    public Point getPoint(){
        return point;
    }

    @Override
    public WeightedPoint divide(Double t, WeightedPoint wp){
        double w = (1-t)*getWeight() + t*wp.getWeight();
        return new WeightedPoint(getPoint().divide(t*wp.getWeight()/w, wp.getPoint()), w);
    }

    @Override public String toString() {
        return JsonWeightedPoint.CONVERTER.toJson(this);
    }
}
