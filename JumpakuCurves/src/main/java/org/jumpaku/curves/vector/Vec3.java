/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author Jumpaku
 */
public class Vec3 implements Vec{
    
    private final Vector3D vector3d;
    public Vec3(double x, double y, double z) {
        this(new Vector3D(x, y, z));
    }
    
    public Vec3(Vector3D v){
        this.vector3d = v;
    }

    @Override
    public Vector<? extends Space> getVector(){
        return getVector3d();
    }
    @Override
    public Vec add(Vec v) {
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return new Vec3(getVector3d().add(((Vec3)v).getVector3d()));
    }

    @Override
    public Vec scale(Double a) {
        return new Vec3(getVector3d().scalarMultiply(a));
    }

    @Override
    public Integer getDimention() {
        return 3;
    }

    @Override
    public Double get(Integer i) {
        if(i < 0 && 3 <= i)
            throw new IllegalArgumentException("index is out of bounds");
        
        return i == 0 ? getX() : i == 1 ? getY() : getZ();
    }

    @Override
    public Double dot(Vec v) {
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector3d().dotProduct(((Vec3)v).getVector3d());
    }

    public Vector3D getVector3d() {
        return vector3d;
    }
    
    public Double getX(){
        return getVector3d().getX();
    }
    
    public Double getY(){
        return getVector3d().getY();
    }
    
    public Double getZ(){
        return getVector3d().getZ();
    }
}
