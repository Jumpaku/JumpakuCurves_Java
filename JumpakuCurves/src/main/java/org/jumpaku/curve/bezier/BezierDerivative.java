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

    private final Bezier bezier;

    public BezierDerivative(Bezier bezier){
        this.bezier = bezier;
    }

    public Interval getDomain() {
        return bezier.getDomain();
    }

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
    public BezierDerivative reverse() {
                return create(bezier.reverse());
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

    @Override
    public String toString() {
                return JsonBezierDerivative.CONVERTER.toJson(this);
            }


    public static BezierDerivative create(Bezier bezier){
        return new BezierDerivative(bezier);
    }

    public static BezierDerivative create(Interval domain, Iterable<? extends Vector.Crisp> vs){
        return create(Bezier.create(domain, Array.ofAll(vs).map(Point.Crisp::new)));
    }
    
    public static String toJson(BezierDerivative db){
        return JsonBezierDerivative.CONVERTER.toJson(db);
    }
    
    public static Option<BezierDerivative> fromJson(String json){
        return JsonBezierDerivative.CONVERTER.fromJson(json);
    }

    @Override
    public BezierDerivative restrict(Double begin, Double end){
        return restrict(Interval.of(begin, end));
    }
}
