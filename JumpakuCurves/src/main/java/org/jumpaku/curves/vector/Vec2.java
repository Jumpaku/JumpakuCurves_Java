/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Jumpaku
 */
public class Vec2 extends Vector2D implements Vec<Euclidean2D> {
    
    public Vec2(double x, double y) {
        super(x, y);
    }
    
    public Vec2(Vector2D v){
        super(v.getX(), v.getY());
    }
}
