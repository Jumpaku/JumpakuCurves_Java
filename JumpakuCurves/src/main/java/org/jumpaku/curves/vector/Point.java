/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;
import javaslang.collection.Stream;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public interface Point {
    
    public static Point origin(Integer dimention){        
        return create(Vec.zero(dimention));
    }
    
    public static Point create(Vec v){
        return new Point() {
            @Override
            public Vec getVector() {
                return v;
            }
        };
    }
    
    public static Point affineCombination(Iterable<Double> cofficients, Iterable<Point> points){
        if(!Precision.equals(Stream.ofAll(cofficients).reduce(Double::sum), 1.0, 2.0e-10))
            throw new IllegalArgumentException("sum of cofficients must be 1.0");
        
        Integer d = points.iterator().next().getDimention();
        return Stream.ofAll(cofficients)
                .zip(points).map(t -> t.transform(
                        (c, p) -> p.getVector().scale(c)))
                .foldLeft(origin(d), (p, v) -> p.move(v));
    }
    
    Vec getVector();
    
    default Point move(Vec v) {
        if(getDimention() !=  v.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        return create(getVector().add(v));
    }
    
    default Point divide(Double t, Point p){
        if(getDimention() !=  p.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        return create(Vec.add(1-t, getVector(), t, p.getVector()));
    }

    default Integer getDimention(){
        return getVector().getDimention();
    }
    
    default Double get(Integer i){
        if(i < 0 && getDimention() <= i)
            throw new IllegalArgumentException("index is out of bounds");
        
        return getVector().get(i);
    }

    default Vec difference(Point p){
        if(!Objects.equals(getDimention(), p.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector().sub(p.getVector());
    }
    
    default Double distance(Point p){
        if(!Objects.equals(getDimention(), p.getDimention()))
            throw new IllegalArgumentException("dimention miss match");

        return difference(p).length();
    }
    
    default Double deistanceSquare(Point p){
        if(!Objects.equals(getDimention(), p.getDimention()))
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
