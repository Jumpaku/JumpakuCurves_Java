/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.utils;

import java.util.List;
import java.util.function.UnaryOperator;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;

/**
 *
 * @author Jumpaku
 */
public class MathUtils {
    private MathUtils(){}
    
    public static Double bernstein(Integer n, Integer k, Double t){
        return binomialCoefficientDouble(n, k)*Math.pow(t, k)*Math.pow(1-t, n-k);
    }
    public static UnaryOperator<Double> bernstain(Integer n, Integer k){
        Double combination = binomialCoefficientDouble(n, k);
        return t -> combination*Math.pow(t, k)*Math.pow(1-t, n-k);
    }
    
    
    public static List<Double> combinations(Integer n){
        return Stream.rangeClosed(0, n).map(i -> binomialCoefficientDouble(n, i)).toJavaList();
    }
    
    public static Double bSplineBasis(Integer n, Integer j, Double t, Array<Double> knots){
        if(n == 0)
            return (knots.get(j).compareTo(t) <= 0 && knots.get(j+1).compareTo(t) > 0) ? 1.0 : 0.0;
        
        Double left = bSplineBasisDiv(t, knots.get(j), knots.get(j+n), knots.get(j));
        if(left.compareTo(0.0) != 0){
            left = left * bSplineBasis(n-1, j, t, knots);
        }
        Double right = bSplineBasisDiv(knots.get(j+n+1), t, knots.get(j+n+1), knots.get(j+1));
        if(right.compareTo(0.0) != 0){
            right = right * bSplineBasis(n-1, j+1, t, knots);
        }
        return left + right;
    }
    private static Double bSplineBasisDiv(Double a, Double b, Double c, Double d){
        return c.compareTo(d) == 0 ? 0 : (a-b)/(c-d);
    }
}
