/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.vector;

import java.util.Objects;
import javaslang.collection.Stream;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public interface Point {
    
    public static Point origin(Integer dimention){        
        return of(Vec.zero(dimention));
    }
    
    public static Point of(Vec v){
        return new Point() {
            @Override
            public Vec getVec() {
                return v;
            }
        };
    }
    
    public static Point of(Double... elements){
        return of(Vec.of(elements));
    }
    
    public static Point affineCombination(Iterable<Double> cofficients, Iterable<Point> points){
        if(!Precision.equals(Stream.ofAll(cofficients).reduce(Double::sum), 1.0, 2.0e-10))
            throw new IllegalArgumentException("sum closed cofficients must be 1.0");
        
        Integer d = points.iterator().next().getDimention();
        return Stream.ofAll(cofficients)
                .zip(points).map(t -> t.transform(
                        (c, p) -> p.getVec().scale(c)))
                .foldLeft(origin(d), (p, v) -> p.move(v));
    }

    
    public static Boolean equals(Point p1, Point p2, Double eps){
        return Vec.equals(p1.getVec(), p2.getVec(), eps);
    }
    
    public static Boolean equals(Point p1, Point p2, Integer ulp){
        return Vec.equals(p1.getVec(), p2.getVec(), ulp);
    }
    
    Vec getVec();
    
    default Point move(Vec v) {
        if(getDimention() !=  v.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        return of(getVec().add(v));
    }
    
    default Point divide(Double t, Point p){
        if(getDimention() !=  p.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        return of(Vec.add(1-t, getVec(), t, p.getVec()));
    }

    default Integer getDimention(){
        return getVec().getDimention();
    }
    
    default Double get(Integer i){
        if(i < 0 && getDimention() <= i)
            throw new IllegalArgumentException("index is out closed bounds");
        
        return getVec().get(i);
    }

    default Vec from(Point p){
        if(!Objects.equals(getDimention(), p.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return getVec().sub(p.getVec());
    }

    default Vec to(Point p){
        if(!Objects.equals(getDimention(), p.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return p.getVec().sub(getVec());
    }
    
    default Double distance(Point p){
        if(!Objects.equals(getDimention(), p.getDimention()))
            throw new IllegalArgumentException("dimention miss match");

        return from(p).length();
    }
    
    default Double distanceSquare(Point p){
        if(!Objects.equals(getDimention(), p.getDimention()))
            throw new IllegalArgumentException("dimention miss match");

        return from(p).square();
    }
}
