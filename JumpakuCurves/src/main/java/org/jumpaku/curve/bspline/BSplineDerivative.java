package org.jumpaku.curve.bspline;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.control.Option;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.*;
import org.jumpaku.curve.bezier.BezierDerivative;

/**
 * Created by jumpaku on 2017/05/01.
 */
public class BSplineDerivative implements Derivative, Differentiable, Reversible<BSplineDerivative>, Restrictable<Derivative> {

    public static BSplineDerivative create(BSpline bSpline){
        return create(bSpline.getDegree(),
                bSpline.getControlPoints().map(Point::toCrisp).map(Point.Crisp::toVector),
                bSpline.getKnots());
    }

    public static BSplineDerivative create(Integer degree, Iterable<? extends Vector.Crisp> controlVectors, Iterable<Knot> knots){
        return new BSplineDerivative(BSpline.create(degree, Array.ofAll(controlVectors).map(Point.Crisp::new), knots));
    }

    public static String toJson(BSplineDerivative bSpline){
        return JsonBSplineDerivative.CONVERTER.toJson(bSpline);
    }

    public static Option<BSplineDerivative> fromJson(String json){
        return JsonBSplineDerivative.CONVERTER.fromJson(json);
    }

    private final BSpline bSpline;

    public BSplineDerivative(BSpline bSpline) {
        this.bSpline = bSpline;
    }

    @Override
    public String toString() {
        return BSplineDerivative.toJson(this);
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

    public BSplineDerivative insertKnotMultiple(Double t, Integer m){
        return create(bSpline.insertKnotMultiple(t, m));
    }

    public BSplineDerivative insertKnot(Integer i){
        return create(bSpline.insertKnot(i));
    }

    public BSplineDerivative insertKnotMultiple(Integer i, Integer m){
        return create(bSpline.insertKnotMultiple(i, m));
    }

    public Array<BezierDerivative> toBeziers(){
        return bSpline.toBeziers().map(BezierDerivative::create);
    }

    public Tuple2<BSplineDerivative, BSplineDerivative> subdivide(Double t){
        return bSpline.subdivide(t).map(
                BSplineDerivative::create, BSplineDerivative::create);
    }

    public BSpline toBSpline(){
        return bSpline;
    }
}
