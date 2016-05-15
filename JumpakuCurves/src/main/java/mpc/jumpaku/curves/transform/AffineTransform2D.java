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
public interface AffineTransform2D extends AffineTransform<Vector2D>{
    AffineTransform2D scale(Double scalar);
    AffineTransform2D rotate(Double radian);
    AffineTransform2D translate(Vector2D v);
    AffineTransform2D shear(Double x, Double y);
    
    AffineTransform2D invert();
    AffineTransform2D concatenate(AffineTransform2D t); 
}
