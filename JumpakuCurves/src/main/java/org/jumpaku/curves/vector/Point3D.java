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
public class Point3D implements Point{
    
    private final Vec3 vec;
    
    public Point3D(Double x, Double y, Double z){
        this(new Vec3(x, y, z));
    }
    
    public Point3D(Vec3 vector) {
        this.vec = vector;
    }
    
    public Point3D(Point p){
        if(3 != p.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        this.vec = new Vec3(p.getVec());
    }

    @Override
    public Vec getVec() {
        return vec;
    }
    
    public Vec3 getVec3(){
        return vec;
    }
    
    public Double getX(){
        return get(0);
    }
        
    public Double getY(){
        return get(1);
    }
    
    public Double getZ(){
        return get(2);
    }
}
