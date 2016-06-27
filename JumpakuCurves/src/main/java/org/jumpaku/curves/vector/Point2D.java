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
    public Vec getVec() {
        return vec;
    }
    
    public Vec2 getVec2(){
        return vec;
    }
    public Double getX(){
        return get(0);
    }
        
    public Double getY(){
        return get(1);
    }
}
