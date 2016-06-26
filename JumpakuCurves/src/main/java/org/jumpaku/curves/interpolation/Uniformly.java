/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 */
public class Uniformly implements Parameterizer{

    @Override
    public <V extends Vec> Array<Data<V>> parameterize(Array<V> points, Double a, Double b) {
        Double d = (b - a) / (points.size() - 1.0);
        return Stream.ofAll(points).zipWithIndex().map(t -> t.transform(
                (p, i) -> new Data<>(p, a + i*d)))
                .toArray();
    }
    
}
