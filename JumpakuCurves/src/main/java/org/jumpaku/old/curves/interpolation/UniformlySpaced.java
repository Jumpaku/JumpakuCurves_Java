/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.interpolation;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.old.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public class UniformlySpaced implements KnotGenerator{

    @Override
    public <P extends Point> Array<Double> generate(Integer degree, Array<Data<P>> data){
        Double d = (data.last().getParam() - data.head().getParam())/(data.size() - degree);
                
        return Stream.fill(degree + 1, ()->data.head().getParam())
                .appendAll(Stream.range(1, data.size() - degree).map(i -> i*d))
                .appendAll(Stream.fill(degree + 1, () -> data.last().getParam()))
                .toArray();
    }
    
}