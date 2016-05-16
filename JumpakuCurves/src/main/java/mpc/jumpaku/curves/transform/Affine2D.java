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
    default Affine2D refrectOrigin(){
        return scale(-1.0);
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
