/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import javaslang.Tuple;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.Vector;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;

/**
 *
 * @author ito
 * @param <V>
 */
public class BezierCurveByBernstein<V extends Vector> extends AbstractBezierCurve<V> {
    private final javaslang.collection.List<Double> conbinations;
    public BezierCurveByBernstein(List<V> cp) {
        super(cp);
        final Integer degree = super.getControlPoints().size() - 1;
        conbinations = javaslang.collection.List.range(0, degree + 1)
                .map(i -> binomialCoefficientDouble(degree, i));
    }
    
    public BezierCurveByBernstein(V... cp) {
        this(Arrays.asList(cp));
    }
    
    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("The parameter t must be in domain [0,1], but t = " + t);
        double degree = getDegree();
        return (V) Stream.ofAll(conbinations).zip(javaslang.collection.List.ofAll(getControlPoints()))
                .zipWithIndex()
                .map(ncmcpi -> ncmcpi.transform((ncmcp, i)-> Tuple.of(ncmcp._1(), ncmcp._2(), i)))
                .map(ncmcpi -> ncmcpi.transform((ncm, cp, i)->cp.scalarMultiply(ncm*Math.pow(t, i)*Math.pow(1-t, degree-i))))
                .reduce((v1, v2)->v1.add(v2));
    }
}
