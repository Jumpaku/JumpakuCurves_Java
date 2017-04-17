/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.vector;

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
    
    public Vec3(Vec v){
        if(3 != v.getDimention())
            throw new IllegalArgumentException("dimention of v is not 3");
        
        this.vector3d = new Vector3D(v.get(0), v.get(1), v.get(2));
    }

    @Override
    public Vec3 add(Vec v) {
        if(3 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return new Vec3(getVector3d().add(new Vector3D(v.get(0), v.get(1), v.get(2))));
    }

    @Override
    public Vec3 scale(Double a) {
        return new Vec3(getVector3d().scalarMultiply(a));
    }

    @Override
    public Vec3 add(Double a, Vec v) {
        return new Vec3(Vec.super.add(a, v));
    }

    @Override
    public Vec3 negate() {
        return new Vec3(Vec.super.negate());
    }

    @Override
    public Vec3 normalize() {
        return new Vec3(Vec.super.normalize());
    }

    @Override
    public Vec3 sub(Vec v) {
        return new Vec3(Vec.super.sub(v));
    }

    @Override
    public Vec3 sub(Double a, Vec v) {
        return new Vec3(Vec.super.sub(a, v));
    }

    public Vec3 cross(Vec3 v){
        return new Vec3(getVector3d().crossProduct(v.getVector3d()));
    }
    
    public Double angle(Vec3 v){
        return Vector3D.angle(getVector3d(), v.getVector3d());
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
        if(3 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector3d().dotProduct(new Vector3D(v.get(0), v.get(1), v.get(2)));
    }

    private Vector3D getVector3d() {
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