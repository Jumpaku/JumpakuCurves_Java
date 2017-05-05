/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import javaslang.collection.Array;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.polyline.Polyline;
import org.jumpaku.fuzzy.Grade;

/**
 *
 * @author jumpaku
 */
public interface FuzzyCurve extends Curve{

    Integer DEFAULT_FUZZY_MATCHING_POINTS = 30;
    
    @Override
    Point evaluate(Double t);
    
    default Array<Point> evaluateAllArcLength(Integer n){
        return Polyline.approximate(this, n)
                .evaluateAllArcLength(n);
    }
    
    default Grade possibility(FuzzyCurve other){
        return possibility(other, DEFAULT_FUZZY_MATCHING_POINTS);
    }

    default Grade possibility(FuzzyCurve other, Integer fmps){
        return evaluateAllArcLength(fmps)
                .zipWith(other.evaluateAllArcLength(fmps), Point::possibility)
                .fold(Grade.TRUE, (a, b) -> a.and(b));
    }
    
    default Grade necessity(FuzzyCurve other){
        return necessity(other, DEFAULT_FUZZY_MATCHING_POINTS);
    }
    
    default Grade necessity(FuzzyCurve other, Integer fmps){
        return evaluateAllArcLength(fmps)
                .zipWith(other.evaluateAllArcLength(fmps), Point::necessity)
                .fold(Grade.TRUE, (a, b) -> a.and(b));
    }
}
