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
public abstract class AbstractPoint implements Point{

    protected abstract Point toPoint(Vec v);
    
    @Override
    public abstract Vec getVector();
    
    @Override
    public Point move(Vec v) {
        return toPoint(getVector().add(v));
    }

    @Override
    public Point divide(Double t, Point p) {
        return toPoint(Vec.add(1-t, getVector(), t, p.getVector()));
    }


    
}
