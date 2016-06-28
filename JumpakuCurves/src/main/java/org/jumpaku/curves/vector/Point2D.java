/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

/**
 *
 * @author Jumpaku
 */
public class Point2D implements Point{
    
    private final Vec2 vec;
    
    public Point2D(Double x, Double y){
        this(new Vec2(x, y));
    }
    
    public Point2D(Vec2 vector) {
        this.vec = vector;
    }
    
    public Point2D(Point p){
        if(2 != p.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        this.vec = new Vec2(p.getVec());
    }

    @Override
    public Vec2 difference(Point p) {
        return new Vec2(Point.super.difference(p));
    }

    @Override
    public Point2D divide(Double t, Point p) {
        return new Point2D(Point.super.divide(t, p));
    }

    @Override
    public Point2D move(Vec v) {
        return new Point2D(Point.super.move(v));
    }
    
    @Override
    public Vec2 getVec() {
        return vec;
    }
    
    public Double getX(){
        return get(0);
    }
        
    public Double getY(){
        return get(1);
    }
}
