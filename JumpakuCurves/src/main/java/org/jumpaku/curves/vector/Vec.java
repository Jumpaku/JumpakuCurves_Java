/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public interface Vec{

    public static Vec zero(Integer dimention){
        return new Vec(){
            @Override
            public Vec add(Vec v) {
                if(getDimention() != v.getDimention())
                    throw new IllegalArgumentException("dimention miss match");
        
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
                if(i < 0 || dimention <= i)
                    throw new IllegalArgumentException("index is out of bounds");
                
                return 0.0;
            }

            @Override
            public Double dot(Vec v) {
                if(getDimention() != v.getDimention())
                    throw new IllegalArgumentException("dimention miss match");
        
                return 0.0;
            }            

            /*@Override
            public Vector<? extends Space> getVector() {
                if(dimention <= 0 || 3 < dimention)
                    throw new IllegalStateException("dimention of this must be 1, 2, or 3");
                
                return dimention == 1 ? Vector1D.ZERO : dimention == 2 ? Vector2D.ZERO : Vector3D.ZERO;
            }*/

            @Override
            public Boolean equals(Vec v, Double eps) {
                if(dimention == v.getDimention()){
                    for(int i = 0; i < dimention; ++i){
                        if(!Precision.equals(0.0, v.get(i), eps))
                            return false;
                    }
                    return true;
                }
                return false;
            }

            @Override
            public Boolean equals(Vec v, Integer ulp) {
                if(dimention == v.getDimention()){
                    for(int i = 0; i < dimention; ++i){
                        if(!Precision.equals(0.0, v.get(i), ulp))
                            return false;
                    }
                    return true;
                }
                return false;
            }
        };
    }
    
    public static Vec add(Double a, Vec v1, Double b, Vec v2){
        if(v1.getDimention() != v2.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return v1.scale(a).add(b, v2);
    }
    
    Vec add(Vec v);
    
    Vec scale(Double a);
    
    Integer getDimention();
    
    Double get(Integer i);
    
    Double dot(Vec v);
    
    Boolean equals(Vec v, Double eps);
    
    Boolean equals(Vec v, Integer ulp);
    
    default Vec sub(Vec v){
        if(getDimention() != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return add(v.negate());
    }
    
    default Vec sub(Double a, Vec v){
        if(getDimention() != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        return sub(v.scale(a));
    }
    
    default Double square(){
        return dot(this);
    }
    
    default Double length(){
        return Math.sqrt(square());
    }
    
    default Vec add(Double a, Vec v){
        if(getDimention() != v.getDimention())
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
