/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.List;
import javaslang.collection.Array;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 * @param <S>
 * @param <V>
 */
public abstract class AbstractBSplineCurve<V extends Vec> implements BSplineCurve<V> {
        
    private final Interval domain;
    private final Array<Double> knots;
    private final Array<V> controlPoints;
    private final Integer degree;

    public AbstractBSplineCurve(Array<Double> knots, Array<V> controlPoints, Integer degree) {
        if(knots.exists(k -> k == null))
            throw new IllegalArgumentException("knots contain null");
        
        if(controlPoints.exists(cp -> cp == null))
            throw new IllegalArgumentException("control points contain null");
        
        for(int i = 0; i < knots.size()-1; ++i){
            if(knots.get(i) > knots.get(i+1))
                throw new IllegalArgumentException("knots must be in ascending order, but knot[" + i +  "] > knot[" + (i+1)+ "]");
        }
        
        if(controlPoints.isEmpty())
            throw new IllegalArgumentException("control points must be not empty");
        
        if(degree < 0)
            throw new IllegalArgumentException("degree must be positive or 0");
        
        if(controlPoints.size() != knots.size() - degree - 1)
            throw new IllegalArgumentException("control points and knots are wrong");
        
        this.knots = knots;
        this.controlPoints = controlPoints;
        this.degree = degree;
        this.domain = new Closed(knots.get(degree), knots.get(knots.size() - degree - 1));  
    }
    
    @Override
    public Interval getDomain() {
        return domain;
    }

    @Override
    public final Array<V> getControlPoints() {
        return controlPoints;
    }

    @Override
    public final Array<Double> getKnots() {
        return knots;
    }

    @Override
    public final Integer getDegree() {
        return degree;
    }

    @Override
    public final BSplineCurve<V> insertKnot(Double u){
        if(!getDomain().isIn(u))
            throw new IllegalArgumentException("New knot to add is out of domain.");
        
        final Array<Double> oknots = getKnots();
        final Integer k = oknots.lastIndexWhere(knot -> knot <= u);
        final Integer n = getDegree();
        
        final List<V> ocps = getControlPoints().toList();
        List<V> tmp = List.empty();
        for(int i = k; i >= k-n+1; --i){
            Double a = (u - oknots.get(i)) / (oknots.get(i+n) - oknots.get(i));
            tmp = tmp.prepend((V)ocps.get(i - 1).scale(1.0 - a).add(ocps.get(i).scale(a)));
        }
        
        Array<V> ncps = Array.ofAll(ocps.subSequence(k, ocps.size()).prependAll(tmp).prependAll(ocps.subSequence(0, k-n + 1)));
        Array<Double> nknots = oknots.insert(k + 1, u);
        
        final SplineCurve<V> original = this;        
        return new AbstractBSplineCurve<V>(nknots, ncps, n) {
            @Override
            public V evaluate(Double t) {
                return original.evaluate(t);
            }
        };
    }
    
    @Override
    public abstract V evaluate(Double t);
}
