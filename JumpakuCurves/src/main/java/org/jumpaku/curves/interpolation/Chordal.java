/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author Jumpaku
 */
public class Chordal implements Parameterizer{

    @Override
    public <S extends Space, V extends Vector<S>> Array<Data<S, V>> parameterize(Array<V> points, final Double a, final Double b) {
        Stream<Double> distances = Stream.empty();
        double tmp = 0.0;
        for(int i = 0; i < points.size() - 1; ++i){
            distances = distances.append(tmp += points.get(i).distance(points.get(i-1)));
        }        
        final Double total = tmp;
        
        return points.zip(distances.map(d -> a + (b-a)*d/total))
                .map(pt -> pt.transform((p, t) -> new Data<>(p, t)));
    }
    
}
