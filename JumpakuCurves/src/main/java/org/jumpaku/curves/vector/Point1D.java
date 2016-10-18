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
    public Vec1 from(Point p) {
        return new Vec1(Point.super.from(p));
    }

    @Override
    public Vec1 to(Point p) {
        return new Vec1(Point.super.to(p)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point1D divide(Double t, Point p) {
        return new Point1D(Point.super.divide(t, p)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point1D move(Vec v) {
        return new Point1D(Point.super.move(v)); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Vec1 getVec() {
        return vec;
    }
    
    /**
     * 
     * @return 
     */    
    public Double getX(){
        return get(0);
    }
}
