/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.affine;

import org.jumpaku.old.curves.vector.Point1D;
import org.jumpaku.old.curves.vector.Vec1;

/**
 *
 * @author ito Jumpaku
 */
public interface Affine1D extends Affine<Point1D>{

    public static abstract class AbstractAffine1D implements Affine1D{
        protected abstract Affine1D createScaling(Double scale);
        protected abstract Affine1D createTranslation(Vec1 v);
        private static Affine1D concatenate(AbstractAffine1D self, Affine1D second){        
            return new AbstractAffine1D() {
                @Override
                public Affine1D invert() {
                    return second.invert().concatenate(self.invert());
                }

                @Override
                public Point1D apply(Point1D v) {
                    return second.apply(self.apply(v));
                }
                @Override
                protected Affine1D createScaling(Double x) {
                    return self.createScaling(x);
                }

                @Override
                protected Affine1D createTranslation(Vec1 v) {
                    return self.createTranslation(v);
                }
            };
        }
        public Affine1D scale(Double scale){
            return concatenate(createScaling(scale));
        }
        public Affine1D translate(Vec1 v){
            return concatenate(createTranslation(v));
        }
         @Override
        public Affine1D concatenate(Affine1D a) {
            return concatenate(this, a);
        }
    }
    
    public static class Scaling extends AbstractAffine1D{
        private final Double scale;

        public Scaling(Double scale) {
            this.scale = scale;
        }
        
        @Override
        protected Affine1D createScaling(Double scale) {
            return new Scaling(scale);
        }

        @Override
        protected Affine1D createTranslation(Vec1 v) {
            return new Translation(v);
        }

        @Override
        public Affine1D invert() {
            return new Scaling(1.0/scale);
        }

        @Override
        public Point1D apply(Point1D p) {
            return new Point1D(p.getVec().scale(scale));
        }
    }
    
    public static class Translation extends AbstractAffine1D{
        private final Vec1 move;

        public Translation(Vec1 move) {
            this.move = move;
        }
        
        @Override
        protected Affine1D createScaling(Double scale) {
            return new Scaling(scale);
        }

        @Override
        protected Affine1D createTranslation(Vec1 v) {
            return new Translation(v);
        }

        @Override
        public Affine1D invert() {
            return createTranslation(move.negate());
        }

        @Override
        public Point1D apply(Point1D p) {
            return p.move(move);
        }
    }
            
    /**
     * <p>逆変換 Inversion.</p>
     * @return 元の変換の逆変換 Inverted affine
     */
    Affine1D invert();
    
    /**
     * <p>合成 concatenation.</p>
     * @param a 追加するアフィン変換 Affine to be added
     * @return 合成後のアフィン変換 Affine concatenated a
     */
    Affine1D concatenate(Affine1D a);

    @Override
    Point1D apply(Point1D p);
}
