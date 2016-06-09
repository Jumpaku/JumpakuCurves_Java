/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.domain.ClosedDomain;

/**
 *
 * @author ito tomohiko
 * @param <S>
 * @param <V>
 */
public abstract class AbstractBSplineCurve<S extends Space, V extends Vector<S>> implements SplineCurve<S, V> {
        
    private final ClosedDomain domain;
    private final List<Double> knots;
    private final List<V> controlPoints;
    private final Integer degree;

    public AbstractBSplineCurve(List<Double> knots, List<V> controlPoints, Integer degree) {
        if(knots.stream().anyMatch(k -> k == null))
            throw new IllegalArgumentException("knots contain null");
        
        if(controlPoints.stream().anyMatch(cp -> cp == null))
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
        
        this.knots = Collections.unmodifiableList(new ArrayList<>(knots));
        this.controlPoints = Collections.unmodifiableList(new ArrayList<>(controlPoints));
        this.degree = degree;
        this.domain = new ClosedDomain(knots.get(degree), knots.get(knots.size() - degree - 1));  
    }
    
    @Override
    public final ClosedDomain getDomain() {
        return domain;
    }

    @Override
    public final List<V> getControlPoints() {
        return controlPoints;
    }

    @Override
    public final List<Double> getKnots() {
        return knots;
    }

    @Override
    public final Integer getDegree() {
        return degree;
    }

    @Override
    public final SplineCurve<S, V> insertKnot(Double u){
        if(!getDomain().isIn(u))
            throw new IllegalArgumentException("New knot to add is out of domain.");
        
        List<Double> nknots = new LinkedList<>(getKnots());
        Integer k = Stream.ofAll(nknots).lastIndexWhere(knot -> knot <= u);

        Integer p = getDegree();
        
        List<V> ncps = new LinkedList<>();  
        List<V> ocps = getControlPoints();
        ncps.addAll(ocps.subList(0, k-1));
        List<V> tmp = new LinkedList<>();
        for(int i = k-p+1; i <= k; ++i){
            Double a = (u - nknots.get(i)) / (nknots.get(i+p) - nknots.get(i));
            tmp.add((V)ocps.get(i - 1).scalarMultiply(1.0 - a).add(ocps.get(i).scalarMultiply(a)));
        }
        ncps.addAll(tmp);
        ncps.addAll(ocps.subList(k, ocps.size()-1));

        nknots.add(u);
        Collections.sort(nknots);
        
        final SplineCurve<S, V> original = this;
        
        return new AbstractBSplineCurve<S, V>(nknots, ncps, p) {
            @Override
            public V evaluate(Double t) {
                return original.evaluate(t);
            }
        };
        
    }
    
    @Override
    public abstract V evaluate(Double t);
}
