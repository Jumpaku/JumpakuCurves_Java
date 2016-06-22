/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.utils;

import java.util.function.UnaryOperator;
import org.apache.commons.math3.util.Precision;
import org.jumpaku.curves.domain.Closed;

/**
 *
 * @author ito tomohiko
 */
public class GoldenSectionSearch {
    private static final Double GOLD_INVERSE = 1.0/1.6180339887498948482045868343656381177203;

    private static final Integer MAX_ULP = 1;
    
    public static Double whereMinimum(UnaryOperator<Double> f, Closed interval){
        Double a = interval.getFrom();
        Double d = interval.getTo();
        Double b = d - (d - a)*GOLD_INVERSE;
        Double c = a + (d - a)*GOLD_INVERSE;
        while(!Precision.equals(a, d, MAX_ULP)){
            if(f.apply(b) < f.apply(c)){
                d = c;
            }
            else{
                a = b;
            }
            b = d - (d - a)*GOLD_INVERSE;
            c = a + (d - a)*GOLD_INVERSE;
        }
        return (a+d)/2;
    }
    
    public static Double wherehMinimum(UnaryOperator<Double> f, Double from, Double to){
        return whereMinimum(f, new Closed(from, to));
    } 
    
    public static Double whereMaximum(UnaryOperator<Double> f, Closed interval){
        return whereMinimum(x -> -f.apply(x), interval);
    }
    
    public static Double whereMaximum(UnaryOperator<Double> f, Double from, Double to){
        return GoldenSectionSearch.whereMaximum(f, new Closed(from, to));
    }
}