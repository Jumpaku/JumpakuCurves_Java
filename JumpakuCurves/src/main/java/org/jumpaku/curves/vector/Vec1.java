/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Objects;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;

/**
 *
 * @author Jumpaku
 */
public class Vec1 implements Vec{
    
    private final Vector1D vector1d;
    
    public Vector1D getVector1d(){
        return vector1d;
    }
    
    @Override
    public Vector<? extends Space> getVector(){
        return getVector1d();
    }
    
    public Vec1(double x) {
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
        return 2;
    }

    @Override
    public Double get(Integer i) {
        if(i < 0 && 1 <= i)
            throw new IllegalArgumentException("index is out of bounds");
        
        return getX();
    }

    @Override
    public Double dot(Vec v) {
        if(!Objects.equals(getDimention(), v.getDimention()))
            throw new IllegalArgumentException("dimention miss match");
        
        return getVector1d().dotProduct(((Vec1)v).getVector1d());
    }

    private Double getX() {
        return getVector1d().getX();
    }
}
