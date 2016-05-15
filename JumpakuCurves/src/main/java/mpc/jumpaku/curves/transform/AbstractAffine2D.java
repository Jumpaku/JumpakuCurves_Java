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
    
    protected abstract Affine2D scaling(Double x, Double y);
    
    protected abstract Affine2D rotation(Double radian);
    
    protected abstract Affine2D translation(Vector2D v);
    
    protected abstract Affine2D shearing(Double x, Double y);
    
    @Override
    public Affine2D scale(Double x, Double y){
        return concatenate(scaling(x, y));
    }
    @Override
    public Affine2D scale(Double scalar){
        return scale(scalar, scalar);
    }
    @Override
    public Affine2D rotate(Double radian){
        return concatenate(rotation(radian));
    }
    @Override
    public Affine2D rotateAt(Vector2D center, Double radian){
        return invert().rotate(radian).translate(center);
    }
    @Override
    public Affine2D translate(Vector2D v){
        return concatenate(translation(v));
    }
    @Override
    public Affine2D shear(Double x, Double y){
        return concatenate(shearing(x, y));
    }
    @Override
    public Affine2D shearAt(Vector2D pivot, Double x, Double y){
        return invert().shear(x, y).translate(pivot);
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
        return new AbstractAffine2D(transforms.append(List.single(t))) {
            @Override
            public Vector2D apply(Vector2D vector) {
                return getTransforms().foldLeft((v, m)->m.apply(v), vector);
            }

            @Override
            protected Affine2D scaling(Double x, Double y) {
                return original.scaling(x, y);
            }

            @Override
            protected Affine2D rotation(Double radian) {
                return original.rotation(radian);
            }

            @Override
            protected Affine2D translation(Vector2D v) {
                return original.translation(v);
            }

            @Override
            protected Affine2D shearing(Double x, Double y) {
                return original.shearing(x, y);
            }
        };
    }
}
