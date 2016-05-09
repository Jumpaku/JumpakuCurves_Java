/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.utils;

import org.apache.commons.math3.geometry.euclidean.twod.Segment;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public class GeomUtils {
    private GeomUtils(){}
    public static Vector2D internallyDivide(Double m, Double n, Vector2D start, Vector2D end){        
        return new Vector2D(n/(n+m), start, m/(n+m), end);
    }
    public static Vector2D internallyDivide(Double m, Double n, Segment s){        
        return new Vector2D(n/(n+m), s.getStart(), m/(n+m), s.getEnd());
    }
    public static Vector2D internallyDivide(Double t, Vector2D start, Vector2D end){
        if(t < 0 || 1 < t){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        return new Vector2D(1.0-t, start, t, end);
    }
    public static Vector2D internallyDivide(Double t, Segment s){        
        if(t < 0 || 1 < t){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        return new Vector2D(1.0-t, s.getStart(), t, s.getEnd());
    }
}
