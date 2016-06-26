/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;

/**
 *
 * @author Jumpaku
 */
public interface Point {
    
    public static Point origin(Integer dimention){
        if(dimention <= 0 || 3 > dimention)
            throw new IllegalArgumentException("dimention must be 1, 2, or 3.");
        
        return dimention == 1 ? new Point1D(0.0) : dimention == 2 ? new Point2D(0.0, 0.0) : new Point3D(0.0 ,0.0 ,0.0);
    }
    
    Vec getVector();
    
    Point move(Vec v);
    
    Point divide(Double t, Point p);

    default Integer dimention(){
        return getVector().getDimention();
    }
    
    default Double get(Integer i){
        if(i < 0 && dimention() <= i)
            throw new IllegalArgumentException("index is out of bounds");
        
        return getVector().get(i);
    }

    default Vec difference(Point p){
        if(!Objects.equals(dimention(), p.dimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector().sub(p.getVector());
    }
    
    default Double distance(Point p){
        if(!Objects.equals(dimention(), p.dimention()))
            throw new IllegalArgumentException("dimention miss match");

        return difference(p).length();
    }
    
    default Double deistanceSquare(Point p){
        if(!Objects.equals(dimention(), p.dimention()))
            throw new IllegalArgumentException("dimention miss match");

        return difference(p).square();
    }
    
    default Boolean equals(Point p, Double eps){
        return getVector().equals(p.getVector(), eps);
    }
    
    default Boolean equals(Point p, Integer ulp){
        return getVector().equals(p.getVector(), ulp);
    }
}
