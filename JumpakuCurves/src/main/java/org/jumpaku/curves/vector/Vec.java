/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Jumpaku
 */
public interface Vec{

    public static Vec zero(Integer dimention){
        return new Vec(){
            @Override
            public Vec add(Vec v) {
                return v;
            }

            @Override
            public Vec scale(Double a) {
                return this;
            }

            @Override
            public Integer getDimention() {
                return dimention;
            }

            @Override
            public Double get(Integer i) {
                return 0.0;
            }

            @Override
            public Double dot(Vec v) {
                return 0.0;
            }            

            @Override
            public Vector<? extends Space> getVector() {
                if(dimention <= 0 || 3 < dimention)
                    throw new IllegalStateException("dimention of this must be 1, 2, or 3");
                
                return dimention == 1 ? Vector1D.ZERO : dimention == 2 ? Vector2D.ZERO : Vector3D.ZERO;
            }
        };
    }
    
    static Vec add(Double a, Vec v1, Double b, Vec v2){
        return v1.scale(a).add(b, v2);
    }
    
    Vec add(Vec v);
    
    Vec scale(Double a);
    
    Integer getDimention();
    
    Double get(Integer i);
    
    Double dot(Vec v);
    
    Vector<? extends Space> getVector();
    
    default Vec sub(Vec v){
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return add(v.negate());
    }
    
    default Double square(){
        return dot(this);
    }
    
    default Double length(){
        return Math.sqrt(square());
    }
    
    default Vec add(Double a, Vec v){
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return add(v.scale(a));
    }
    
    default Vec negate(){
        return scale(-1.0);
    }
    
    default Vec normalize(){
        return scale(1.0/length());
    }
}
