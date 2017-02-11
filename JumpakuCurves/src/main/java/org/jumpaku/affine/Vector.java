/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author Jumpaku
 */
public interface Vector {
    
    static Vector of(Vector3D v){
        return new Vector() {
            @Override
            public Vector add(Vector other) {
                return of(v.add(new Vector3D(other.getX(), other.getY(), other.getZ())));
            }

            @Override
            public Vector scale(Double s) {
                return of(v.scalarMultiply(s));
            }

            @Override
            public Double dot(Vector other) {
                return v.dotProduct(new Vector3D(other.getX(), other.getY(), other.getZ()));
            }

            @Override public Double getX() {
                return v.getX();
            }

            @Override public Double getY() {
                return v.getY();
            }

            @Override public Double getZ() {
                return v.getZ();
            }
        };
    }
    
    static Vector of(Double x, Double y, Double z){
        return of(new Vector3D(x, y, z));
    }
    
    static Vector twod(Double x, Double y){
        return of(x, y, 0.0);
    }
    
    static Vector oned(Double x){
        return twod(x, 0.0);
    }
    
    Vector add(Vector v);

    Vector scale(Double s);

    Double dot(Vector v);
    
    Double getX();
    
    Double getY();
    
    Double getZ();
    
    static Vector add(Double a, Vector v1, Double b, Vector v2){
        return v1.scale(a).add(b, v2);
    }
    
    default Vector sub(Vector v){
        return add(v.negate());
    }
    
    default Vector sub(Double a, Vector v){
        return sub(v.scale(a));
    }
    
    default Double square(){
        return dot(this);
    }
    
    default Double length(){
        return FastMath.sqrt(square());
    }
    
    default Vector add(Double a, Vector v){
        return add(v.scale(a));
    }
    
    default Vector negate(){
        return scale(-1.0);
    }
    
    default Vector normalize(){
        return scale(1.0/length());
    }
    
    default Vector cross(Vector v){
        Vector3D cross = new Vector3D(getX(), getY(), getZ()).crossProduct(new Vector3D(v.getX(), v.getY(), v.getZ()));
        return of(cross.getX(), cross.getY(), cross.getZ());
    }
    
    default Double angle(Vector v){
        return Vector3D.angle(new Vector3D(getX(), getY(), getZ()), new Vector3D(v.getX(), v.getY(), v.getZ()));
    }
}
