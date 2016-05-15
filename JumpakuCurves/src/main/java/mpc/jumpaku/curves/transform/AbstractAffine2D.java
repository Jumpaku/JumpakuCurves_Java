/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.transform;

import fj.data.List;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public abstract class AbstractAffine2D implements Affine2D{
    private final List<Affine2D> transforms;
    public AbstractAffine2D(){
        transforms = List.nil();
    }
    public AbstractAffine2D(Affine2D transform){
        transforms = List.single(transform);
    }
    public AbstractAffine2D(List<Affine2D> transforms){
        this.transforms = transforms;
    }
    
    protected final List<Affine2D> getTransforms(){
        return transforms;
    }
    
    protected abstract Affine2D createScaling(Double x, Double y);
    
    protected abstract Affine2D createRotation(Double radian);
    
    protected abstract Affine2D createTranslation(Vector2D v);
    
    protected abstract Affine2D createShearing(Double x, Double y);
    
    @Override
    public Affine2D scale(Double x, Double y){
        return concatenate(createScaling(x, y));
    }
    @Override
    public Affine2D scale(Double scalar){
        return scale(scalar, scalar);
    }
    @Override
    public Affine2D scaleAt(Vector2D center, Double x, Double y){
        return translate(center.negate()).scale(x, y).translate(center);
    }
    @Override
    public Affine2D scaleAt(Vector2D center, Double scale){
        return translate(center.negate()).scale(scale).translate(center);
    }
    @Override
    public Affine2D rotate(Double radian){
        return concatenate(createRotation(radian));
    }
    @Override
    public Affine2D rotateAt(Vector2D center, Double radian){
        return translate(center.negate()).rotate(radian).translate(center);
    }
    @Override
    public Affine2D translate(Vector2D v){
        return concatenate(createTranslation(v));
    }
    @Override
    public Affine2D shear(Double x, Double y){
        return concatenate(createShearing(x, y));
    }
    @Override
    public Affine2D shearAt(Vector2D pivot, Double x, Double y){
        return translate(pivot.negate()).shear(x, y).translate(pivot);
    }
    
    @Override
    public final Affine2D invert() {
        return transforms
                .map(m -> m.invert())
                .reverse()
                .foldLeft1((m1, m2)->m1.concatenate(m2));
    }

    @Override
    public final Affine2D concatenate(Affine2D t) {
        AbstractAffine2D original = this;
        return new AbstractAffine2D(List.single(t).append(transforms)) {
            @Override
            public Vector2D apply(Vector2D vector) {
                return getTransforms().foldLeft((v, m)->m.apply(v), vector);
            }

            @Override
            protected Affine2D createScaling(Double x, Double y) {
                return original.createScaling(x, y);
            }

            @Override
            protected Affine2D createRotation(Double radian) {
                return original.createRotation(radian);
            }

            @Override
            protected Affine2D createTranslation(Vector2D v) {
                return original.createTranslation(v);
            }

            @Override
            protected Affine2D createShearing(Double x, Double y) {
                return original.createShearing(x, y);
            }
        };
    }
}
