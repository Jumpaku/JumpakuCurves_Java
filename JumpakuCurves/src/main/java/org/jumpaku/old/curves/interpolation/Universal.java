/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.interpolation;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.old.curves.utils.GoldenSectionSearch;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Vec;
import org.jumpaku.old.curves.spline.BSpline;

/**
 *
 * @author Jumpaku
 */
public class Universal implements Parameterizer{

    private final Integer degree;

    public Universal(Integer degree) {
        this.degree = degree;
    }
    
    
    @Override
    public <P extends Point> Array<Data<P>> parameterize(Array<P> points, Double a, Double b) {
        Double d = (b-a)/(points.size() - degree);
        Array<Double> knots = Stream.fill(degree + 1, () -> a)
                .appendAll(Stream.range(1, points.size() - degree).map(i -> a + i*d))
                .appendAll(Stream.fill(degree + 1, () -> b))
                .toArray();
        
        return points.zipWithIndex()
                .map(pt -> pt.transform((p, i) -> new Data<>(p, GoldenSectionSearch.whereMaximum(t -> BSpline.bSplineBasis(degree, i.intValue(), t, knots), 
                            knots.get(i.intValue()), knots.get(i.intValue() + degree + 1)))));
    }
    
}
