/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author Jumpaku
 */
public class Vec3 extends Vector3D implements Vec<Euclidean3D>{
    
    public Vec3(double x, double y, double z) {
        super(x, y, z);
    }
    
    public Vec3(Vector3D v){
        super(v.getX(), v.getY(), v.getZ());
    }
    
}
