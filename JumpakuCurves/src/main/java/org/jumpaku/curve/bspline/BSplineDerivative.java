package org.jumpaku.curve.bspline;

import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.*;
import org.jumpaku.curve.bezier.BezierDerivative;

/**
 * Created by jumpaku on 2017/05/01.
 */
public class BSplineDerivative implements Derivative, Differentiable, Reversible<BSplineDerivative> {

    public static BSplineDerivative create(BSpline bSpline){
        return new BSplineDerivative(bSpline);
    }

    public static BSplineDerivative create(Interval domain, Integer degree, Iterable<? extends Vector.Crisp> controlVectors, Iterable<Knot> knots){
        return create(BSpline.create(domain, degree, Array.ofAll(controlVectors).map(Point.Crisp::new), knots));
    }

    private final BSpline bSpline;

    public BSplineDerivative(BSpline bSpline) {
        this.bSpline = bSpline;
    }

    @Override
    public BSplineDerivative restrict(Double begin, Double end) {
        return create(bSpline.restrict(begin, end));
    }

    @Override
    public BSplineDerivative restrict(Interval i) {
        return create(bSpline.restrict(i));
    }

    @Override
    public Vector.Crisp evaluate(Double t) {
        return bSpline.evaluate(t).toVector().toCrisp();
    }

    @Override
    public Interval getDomain() {
        return bSpline.getDomain();
    }

    public Array<Vector.Crisp> getControlVectors(){
        return bSpline.getControlPoints().map(p -> p.toCrisp().toVector());
    }

    public Array<Knot> getKnots(){
        return bSpline.getKnots();
    }

    public Array<Double> getKnotValues(){
        return bSpline.getKnotValues();
    }

    public Integer getDegree(){
        return bSpline.getDegree();
    }

    public BSplineDerivative insertKnot(Double t){
        return create(bSpline.insertKnot(t));
    }

    public Array<BezierDerivative> toBeziers(){
        return bSpline.toBeziers().map(BezierDerivative::create);
    }

    public Tuple2<BSplineDerivative, BSplineDerivative> subdivide(Double t){
        return bSpline.subdivide(t).map(
                BSplineDerivative::create, BSplineDerivative::create);
    }

    @Override
    public BSplineDerivative reverse() {
        return create(bSpline.reverse());
    }

    @Override
    public Vector.Crisp differentiate(Double t) {
        return bSpline.differentiate(t);
    }

    @Override
    public BSplineDerivative differentiate() {
        return bSpline.differentiate();
    }
}
