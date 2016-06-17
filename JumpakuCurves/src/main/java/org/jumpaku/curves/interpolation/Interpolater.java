/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.Curve;

/**
 *
 * @author ito tomohiko
 * @param <S>
 * @param <V>
 * @param <C>
 */
public interface Interpolater<S extends Space, V extends Vector<S>, C extends Curve<S, V>> {
    public static final class Data<S extends Space, V extends Vector<S>>{

        public Data(V point, Double param) {
            this.point = point;
            this.param = param;
        }
        private V point;
        private Double param;

        public V getPoint() {
            return point;
        }

        public Double getParam() {
            return param;
        }
    }
    
    C interpolate();
}
