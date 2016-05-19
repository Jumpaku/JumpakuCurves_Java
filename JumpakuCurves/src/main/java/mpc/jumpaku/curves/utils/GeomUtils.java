/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.utils;

import java.util.Arrays;
import java.util.List;
import mpc.jumpaku.curves.beziercurve.BezierCurve;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.MonotoneChain;

/**
 *
 * @author ito
 */
public class GeomUtils {
    private GeomUtils(){}
    public static <V extends Vector> V internallyDivide(Double t, V start, V end){
        if(t < 0 || 1 < t){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        return (V) start.scalarMultiply(1.0-t).add(end.scalarMultiply(t));
    }
    public static <V extends Vector> V internallyDivide(Double m, Double n, V start, V end){
        return (V) start.scalarMultiply(n/(m+n)).add(end.scalarMultiply(m/(m+n)));
    }
    
    public static <V extends Vector> V scalingAdd(Double a1, V v1, Double a2, V v2){
        return (V) v1.scalarMultiply(a1).add(v2.scalarMultiply(a2));
    }
    
    public static List<Vector2D> createConvexHull(BezierCurve<Euclidean2D, Vector2D> bezierCurve){
        return Arrays.asList(new MonotoneChain().generate(bezierCurve.getControlPoints()).getVertices());
    }
}
