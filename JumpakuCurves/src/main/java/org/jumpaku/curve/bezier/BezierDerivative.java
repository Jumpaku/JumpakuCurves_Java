/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.control.Option;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Reversible;

/**
 *
 * @author Jumpaku
 */
public final class BezierDerivative implements Derivative, Differentiable, Reversible<BezierDerivative> {

    public static BezierDerivative create(Bezier bezier){
        return new BezierDerivative(bezier);
    }

    public static BezierDerivative create(Iterable<? extends Vector.Crisp> vs){
        return create(Bezier.create(Array.ofAll(vs).map(Point.Crisp::new)));
    }

    public static String toJson(BezierDerivative db){
        return JsonBezierDerivative.CONVERTER.toJson(db);
    }

    public static Option<BezierDerivative> fromJson(String json){
        return JsonBezierDerivative.CONVERTER.fromJson(json);
    }

    private final Bezier bezier;

    public BezierDerivative(Bezier bezier){
        this.bezier = bezier;
    }

    @Override
    public Interval getDomain() {
        return bezier.getDomain();
    }

    @Override
    public Vector.Crisp evaluate(Double t) {
        return bezier.evaluate(t).toVector().toCrisp();
    }

    @Override
    public BezierDerivative differentiate() {
        return bezier.differentiate();
    }

    @Override
    public BezierDerivative restrict(Interval i) {
        return create(bezier.restrict(i));
    }

    @Override
    public BezierDerivative restrict(Double begin, Double end){
        return create(bezier.restrict(begin, end));
    }

    @Override
    public BezierDerivative reverse() {
                return create(bezier.reverse());
            }

    @Override
    public String toString() {
        return JsonBezierDerivative.CONVERTER.toJson(this);
    }

    public Array<Vector> getControlVectors() {
        return bezier.getControlPoints().map(Point::toVector);
    }

    public Integer getDegree() {
        return bezier.getDegree();
    }

    public BezierDerivative elevate() {
        return create(bezier.elevate());
    }

    public BezierDerivative reduce() {
        return create(bezier.reduce());
    }

    public Tuple2<BezierDerivative, BezierDerivative> subdivide(Double t) {
        return bezier.subdivide(t).map(BezierDerivative::create, BezierDerivative::create);
    }

    public Bezier toBezier() {
        return bezier;
    }
}
