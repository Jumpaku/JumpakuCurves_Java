/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public interface Vector {
    
    static final JsonVector CONVERTER = new JsonVector();
    
    static Boolean equals(Vector a, Vector b, Double eps){
        return Precision.equals(a.getX(), b.getX(), eps) &&
                Precision.equals(a.getY(), b.getY(), eps) &&
                Precision.equals(a.getZ(), b.getZ(), eps);
    }
    
    static Vector of(Vector3D v){
        return new Vector() {
            @Override public Vector add(Vector other) {
                return Vector.of(v.add(new Vector3D(other.getX(), other.getY(), other.getZ())));
            }

            @Override public Vector scale(Double s) {
                return Vector.of(v.scalarMultiply(s));
            }

            @Override public Double dot(Vector other) {
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
            
            @Override public String toString(){
                return Vector.toString(this);
            }
        };
    }
    
    static Vector of(Double x, Double y, Double z){
        return of(new Vector3D(x, y, z));
    }
    
    static Vector of(Double x, Double y){
        return of(x, y, 0.0);
    }
    
    static Vector of(Double x){
        return of(x, 0.0);
    }
    
    static final Vector ZERO = of(0.0, 0.0, 0.0);
    
    static Vector zero(){
        return ZERO;
    }
    
    static String toString(Vector v){
        return CONVERTER.toJson(v);
    }
    
    static Vector add(Double a, Vector v1, Double b, Vector v2){
        return v1.scale(a).add(b, v2);
    }

    Vector add(Vector v);

    Vector scale(Double w);

    Double dot(Vector v);
    
    Double getX();
    
    Double getY();
    
    Double getZ();
    
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
    
    default Vector resize(Double l){
        return scale(l/length());
    }
    
    default Vector cross(Vector v){
        Vector3D cross = new Vector3D(getX(), getY(), getZ()).crossProduct(new Vector3D(v.getX(), v.getY(), v.getZ()));
        return of(cross.getX(), cross.getY(), cross.getZ());
    }
    
    default Double angle(Vector v){
        return Vector3D.angle(new Vector3D(getX(), getY(), getZ()), new Vector3D(v.getX(), v.getY(), v.getZ()));
    }
}
