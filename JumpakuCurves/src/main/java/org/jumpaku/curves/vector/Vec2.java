/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public class Vec2 implements Vec{
    
    private final Vector2D vector2d;
    
    private Vector2D getVector2d(){
        return vector2d;
    }
    
    public Vec2(double x, double y) {
        this(new Vector2D(x, y));
    }
    
    public Vec2(Vector2D v){
        this.vector2d = v;
    }
    
    public Vec2(Vec v){
        if(2 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
            
        vector2d = new Vector2D(v.get(0), v.get(1));
    }

    @Override
    public Vec2 add(Vec v) {
        if(2 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return new Vec2(getVector2d().add(new Vector2D(v.get(0), v.get(1))));
    }

    @Override
    public Vec2 scale(Double a) {
        return new Vec2(getVector2d().scalarMultiply(a));
    }

    @Override
    public Vec2 add(Double a, Vec v) {
        return new Vec2(Vec.super.add(a, v));
    }

    @Override
    public Vec2 negate() {
        return new Vec2(Vec.super.negate());
    }

    @Override
    public Vec2 normalize() {
        return new Vec2(Vec.super.normalize());
    }

    @Override
    public Vec2 sub(Vec v) {
        return new Vec2(Vec.super.sub(v));
    }

    @Override
    public Vec2 sub(Double a, Vec v) {
        return new Vec2(Vec.super.sub(a, v));
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
        if(2 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector2d().dotProduct(new Vector2D(v.get(0), v.get(1)));
    }
    
    public Double cross(Vec2 v) {
        return v.getVector2d().crossProduct(Vector2D.ZERO, getVector2d());
    }
    
    public Double angle(Vec2 v){
        return Vector2D.angle(getVector2d(), v.getVector2d());
    }
    
    public static Double angle(Vec2 from, Vec2 to){
        return from.cross(to) > 0 ? from.angle(to) : -from.angle(to);
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
