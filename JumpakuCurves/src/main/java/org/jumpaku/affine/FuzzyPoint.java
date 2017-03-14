/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import javaslang.control.Option;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.fuzzy.Grade;
import org.jumpaku.fuzzy.Membership;

/**
 *
 * @author Jumpaku
 */
public interface FuzzyPoint extends Membership<FuzzyPoint, Point>, Point{
    
    static FuzzyPoint of(FuzzyVector v){
        return new FuzzyPoint() {
            @Override public FuzzyVector toVector() {
                return v;
            }

            @Override public String toString(){
                return FuzzyPoint.toJson(this);
            }
        };
    }
    
    static FuzzyPoint of(Point p, Double r){
        return of(FuzzyVector.of(p.toVector(), r));
    }

    static FuzzyPoint of(Double x, Double y, Double z, Double r){
        return of(Point.of(x, y, z), r);
    }
    
    static FuzzyPoint of(Double x, Double y, Double r){
        return of(Point.of(x, y, 0.0), r);
    }
    
    static FuzzyPoint of(Double x, Double r){
        return of(Point.of(x, 0.0), r);
    }

    static FuzzyPoint crisp(Double x, Double y, Double z){
        return of(x, y, z, 0.0);
    }
    
    static FuzzyPoint crisp(Double x, Double y){
        return of(x, y, 0.0);
    }    
    
    static FuzzyPoint crisp(Double x){
        return of(x, 0.0);
    }    
    
    static FuzzyPoint crisp(Point p){
        return of(p, 0.0);
    }

    static String toJson(FuzzyPoint p){
        return JsonFuzzyPoint.CONVERTER.toJson(p);
    }
    
    static Option<FuzzyPoint> fromJson(String json){
        return JsonFuzzyPoint.CONVERTER.fromJson(json);
    }

    default Double getR() {
        return toVector().getR();
    }
    
    @Override FuzzyVector toVector();

    @Override default FuzzyPoint move(Vector v) {
        return of(toVector().add(v));
    }

    @Override default FuzzyVector diff(Point p) {
        return toVector().sub(p.toVector());
    }

    @Override default Grade membership(Point p) {
        return toVector().membership(p.toVector());
    }

    @Override default Grade possibility(FuzzyPoint p) {
        return toVector().possibility(p.toVector());
    }

    @Override default Grade necessity(FuzzyPoint p) {
        return toVector().necessity(p.toVector());
    }

    @Override default FuzzyPoint divide(Double t, Point p){
        return of(Point.super.divide(t, p),
                FastMath.abs(1-t)*getR()+FastMath.abs(t)*(p instanceof FuzzyPoint ? ((FuzzyPoint)p).getR() : 0.0));
    }
}
