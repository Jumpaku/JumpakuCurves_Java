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
public class Point1D extends AbstractPoint{

    private Vec1 vector; 

    public Point1D(Double x){
        this(new Vec1(x));
    }
    
    public Point1D(Vec1 vector) {
        this.vector = vector;
    }
    
    @Override
    public Vec getVector() {
        return vector;
    }

    @Override
    public Point toPoint(Vec v) {
        if(v.getDimention() != 1)
            throw new IllegalArgumentException("dimention miss match");
        
        return new Point1D((Vec1)v);
    }
}
