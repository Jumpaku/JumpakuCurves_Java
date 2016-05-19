/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.utils;

import java.util.List;
import java.util.function.UnaryOperator;
import javaslang.collection.Stream;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;

/**
 *
 * @author ito
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
}
