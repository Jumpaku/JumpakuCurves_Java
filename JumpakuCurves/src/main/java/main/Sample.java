/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Arrays;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 *
 * @author ito tomohiko
 */
public class Sample {
    public static void main(String[] args) {
        PolynomialSplineFunction s = new SplineInterpolator().interpolate(new double[]{1.0,2.0,3.0,4.0}, new double[]{3.0,4.0,2.0,1.0});
        Arrays.stream(s.getKnots())
                .forEach(System.out::println);
        
        System.out.println(s.value(0.0));
    }
}
