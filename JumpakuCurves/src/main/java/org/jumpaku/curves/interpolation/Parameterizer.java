/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.domain.Interval;

/**
 *
 * @author Jumpaku
 */
public interface Parameterizer {
    default <S extends Space, V extends Vector<S>> Array<Data<S, V>> parameterize(Array<V> points, Interval interval){
        return parameterize(points, interval.getFrom(), interval.getTo());
    }
    
    <S extends Space, V extends Vector<S>> Array<Data<S, V>> parameterize(Array<V> points, Double a, Double b);
    
    default <S extends Space, V extends Vector<S>> Array<Data<S, V>> parameterize(Array<V> points){
        return parameterize(points, 0.0, 1.0);
    }
}
