/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.WeightedPoint;
import org.jumpaku.curves.affine.Affine;

/**
 *
 * @author Jumpaku
 * @param <S>
 * @param <V>
 */
public class RationalBezierCurveBernstein<S extends Space, V extends Vector<S>> implements RationalBezierCurve<S, V>{

    private final Array<Double> weights;
    private final BezierCurve<S, V> bezier;
    public RationalBezierCurveBernstein(Array<V> cp, Array<Double> weights) {
        bezier = BezierCurve.create(cp);
        this.weights = weights;
    }

    @Override
    public Array<Double> getWeights() {
        return weights;
    }

    @Override
    public Array<WeightedPoint> getWeightedPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interval getDomain() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Array<V> getControlPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getDegree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BezierCurve<S, V> elevate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BezierCurve<S, V> reduce() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Array<? extends BezierCurve<S, V>> divide(Double t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BezierCurve<S, V> transform(Affine<S, V> transform) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BezierCurve<S, V> reverse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V computeTangent(Double t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V evaluate(Double t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
