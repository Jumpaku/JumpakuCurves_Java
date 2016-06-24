/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author Jumpaku
 * @param <S>
 * @param <V>
 */
public class Data<S extends Space, V extends Vector<S>> {
    public Data(V point, Double param) {
        this.point = point;
        this.param = param;
    }

    private final V point;

    private final Double param;

    public V getPoint() {
        return point;
    }

    public Double getParam() {
        return param;
    }
}

