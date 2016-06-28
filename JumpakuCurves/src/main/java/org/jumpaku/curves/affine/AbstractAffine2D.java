/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.affine;

import org.jumpaku.curves.vector.Point2D;
import org.jumpaku.curves.vector.Vec2;

/**
 *
 * @author Jumpaku
 */
public abstract class AbstractAffine2D implements Affine2D {
    
    protected abstract Affine2D createScaling(Double x, Double y);
    
    protected abstract Affine2D createRotation(Double radian);
    
    protected abstract Affine2D createTranslation(Vec2 v);
    
    protected abstract Affine2D createShearing(Double x, Double y);
    
    private static Affine2D concatenate(AbstractAffine2D self, Affine2D second){        
        return new AbstractAffine2D() {
            @Override
            public Affine2D invert() {
                return second.invert().concatenate(self.invert());
            }

            @Override
            public Point2D apply(Point2D v) {
                return second.apply(self.apply(v));
            }
            @Override
            protected Affine2D createScaling(Double x, Double y) {
                return self.createScaling(x, y);
            }

            @Override
            protected Affine2D createRotation(Double radian) {
                return self.createRotation(radian);
            }

            @Override
            protected Affine2D createTranslation(Vec2 v) {
                return self.createTranslation(v);
            }

            @Override
            protected Affine2D createShearing(Double x, Double y) {
                return self.createShearing(x, y);
            }
        };
    }
    
    @Override
    public Affine2D scale(Double x, Double y){
        return concatenate(createScaling(x, y));
    }
    @Override
    public Affine2D rotate(Double radian){
        return concatenate(createRotation(radian));
    }
    @Override
    public Affine2D translate(Vec2 v){
        return concatenate(createTranslation(v));
    }
    @Override
    public Affine2D shear(Double x, Double y){
        return concatenate(createShearing(x, y));
    }
    
    @Override
    public Affine2D concatenate(Affine2D t) {
        return concatenate(this, t);
    }
}
