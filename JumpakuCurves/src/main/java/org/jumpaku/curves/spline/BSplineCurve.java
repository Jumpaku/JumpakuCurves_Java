/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import java.util.ArrayList;
import java.util.Collections;
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
public class BSplineCurve<S extends Space, V extends Vector<S>> implements SplineCurve<S, V>{

    private final ClosedDomain domain;
    private final List<Double> knots;
    private final List<V> controlPoints;
    private final Integer degree;

    public BSplineCurve(List<Double> knots, List<V> controlPoints, Integer degree) {
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
        
        if(controlPoints.size() - 1 != knots.size() - degree - 2)
            throw new IllegalArgumentException("control points and knots are wrong");
        
        this.knots = new ArrayList<>(knots);
        this.controlPoints = new ArrayList<>(controlPoints);
        this.degree = degree;
        this.domain = new ClosedDomain(knots.get(degree-1), knots.get(knots.size()-degree-1));  
    }
    
    @Override
    public ClosedDomain getDomain() {
        return domain;
    }

    @Override
    public List<V> getControlPoints() {
        return Collections.unmodifiableList(controlPoints);
    }

    @Override
    public List<Double> getKnots() {
        return Collections.unmodifiableList(knots);
    }

    @Override
    public Integer getDegree() {
        return degree;
    }

    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t is out of domain");
        
        return (V) Stream.ofAll(getControlPoints()).zipWithIndex().map(cpi -> cpi.transform(
                        (cp, i) -> cp.scalarMultiply(bSplineBasis(i.intValue(), getDegree(), getKnots(), t))))
                .reduce((v1, v2) -> v1.add(v2));
    }

    private static Double coefficient(Double a, Double b, Double c, Double d){
        return c.compareTo(d) == 0 ? 0 : (a-b)/(c-d);
    }
    
    private static Double bSplineBasis(Integer j, Integer n, List<Double> knots, Double t){
        if(n == 0)
            return (knots.get(j).compareTo(t) <= 0 && knots.get(j+1).compareTo(t) > 0) ? 1.0 : 0.0;
        
        Double left = coefficient(t, knots.get(j), knots.get(j+n), knots.get(j));
        if(left.compareTo(0.0) != 0){
            left = left * bSplineBasis(j, n-1, knots, t);
        }
        Double right = coefficient(knots.get(j+n+1), t, knots.get(j+n+1), knots.get(j+1));
        if(right.compareTo(0.0) != 0){
            right = right * bSplineBasis(j+1, n-1, knots, t);
        }
        return left + right;
    }
    
}
