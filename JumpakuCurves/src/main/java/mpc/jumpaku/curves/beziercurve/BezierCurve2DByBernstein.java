/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import fj.data.Stream;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import org.apache.commons.math3.geometry.Vector;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;

/**
 *
 * @author ito
 * @param <V>
 */
public class BezierCurve2DByBernstein<V extends Vector> extends AbstractBezierCurve<V> {
    private final fj.data.List<UnaryOperator<Double>> bernsteinBasis;
    public BezierCurve2DByBernstein(List<V> cp) {
        super(cp);
        final Integer degree = super.getControlPoints().size() - 1;
        final fj.data.List<Integer> range = fj.data.List.range(0, degree + 1);
        final fj.data.List<Double> conbinations = range.map(i -> binomialCoefficientDouble(degree, i));
        this.bernsteinBasis = conbinations.zipWith(range, (c, i) -> (t -> c*Math.pow(t, i)*Math.pow(1-t, degree-i)));
    }
    
    public BezierCurve2DByBernstein(V... cp) {
        this(Arrays.asList(cp));
    }
    
    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t)){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        return (V) bernsteinBasis.toStream()
                .zipWith(Stream.iterableStream(getControlPoints()), (b, cp) -> cp.scalarMultiply(b.apply(t)))
                .foldLeft1((v1, v2) -> v1.add(v2));
    }
}
