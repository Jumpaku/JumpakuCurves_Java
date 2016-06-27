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
public class Point1D implements Point{

    private final Vec1 vec;
    
    public Point1D(Double x){
        this(new Vec1(x));
    }
    
    public Point1D(Vec1 vector) {
        this.vec = vector;
    }
    
    public Point1D(Point p){
        if(1 != p.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        this.vec = new Vec1(p.getVec());
    }
    
    @Override
    public Vec getVec() {
        return vec;
    }
    
    public Vec1 getVec1(){
        return vec;
    }
    
    public Double getX(){
        return get(0);
    }
}
