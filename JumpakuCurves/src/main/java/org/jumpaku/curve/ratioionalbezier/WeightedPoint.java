/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import org.jumpaku.affine.Divideable;
import org.jumpaku.affine.FuzzyPoint;

/**
 *
 * @author tomohiko
 */
public class WeightedPoint implements Divideable<WeightedPoint>{
    
        private final Double w;
        
        private final FuzzyPoint point;

        public WeightedPoint(Double w, FuzzyPoint point) {
            this.w = w;
            this.point = point;
        }
        
        public Double getW(){
            return w;
        }
        
        public FuzzyPoint getPoint(){
            return point;
        }
        
        @Override
        public WeightedPoint divide(Double t, WeightedPoint wp){
            double w = (1-t)*getW() + t*wp.getW();
            return new WeightedPoint(w, getPoint().divide(t*wp.getW()/w, wp.getPoint()));
        }
    }
