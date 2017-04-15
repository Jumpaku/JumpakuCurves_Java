/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.fuzzy;

import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author Jumpaku
 */
public final class Grade implements Comparable<Grade> {
    
    public static Grade of(Double value){        
        return new Grade(value);
    }
    
    public static Grade clamped(Double value){
        return  of(FastMath.min(1.0, FastMath.max(0.0, value)));
    }
    
    public static Grade of(Integer value){        
        return of(value.doubleValue());
    }

    public static Grade of(Boolean b){
        return b ? TRUE : FALSE;
    }
    
    public static final Grade TRUE = of(1.0);
    
    public static final Grade FALSE = of(0.0);
    
    public static Grade and(Grade g1, Grade g2){
        return g1.compareTo(g2) <= 0 ? g1 : g2;
    }
    
    public static Grade or(Grade g1, Grade g2) {
        return g1.compareTo(g2) >= 0 ? g1 : g2;
    }
    
    public static Grade not(Grade g){
        return of(1.0 - g.getValue());
    }

    public static int compare(Grade g1, Grade g2){
        return Double.compare(g1.getValue(), g2.getValue());
    }
    
    public static String toString(Grade g){
        return g.getValue().toString();
    }
    
    public static Grade parseGrade(String s){
        return of(Double.parseDouble(s));
    }

    private final Double value;

    public Grade(Double value) {
        if((!Double.isFinite(value)) ||  value.compareTo(0.0) < 0 || value.compareTo(1.0) > 0)
            throw new IllegalArgumentException("d must be in [0.0, 1.0].");

        this.value = value;
    }
    
    
    public Double getValue(){
        return value;
    }

    public Grade and(Grade g){
        return and(this, g);
    }
    
    public Grade or(Grade g){
        return or(this, g);
    }
    
    public Grade not(){
        return not(this);
    }
    
    @Override public String toString() {
        return toString(this);
    }

    @Override public int compareTo(Grade g) {
        return compare(this, g);
    }    
}
