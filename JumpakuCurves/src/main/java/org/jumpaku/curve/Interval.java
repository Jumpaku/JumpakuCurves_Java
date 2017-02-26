/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.util.FastMath;


/**
 *
 * @author Jumpaku
 */
public interface Interval extends Domain{
    
    static final JsonInterval CONVERTER = new JsonInterval();

    static Interval ZERO_ONE = Interval.of(0.0, 1.0);
    
    static Interval of(Double begin, Double end){
        return new Interval() {
            @Override public Boolean includes(Double t) {
                return getbegin().compareTo(t) <= 0 && getEnd().compareTo(t) >= 0;
            }

            @Override public Double getbegin() {
                return begin;
            }

            @Override public Double getEnd() {
                return end;
            }
            
            @Override public String toString(){
                return Interval.toString(this);
            }
        };
    }
    
    static String toString(Interval i){
        return CONVERTER.toJson(i);
    }
    
    default Array<Double> sample(Integer n){
        if(n < 2)
            throw new IllegalArgumentException("n must be grater than 1, but n = " + n);
        
        return Stream.range(0, n)
                .map(i -> (n-1-i)/(n.doubleValue()-1.0)*getbegin() + i/(n.doubleValue()-1.0)*getEnd())
                .toArray();
    }
    
    default Array<Double> sample(Double delta){
        return sample((int)FastMath.ceil((getEnd()-getbegin())/delta));
    }

    @Override Boolean includes(Double t);
    
    default Boolean includes(Interval i){
        return getbegin().compareTo(i.getbegin()) <= 0 && getEnd().compareTo(i.getEnd()) >= 0;
    }
    
    Double getbegin();
    
    Double getEnd();
}
