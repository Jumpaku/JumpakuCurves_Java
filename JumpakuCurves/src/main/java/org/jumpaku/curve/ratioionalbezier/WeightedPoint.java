/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import org.jumpaku.affine.Divideable;
import org.jumpaku.affine.Point;

/**
 *
 * @author jumpaku
 */
public class WeightedPoint implements Divideable<WeightedPoint>{
    
        private final Double weight;
        
        private final Point point;

        public WeightedPoint(Double weight, Point point) {
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
            return new WeightedPoint(w, getPoint().divide(t*wp.getWeight()/w, wp.getPoint()));
        }
    }
