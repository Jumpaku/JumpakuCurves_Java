/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.fuzzy;

import java.util.Objects;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public final class Grade implements Comparable<Grade> {
    
    public static Grade of(Double value){        
        return new Grade(value);
    }
    
    public static Grade of(Boolean b){
        return b ? TRUE : FALSE;
    }
    
    public static final Grade TRUE = of(1.0);
    
    public static final Grade FALSE = of(0.0);
    
    public static Grade tureValue(){
        return TRUE;
    }
    
    public static Grade falseValue(){
        return FALSE;
    }
    
    public static Grade and(Grade g1, Grade g2){
        return of(Math.min(g1.doubleValue(), g2.doubleValue()));
    }
    
    public static Grade or(Grade g1, Grade g2) {
        return of(Math.max(g1.doubleValue(), g2.doubleValue()));
    }
    
    public static Grade not(Grade g){
        return of(1.0 - g.doubleValue());
    }

    public static int compare(Grade g1, Grade g2){
        return Double.compare(g1.doubleValue(), g2.doubleValue());
    }
    
    public static String toString(Grade g){
        return g.doubleValue().toString();
    }
    
    public static Grade parseGrade(String s){
        return of(Double.parseDouble(s));
    }
    
    public static Boolean equals(Grade g1, Grade g2, Double eps){
        return Precision.equals(g1.doubleValue(), g2.doubleValue(), eps);
    }
    
    public static Boolean equals(Grade g1, Grade g2, Integer ulp){
        return Precision.equals(g1.doubleValue(), g2.doubleValue(), ulp);
    }
    private final Double value;

    public Grade(Double d) {
        if(!Double.isFinite(d) ||  d.compareTo(0.0) < 0 || d.compareTo(1.0) > 0)
            throw new IllegalArgumentException("d must be in [0.0, 1.0].");
        
        this.value = d;
    }
    
    public Double doubleValue(){
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

    @Override
    public String toString() {
        return toString(this);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object g) {
        if (this == g) {
            return true;
        }
        if (g == null) {
            return false;
        }
        if (getClass() != g.getClass()) {
            return false;
        }
        final Grade other = (Grade) g;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
    
    public Boolean equals(Grade g, Double eps){
        return equals(this, g, eps);
    }
    
    public Boolean equals(Grade g, Integer ulp){
        return equals(this, g, ulp);
    }
    
    @Override
    public int compareTo(Grade g) {
        return compare(this, g);
    }    
}    

