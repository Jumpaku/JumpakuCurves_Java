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
 * @author Jumpaku
 * @param <S>
 * @param <V>
 * @param <C>
 */
public interface Interpolater<S extends Space, V extends Vector<S>, C extends Curve<S, V>> {
    C interpolate();
}
