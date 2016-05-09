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
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;

/**
 *
 * @author ito
 */
public class BezierCurve2DByBernstein extends BezierCurve2D {
    private final Stream<UnaryOperator<Double>> bernsteinBasis;
    public BezierCurve2DByBernstein(List<Vector2D> cp) {
        super(cp);
        final Integer degree = super.getControlPoints().size() - 1;
        this.bernsteinBasis = Stream.range(0, degree + 1)
                .map(i -> (t -> binomialCoefficientDouble(degree, i)*Math.pow(t, i)*Math.pow(1-t, degree-i)));
    }
    
    public BezierCurve2DByBernstein(Vector2D... cp) {
        this(Arrays.asList(cp));
    }
    
    @Override
    public Vector2D evaluate(Double t) {
        if(!getDomain().isIn(t)){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        return bernsteinBasis.zipWith(Stream.iterableStream(getControlPoints()), (b, cp)-> cp.scalarMultiply(b.apply(t)))
                .foldLeft1((v1, v2) -> v1.add(v2));
    }
}
