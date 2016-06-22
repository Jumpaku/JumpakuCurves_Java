/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Arrays;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.util.Pair;
import org.jumpaku.curves.utils.GoldenSectionSearch;

/**
 *
 * @author ito tomohiko
 */
public class Sample {
    public static void main(String[] args) {
        Double p = GoldenSectionSearch.whereMaximum(x -> -(x-1)*(x-1) + 3, 0.0, 10.0);
        System.out.println(p);
    }
}
