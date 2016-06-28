/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public class Vec1 implements Vec{
    
    private final Vector1D vector1d;
    
    private Vector1D getVector1d(){
        return vector1d;
    }
    
    public Vec1(Double x) {
        this(new Vector1D(x));
    }
    
    public Vec1(Vector1D v){
        this.vector1d = v;
    }

    public Vec1(Vec v){
        if(1 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
            
        vector1d = new Vector1D(v.get(0));
    }
    
    @Override
    public Vec1 add(Vec v) {
        if(1 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return new Vec1(getVector1d().add(new Vector1D(v.get(0))));
    }

    @Override
    public Vec1 add(Double a, Vec v) {
        return new Vec1(Vec.super.add(a, v));
    }

    @Override
    public Vec1 negate() {
        return new Vec1(Vec.super.negate());
    }

    @Override
    public Vec1 normalize() {
        return new Vec1(Vec.super.normalize());
    }

    @Override
    public Vec1 sub(Vec v) {
        return new Vec1(Vec.super.sub(v));
    }

    @Override
    public Vec1 sub(Double a, Vec v) {
        return new Vec1(Vec.super.sub(a, v));
    }
    
    @Override
    public Vec1 scale(Double a) {
        return new Vec1(getVector1d().scalarMultiply(a));
    }

    @Override
    public Integer getDimention() {
        return 1;
    }

    @Override
    public Double get(Integer i) {
        if(0 != i)
            throw new IllegalArgumentException("index is not 0");
        
        return getX();
    }

    @Override
    public Double dot(Vec v) {
        if(1 != v.getDimention())
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector1d().dotProduct(new Vector1D(v.get(0)));
    }

    public Double getX() {
        return getVector1d().getX();
    }

    @Override
    public Boolean equals(Vec v, Double eps) {
        if(v == null)
            return false;
        
        if(1 != v.getDimention())
            return false;
        
        return Precision.equals(getX(), v.get(0), eps);
    }

    @Override
    public Boolean equals(Vec v, Integer ulp) {
        if(v == null)
            return false;
        
        if(1 != v.getDimention())
            return false;
        
        return Precision.equals(getX(), v.get(0), ulp);
    }
}
