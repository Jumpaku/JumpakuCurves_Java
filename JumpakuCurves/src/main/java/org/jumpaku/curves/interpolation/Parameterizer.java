/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public interface Parameterizer {
    default <P extends Point> Array<Data<P>> parameterize(Array<P> points, Interval interval){
        return parameterize(points, interval.getFrom(), interval.getTo());
    }
    
    <P extends Point> Array<Data<P>> parameterize(Array<P> points, Double a, Double b);
    
    default <P extends Point> Array<Data<P>> parameterize(Array<P> points){
        return parameterize(points, 0.0, 1.0);
    }
}
