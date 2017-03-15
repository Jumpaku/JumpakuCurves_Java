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
import org.jumpaku.curve.Reverseable;

/**
 *
 * @author Jumpaku
 */
public interface BezierDerivative extends Derivative, Differentiable, Reverseable<BezierDerivative>{

    static BezierDerivative create(Bezier bezier){
        return new BezierDerivative() {
            @Override public Interval getDomain() {
                return bezier.getDomain();
            }

            @Override public Vector.Crisp evaluate(Double t) {
                return bezier.evaluate(t).toVector().toCrisp();
            }
            
            @Override public BezierDerivative differentiate() {
                return bezier.differentiate();
            }

            @Override public BezierDerivative restrict(Interval i) {
                return create(bezier.restrict(i));
            }

            @Override public BezierDerivative reverse() {
                return create(bezier.reverse());
            }

            @Override public Array<Vector> getControlVectors() {
                return bezier.getControlPoints().map(Point::toVector);
            }

            @Override public Integer getDegree() {
                return bezier.getDegree();
            }

            @Override public BezierDerivative elevate() {
                return create(bezier.elevate());
            }

            @Override public BezierDerivative reduce() {
                return create(bezier.reduce());
            }

            @Override
            public Tuple2<BezierDerivative, BezierDerivative> subdivide(Double t) {
                return bezier.subdivide(t).map(BezierDerivative::create, BezierDerivative::create);
            }

            @Override public String toString() {
                return super.toString(); 
            }
        };
    }

    public static BezierDerivative create(Interval domain, Array<? extends Vector.Crisp> vs){
        return create(Bezier.create(domain, vs.map(Point.Crisp::new)));
    }
    
    public static String toJson(BezierDerivative db){
        return JsonBezierDerivative.CONVERTER.toJson(db);
    }
    
    public static Option<BezierDerivative> fromJson(String json){
        return JsonBezierDerivative.CONVERTER.fromJson(json);
    }

    @Override Interval getDomain();
    
    @Override Vector.Crisp evaluate(Double t);

    @Override BezierDerivative differentiate();

    @Override BezierDerivative restrict(Interval i);

    @Override default BezierDerivative restrict(Double begin, Double end){
        return restrict(Interval.of(begin, end));
    }

    @Override BezierDerivative reverse();

    Array<Vector> getControlVectors();

    Integer getDegree();
    
    BezierDerivative elevate();
    
    BezierDerivative reduce();
    
    Tuple2<BezierDerivative, BezierDerivative> subdivide(Double t);    
}
