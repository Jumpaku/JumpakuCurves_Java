/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 */
public interface Parameterizer {
    default <V extends Vec> Array<Data<V>> parameterize(Array<V> points, Interval interval){
        return parameterize(points, interval.getFrom(), interval.getTo());
    }
    
    <V extends Vec> Array<Data<V>> parameterize(Array<V> points, Double a, Double b);
    
    default <V extends Vec> Array<Data<V>> parameterize(Array<V> points){
        return parameterize(points, 0.0, 1.0);
    }
}
