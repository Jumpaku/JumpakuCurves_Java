/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import javaslang.collection.Array;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.curve.polyline.Polyline;
import org.jumpaku.fuzzy.Grade;

/**
 *
 * @author tomohiko
 */
public interface FuzzyCurve extends Curve{

    public static final Integer DEFAULT_FUZZY_MATCHING_POINTS = 30;
    
    public static FuzzyCurve crisp(Curve curve){
        return new FuzzyCurve() {
            @Override public FuzzyPoint evaluate(Double t) {
                return FuzzyPoint.crisp(curve.evaluate(t));
            }

            @Override public Interval getDomain() {
                return curve.getDomain();
            }
        };
    }
    
    @Override default FuzzyPoint apply(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
        
        return evaluate(t);
    }

    @Override FuzzyPoint evaluate(Double t);
    
    default Array<FuzzyPoint> evaluateAllByArcLengthParams(Integer n){
        return Polyline.approximate(this, n, Polyline.DEFAULT_ABSOLUTE_ACCURACY)
                .evaluateAllByArcLengthParams(n);
    }
    
    default Grade possibility(FuzzyCurve other){
        return possibility(other, DEFAULT_FUZZY_MATCHING_POINTS);
    }

    default Grade possibility(FuzzyCurve other, Integer fmps){
        return evaluateAllByArcLengthParams(fmps)
                .zipWith(other.evaluateAllByArcLengthParams(fmps), FuzzyPoint::possibility)
                .fold(Grade.TRUE, (a, b) -> a.and(b));
    }
    
    default Grade necessity(FuzzyCurve other){
        return necessity(other, DEFAULT_FUZZY_MATCHING_POINTS);
    }
    
    default Grade necessity(FuzzyCurve other, Integer fmps){
        return evaluateAllByArcLengthParams(fmps)
                .zipWith(other.evaluateAllByArcLengthParams(fmps), FuzzyPoint::necessity)
                .fold(Grade.TRUE, (a, b) -> a.and(b));
    }
}
