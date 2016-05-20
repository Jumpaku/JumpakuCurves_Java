/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.transform;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public abstract class Affine2DChain extends AbstractAffine2D{
    public static class Scaling extends Affine2DChain{
        private final Double x;
        private final Double y;
        public Scaling(Double x, Double y){
            if(x.compareTo(0.0) == 0)
                throw new IllegalArgumentException("x must be not 0");
            if(y.compareTo(0.0) == 0)
                throw new IllegalArgumentException("y must be not 0");
            this.x = x;
            this.y = y;
        }
        @Override
        public Affine2D invert() {
            return new Scaling(1/x, 1/y);
        }

        @Override
        public Vector2D apply(Vector2D v) {
            return new Vector2D(v.getX()*x, v.getY()*y);
        }        
    }
    public static class Rotation extends Affine2DChain{
        private final Double cos;
        private final Double sin;
        private final Double radian;
        public Rotation(Double radian) {
            cos = Math.cos(radian);
            sin = Math.sin(radian);
            this.radian = radian;
        }
        
        @Override
        public Affine2D invert() {
            return new Rotation(-radian);
        }

        @Override
        public Vector2D apply(Vector2D v) {
            return new Vector2D(cos*v.getX() - sin*v.getY(), sin*v.getX() + cos*v.getY());
        }        
    }    
    public static class Translation extends Affine2DChain{
        private final Vector2D v;

        public Translation(Vector2D v) {
            this.v = v;
        }
        
        @Override
        public Affine2D invert() {
            return new Translation(v.negate());
        }

        @Override
        public Vector2D apply(Vector2D v) {
            return v.add(this.v);
        }        
    }
    public static class Shearing extends Affine2DChain{
        private final Double x;
        private final Double y;

        public Shearing(Double x, Double y) {
            if(Double.compare(x*y, 1.0) == 0)
                throw new IllegalArgumentException("x*y must be not 1");
            this.x = x;
            this.y = y;
        }
        
        @Override
        public Affine2D invert() {
            return new Shearing(-x, -y).scale(1/(1-x*y));
        }

        @Override
        public Vector2D apply(Vector2D v) {
            return new Vector2D(v.getX()+v.getY()*x, v.getX()*y+v.getY());
        }
        
    }
    protected static final Affine2D IDENTITY = new Affine2DChain() {
        @Override
        public Affine2D invert() {
            return this;
        }

        @Override
        public Vector2D apply(Vector2D v) {
            return v;
        }
        
        @Override
        public Affine2D concatenate(Affine2D t){
            return t;
        }
    };
    public static Affine2D identity(){
        return IDENTITY;
    }
    @Override
    protected Affine2D createScaling(Double x, Double y) {
        return new Scaling(x, y);
    }

    @Override
    protected Affine2D createRotation(Double radian) {
        return new Rotation(radian);
    }

    @Override
    protected Affine2D createTranslation(Vector2D v) {
        return new Translation(v);
    }

    @Override
    protected Affine2D createShearing(Double x, Double y) {
        return new Shearing(x, y);
    }
}
