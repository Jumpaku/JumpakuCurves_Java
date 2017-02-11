/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.affine;

import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Point2D;
import org.jumpaku.old.curves.vector.Vec2;

/**
 *
 * @author Jumpaku
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
        public Point2D apply(Point2D v) {
            return new Point2D(v.get(0)*x, v.get(1)*y);
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
        public Point2D apply(Point2D v) {
            return new Point2D(cos*v.get(0) - sin*v.get(1), sin*v.get(0) + cos*v.get(1));
        }        
    }    
    public static class Translation extends Affine2DChain{
        private final Vec2 v;

        public Translation(Vec2 v) {
            this.v = v;
        }
        
        @Override
        public Affine2D invert() {
            return new Translation(new Vec2(v.negate()));
        }

        @Override
        public Point2D apply(Point2D p) {
            Point tmp = p.move(v);
            return new Point2D(tmp.get(0), tmp.get(1));
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
        public Point2D apply(Point2D p) {
            return new Point2D(p.get(0)+p.get(1)*x, p.get(0)*y+p.get(1));
        }
        
    }
    protected static final Affine2D IDENTITY = new Affine2DChain() {
        @Override
        public Affine2D invert() {
            return this;
        }

        @Override
        public Point2D apply(Point2D p) {
            return p;
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
    protected Affine2D createTranslation(Vec2 v) {
        return new Translation(v);
    }

    @Override
    protected Affine2D createShearing(Double x, Double y) {
        return new Shearing(x, y);
    }
}
