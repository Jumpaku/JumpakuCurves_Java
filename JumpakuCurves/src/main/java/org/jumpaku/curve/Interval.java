/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.apache.commons.math3.util.FastMath;


/**
 *
 * @author Jumpaku
 */
public interface Interval extends Domain{
    
    Interval ZERO_ONE = Interval.closed(0.0, 1.0);
    
    static Interval closed(Double begin, Double end){
        return new Interval() {
            @Override public Boolean includes(Double t) {
                return getBegin().compareTo(t) <= 0 && getEnd().compareTo(t) >= 0;
            }

            @Override public Double getBegin() {
                return begin;
            }

            @Override public Double getEnd() {
                return end;
            }
            
            @Override public String toString(){
                return Interval.toJson(this);
            }
        };
    }
    
    static String toJson(Interval i){
        return JsonInterval.CONVERTER.toJson(i);
    }
    
    static Option<Interval> fromJson(String json){
        return JsonInterval.CONVERTER.fromJson(json);
    }
    
    default Array<Double> sample(Integer n){
        if(n < 2) {
            throw new IllegalArgumentException("n must be grater than 1, but n = " + n);
        }
        
        return Stream.range(0, n)
                .map(i -> (n-1-i)/(n.doubleValue()-1.0)*getBegin() + i/(n.doubleValue()-1.0)*getEnd())
                .toArray();
    }
    
    default Array<Double> sample(Double delta){
        return sample((int)FastMath.ceil((getEnd()-getBegin())/delta));
    }

    @Override
    Boolean includes(Double t);
    
    default Boolean includes(Interval i){
        return getBegin() <= i.getBegin() && i.getEnd() <= getEnd();
    }
    
    Double getBegin();
    
    Double getEnd();
}
