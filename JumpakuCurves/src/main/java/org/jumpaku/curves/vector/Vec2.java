/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public class Vec2 implements Vec{
    
    private final Vector2D vector2d;
    
    public Vector2D getVector2d(){
        return vector2d;
    }
    
    /*@Override
    public Vector<? extends Space> getVector(){
        return getVector2d();
    }*/
    
    public Vec2(double x, double y) {
        this(new Vector2D(x, y));
    }
    
    public Vec2(Vector2D v){
        this.vector2d = v;
    }

    @Override
    public Vec add(Vec v) {
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return new Vec2(getVector2d().add(((Vec2)v).getVector2d()));
    }

    @Override
    public Vec scale(Double a) {
        return new Vec2(getVector2d().scalarMultiply(a));
    }

    @Override
    public Integer getDimention() {
        return 2;
    }

    @Override
    public Double get(Integer i) {
        if(i < 0 && 2 <= i)
            throw new IllegalArgumentException("index is out of bounds");
        
        return i == 0 ? getX() : getY();
    }

    @Override
    public Double dot(Vec v) {
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector2d().dotProduct(((Vec2)v).getVector2d());
    }

    public Double getX() {
        return getVector2d().getX();
    }

    public Double getY() {
        return getVector2d().getY();
    }

    @Override
    public Boolean equals(Vec v, Double eps) {
        if(v == null)
            return false;
        
        if(2 != v.getDimention())
            return false;
        
        return Precision.equals(getX(), v.get(0), eps) && Precision.equals(getY(), v.get(1), eps);
    }

    @Override
    public Boolean equals(Vec v, Integer ulp) {
        if(v == null)
            return false;
        
        if(2 != v.getDimention())
            return false;
        
        return Precision.equals(getX(), v.get(0), ulp) && Precision.equals(getY(), v.get(1), ulp);
    }
}
