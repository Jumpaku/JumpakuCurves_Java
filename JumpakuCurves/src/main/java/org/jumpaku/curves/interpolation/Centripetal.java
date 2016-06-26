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
public class Centripetal implements Parameterizer{

    private final Double alpha; 
    
    public Centripetal(Double alpha) {
        this.alpha = alpha;
    }

    @Override
    public <V extends Vec> Array<Data<V>> parameterize(Array<V> points, Double a, Double b) {
        Stream<Double> distances = Stream.of(a);
        double tmp = 0.0;
        for(int i = 1; i < points.size(); ++i){
            distances = distances.append(tmp += Math.pow(points.get(i).sub(points.get(i-1)).square(), alpha/2));
        }        
        final Double total = tmp;
        
        return distances.map(d -> a + (b-a)*d/total).zip(points)
                .map(tp -> tp.transform((t, p) -> new Data<>(p, t)))
                .toArray();
    }
    
}
