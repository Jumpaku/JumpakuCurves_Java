/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author Jumpaku
 */
public class Centripetal implements Parameterizer{

    private final Double alpha; 
    
    public Centripetal(Double alpha) {
        this.alpha = alpha;
    }

    @Override
    public <S extends Space, V extends Vector<S>> Array<Data<S, V>> parameterize(Array<V> points, Double a, Double b) {
        return new Chordal().parameterize(points, a, b).map(d -> new Data(d.getPoint(), Math.pow(d.getParam(), alpha)));
    }
    
}
