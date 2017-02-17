/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.affine;

import org.jumpaku.old.curves.vector.Point3D;
import org.jumpaku.old.curves.vector.Vec3;

/**
 *
 * @author ito Jumpaku
 */
public abstract class AbstractAffine3D implements Affine3D{

    
    protected abstract Affine3D createScaling(Double x, Double y, Double z);
    
    protected abstract Affine3D createRotation(Vec3 axis, Double radian);
    
    protected abstract Affine3D createTranslation(Vec3 v);
    
    private static Affine3D concatenate(AbstractAffine3D self, Affine3D second){        
        return new AbstractAffine3D() {
            @Override
            public Affine3D invert() {
                return second.invert().concatenate(self.invert());
            }

            @Override
            public Point3D apply(Point3D v) {
                return second.apply(self.apply(v));
            }
            @Override
            protected Affine3D createScaling(Double x, Double y, Double z) {
                return self.createScaling(x, y, z);
            }

            @Override
            protected Affine3D createRotation(Vec3 axis, Double radian) {
                return self.createRotation(axis, radian);
            }

            @Override
            protected Affine3D createTranslation(Vec3 v) {
                return self.createTranslation(v);
            }
        };
    }
    
    @Override
    public Affine3D scale(Double x, Double y, Double z) {
        return concatenate(createScaling(x, y, z));
    }

    @Override
    public Affine3D rotate(Vec3 axis, Double radian) {
        return concatenate(createRotation(axis, radian));
    }

    @Override
    public Affine3D translate(Vec3 v) {
        return concatenate(createTranslation(v));
    }

    @Override
    public Affine3D concatenate(Affine3D a) {
        return concatenate(this, a);
    }
}
