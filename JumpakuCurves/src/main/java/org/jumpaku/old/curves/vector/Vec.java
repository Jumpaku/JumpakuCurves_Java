/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.vector;

import java.util.Arrays;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public interface Vec{

    public static Vec of(Double... elements){
        return new Vec(){
            @Override
            public Vec add(Vec v) {
                if(getDimention() != v.getDimention())
                    throw new IllegalArgumentException("dimention miss match");
                
                Double[] result = new Double[getDimention()];
                for(int i = 0; i < getDimention(); ++i){
                    result[i] = get(i) + v.get(i);
                }
                return of(result);
            }

            @Override
            public Vec scale(Double a) {
                Double[] result = new Double[getDimention()];
                for(int i = 0; i < getDimention(); ++i){
                    result[i] = get(i)*a;
                }
                return of(result);
            }

            @Override
            public Integer getDimention() {
                return elements.length;
            }

            @Override
            public Double get(Integer i) {
                if(i < 0 || getDimention() <= i)
                    throw new IllegalArgumentException("index is out of bounds");
                
                return elements[i];
            }

            @Override
            public Double dot(Vec v) {
                if(getDimention() != v.getDimention())
                    throw new IllegalArgumentException("dimention miss match");
                
                Double result = 0.0;
                for(int i = 0; i < getDimention(); ++i){
                    result += get(i)*v.get(i);
                }
                return result;
            }            
        };
    }
    
    public static Vec zero(Integer dimention){
        Double[] z = new Double[dimention];
        Arrays.fill(z, 0.0);
        return of(z);
    }
    
    public static Vec add(Double a, Vec v1, Double b, Vec v2){
        if(v1.getDimention() != v2.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return v1.scale(a).add(b, v2);
    }
    

    public static Boolean equals(Vec v1, Vec v2, Double eps) {
        if(v1.getDimention() == v2.getDimention()){
            for(int i = 0; i < v1.getDimention(); ++i){
                if(!Precision.equals(v1.get(i), v2.get(i), eps))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static Boolean equals(Vec v1, Vec v2, Integer ulp) {
        if(v1.getDimention() == v2.getDimention()){
            for(int i = 0; i < v1.getDimention(); ++i){
                if(!Precision.equals(v1.get(i), v2.get(i), ulp))
                    return false;
            }
            return true;
        }
        return false;
    }
    
    Vec add(Vec v);
    
    Vec scale(Double a);
    
    Integer getDimention();
    
    Double get(Integer i);
    
    Double dot(Vec v);
    
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
