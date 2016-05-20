/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.transform;

import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Jumpaku
 */
public interface Affine2D extends Transform<Euclidean2D, Vector2D>{
    Affine2D scale(Double x, Double y);
    Affine2D rotate(Double radian);
    Affine2D translate(Vector2D v);
    Affine2D shear(Double x, Double y);
    
    default Affine2D scale(Double scale){
        return scale(scale, scale);
    }
    default Affine2D scaleAt(Vector2D center, Double x, Double y){
        return translate(center.negate()).scale(x, y).translate(center);
    }
    default Affine2D scaleAt(Vector2D center, Double scale){
        return scaleAt(center, scale, scale);
    }
    default Affine2D rotateAt(Vector2D center, Double radian){
        return translate(center.negate()).rotate(radian).translate(center);
    }
    default Affine2D rotate(Vector2D to, Vector2D from){
        return rotateAt(Vector2D.ZERO, from, from);
    }
    default Affine2D rotateAt(Vector2D center, Vector2D from, Vector2D to){
        Double cross = to.crossProduct(center, from);
        Double radian = Vector2D.angle(from, to);
        return rotateAt(center, cross > 0 ? radian : -radian);
    }
    default Affine2D shearX(Double x){
        return shear(x, 0.0);
    }
    default Affine2D shearY(Double y){
        return shear(0.0, y);
    }
    default Affine2D shearAt(Vector2D pivot, Double x, Double y){
        return translate(pivot.negate()).shear(x, y).translate(pivot);
    }
    default Affine2D shearXAt(Vector2D pivot, Double x){
        return shearAt(pivot, x, 0.0);
    }
    default Affine2D shearYAt(Vector2D pivot, Double y){
        return shearAt(pivot, 0.0, y);
    }
    default Affine2D squeeze(Double k){
        return scale(k, 1/k);
    }
    default Affine2D squeezeAt(Vector2D center, Double k){
        return translate(center.negate()).squeeze(k).translate(center);
    }
    default Affine2D refrectOrigin(){
        return scale(-1.0);
    }
    default Affine2D refrectAt(Vector2D center){
        return scaleAt(center, -1.0);
    }
    default Affine2D refrectXAxis(){
        return scale(1.0, -1.0);
    }
    default Affine2D refrectYAxis(){
        return scale(-1.0, 1.0);
    }
            
    Affine2D invert();
    Affine2D concatenate(Affine2D t); 
}
