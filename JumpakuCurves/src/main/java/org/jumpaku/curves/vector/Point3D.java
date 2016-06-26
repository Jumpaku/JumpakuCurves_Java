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
public class Point3D extends AbstractPoint{

    private Vec3 vector; 

    public Point3D(Double x, Double y, Double z){
        this(new Vec3(x, y, z));
    }
    
    public Point3D(Vec3 vector) {
        this.vector = vector;
    }
    
    @Override
    public Vec getVector() {
        return vector;
    }

    @Override
    public Point toPoint(Vec v) {
        if(v.getDimention() != 3)
            throw new IllegalArgumentException("dimention miss match");
        
        return new Point3D((Vec3)v);
    }
}
