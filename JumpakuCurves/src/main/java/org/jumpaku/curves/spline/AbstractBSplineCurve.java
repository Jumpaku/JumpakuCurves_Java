/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public ClosedDomain getDomain() {
        return domain;
    }

    @Override
    public List<V> getControlPoints() {
        return controlPoints;
    }

    @Override
    public List<Double> getKnots() {
        return knots;
    }

    @Override
    public Integer getDegree() {
        return degree;
    }

    @Override
    public abstract V evaluate(Double t);
}
