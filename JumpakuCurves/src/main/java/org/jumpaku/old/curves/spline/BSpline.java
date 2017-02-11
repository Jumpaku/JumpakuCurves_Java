/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.spline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.old.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public interface BSpline extends Spline{
    
    /**
     * 
     * @param n
     * @param j
     * @param t
     * @param knots
     * @return 
     */
    public static Double bSplineBasis(Integer n, Integer j, Double t, Array<Double> knots){
        if(n == 0)
            return (knots.get(j).compareTo(t) <= 0 && knots.get(j+1).compareTo(t) > 0) ? 1.0 : 0.0;
        
        
        Double left = bSplineBasisHelper(t, knots.get(j), knots.get(j+n), knots.get(j));
        if(left.compareTo(0.0) != 0){
            left = left * bSplineBasis(n-1, j, t, knots);
        }
        Double right = bSplineBasisHelper(knots.get(j+n+1), t, knots.get(j+n+1), knots.get(j+1));
        if(right.compareTo(0.0) != 0){
            right = right * bSplineBasis(n-1, j+1, t, knots);
        }
        return left + right;
    }
    
    @Deprecated
    public static Double bSplineBasisHelper(Double a, Double b, Double c, Double d){
        return Double.isFinite((a-b)/(c-d)) ? (a-b)/(c-d) : 0.0;
    }
    
    public static BSpline create(Array<Double> knots, Array<Point> controlPoints, Integer degree, Integer dimention){
        return new BSplineDeBoor(knots, controlPoints, degree, dimention);
    }

    /**
     *
     * @param degree the value of degree
     * @param begin the value of begin
     * @param end the value of end
     * @param maxKnotInterval the value of maxKnotInterval
     * @return 
     */
    public static Array<Double> createUniformedClampedKnots(Integer degree, Double begin, Double end, Double maxKnotInterval) {
        long n = (long) (FastMath.ceil((end - begin) / maxKnotInterval));
        return Stream.fill(degree, () -> begin).appendAll(Stream.rangeClosed(0, n).map((Long i) -> begin * (n - i) / n + end * i / n)).appendAll(Stream.fill(degree, () -> end)).toArray();
    }
    
    BSpline insertKnot(Double u);    
}
