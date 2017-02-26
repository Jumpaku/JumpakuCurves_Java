/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.DefinedOnInterval;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.Interval;

/**
 *
 * @author tomohiko
 */
public interface BezierDerivative extends Derivative, Differentiable, DefinedOnInterval<BezierDerivative>{

    static BezierDerivative create(Bezier bezier){
        Array<Vector> controlVectors = bezier.getControlPoints().map(Point::getVector);
        Interval interval = bezier.getDomain();
        return new BezierDerivative() {
            @Override public Interval getDomain() {
                return bezier.getDomain();
            }

            @Override public Vector evaluate(Double t) {
                return bezier.evaluate(t).getVector();
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
                return controlVectors;
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
            public Tuple2<? extends BezierDerivative, ? extends BezierDerivative> subdivide(Double t) {
                return bezier.subdivide(t).map(BezierDerivative::create, BezierDerivative::create);
            }
        };
    }

    public static BezierDerivative create(Array<Vector> vs, Interval domain){
        return create(Bezier.create(vs.map(Point::of),domain));
    }

    @Override Interval getDomain();
    
    @Override Vector evaluate(Double t);

    @Override BezierDerivative differentiate();

    @Override BezierDerivative restrict(Interval i);

    @Override BezierDerivative reverse();

    Array<Vector> getControlVectors();

    Integer getDegree();
    
    BezierDerivative elevate();
    
    BezierDerivative reduce();
    
    Tuple2<? extends BezierDerivative, ? extends BezierDerivative> subdivide(Double t);    
}
