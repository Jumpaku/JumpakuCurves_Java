/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.transform;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public interface Affine2D extends Transform<Vector2D>{
    Affine2D scale(Double scale);
    Affine2D scale(Double x, Double y);
    Affine2D scaleAt(Vector2D center, Double x, Double y);
    Affine2D scaleAt(Vector2D center, Double scale);
    Affine2D rotate(Double radian);
    Affine2D rotateAt(Vector2D center, Double radian);
    Affine2D translate(Vector2D v);
    Affine2D shear(Double x, Double y);
    Affine2D shearAt(Vector2D pivot, Double x, Double y);
    
    Affine2D invert();
    Affine2D concatenate(Affine2D t); 
}
