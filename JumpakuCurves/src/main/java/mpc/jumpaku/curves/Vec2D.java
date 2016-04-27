/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public class Vec2D extends Vector2D {
    public Vec2D(){
        super(0, 0);
    }
    public Vec2D(Double x, Double y){
        super(x, y);
    }
    public Vec2D(Vector2D v){
        super(v.getX(), v.getY());
    }
}
