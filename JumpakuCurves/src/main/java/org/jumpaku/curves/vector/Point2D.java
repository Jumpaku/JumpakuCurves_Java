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
public class Point2D extends AbstractPoint{

    private Vec2 vector; 

    public Point2D(Double x, Double y){
        this(new Vec2(x, y));
    }
    
    public Point2D(Vec2 vector) {
        this.vector = vector;
    }
    
    @Override
    public Vec getVector() {
        return vector;
    }

    @Override
    public Point toPoint(Vec v) {
        if(v.getDimention() != 2)
            throw new IllegalArgumentException("dimention miss match");
        
        return new Point2D((Vec2)v);
    }
}
