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
public abstract class AbstractAffineTransform2D implements AffineTransform2D{
    private final List<AffineTransform2D> transforms;
    public AbstractAffineTransform2D(){
        transforms = List.nil();
    }
    public AbstractAffineTransform2D(AffineTransform2D transform){
        transforms = List.single(transform);
    }
    public AbstractAffineTransform2D(List<AffineTransform2D> transforms){
        this.transforms = transforms;
    }
    
    protected final List<AffineTransform2D> getTransforms(){
        return transforms;
    }
    
    protected abstract AffineTransform2D scaling(Double x, Double y);
    
    protected abstract AffineTransform2D rotation(Double radian);
    
    protected abstract AffineTransform2D translation(Vector2D v);
    
    protected abstract AffineTransform2D shearing(Double x, Double y);
    
    @Override
    public AffineTransform2D scale(Double scalar){
        return concatenate(scaling(scalar, scalar));
    }
    @Override
    public AffineTransform2D rotate(Double radian){
        return concatenate(rotation(radian));
    }
    @Override
    public AffineTransform2D translate(Vector2D v){
        return concatenate(translation(v));
    }
    @Override
    public AffineTransform2D shear(Double x, Double y){
        return concatenate(shearing(x, y));
    }
    
    @Override
    public AffineTransform2D invert() {
        return transforms
                .map(m -> m.invert())
                .reverse()
                .foldLeft1((m1, m2)->m1.concatenate(m2));
    }

    @Override
    public AffineTransform2D concatenate(AffineTransform2D t) {
        AbstractAffineTransform2D original = this;
        return new AbstractAffineTransform2D(transforms.append(List.single(t))) {
            @Override
            public Vector2D apply(Vector2D vector) {
                return getTransforms().foldLeft((v, m)->m.apply(v), vector);
            }

            @Override
            protected AffineTransform2D scaling(Double x, Double y) {
                return original.scaling(x, y);
            }

            @Override
            protected AffineTransform2D rotation(Double radian) {
                return original.rotation(radian);
            }

            @Override
            protected AffineTransform2D translation(Vector2D v) {
                return original.translation(v);
            }

            @Override
            protected AffineTransform2D shearing(Double x, Double y) {
                return original.shearing(x, y);
            }
        };
    }
}
