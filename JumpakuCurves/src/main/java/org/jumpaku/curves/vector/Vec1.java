/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author Jumpaku
 */
public class Vec1 implements Vec{
    
    private final Vector1D vector1d;
    
    public Vector1D getVector1d(){
        return vector1d;
    }
    
    /*@Override
    public Vector<? extends Space> getVector(){
        return getVector1d();
    }*/
    
    public Vec1(Double x) {
        this(new Vector1D(x));
    }
    
    public Vec1(Vector1D v){
        this.vector1d = v;
    }

    @Override
    public Vec add(Vec v) {
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return new Vec1(getVector1d().add(((Vec1)v).getVector1d()));
    }

    @Override
    public Vec scale(Double a) {
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
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector1d().dotProduct(((Vec1)v).getVector1d());
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
        
        return Precision.equals(getX(), ((Vec1)v).getX(), eps);
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
