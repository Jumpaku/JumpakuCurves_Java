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
public class Average implements KnotGenerator{

    static private <P extends Point> Double average(int i, int degree, Array<Data<P>> data){
        Double sum = 0.0;
        for(int j = i; j < i + degree; ++j){
            sum += data.get(j).getParam();
        }
        return sum / degree;
    }
    
    @Override
    public <P extends Point> Array<Double> generate(Integer degree, Array<Data<P>> data){
        return Stream.fill(degree + 1, () -> data.head().getParam())
                .appendAll(Stream.range(1, data.size() - degree).map(i -> average(i, degree, data)))
                .appendAll(Stream.fill(degree + 1, () -> data.last().getParam()))
                .toArray();
    }
    
}