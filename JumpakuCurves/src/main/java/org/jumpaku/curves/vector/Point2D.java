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
    
    private final Vec vector;
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
}
