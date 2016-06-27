/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.spline;

import javaslang.collection.List;
import javaslang.collection.Array;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public abstract class AbstractBSplineCurve implements BSplineCurve {
        
    private final Interval domain;
    private final Array<Double> knots;
    private final Array<Point> controlPoints;
    private final Integer degree;
    private final Integer dimention;

    public AbstractBSplineCurve(Array<Double> knots, Array<Point> controlPoints, Integer degree, Integer dimention) {
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
        
        if(controlPoints.exists(p -> p.getDimention() != dimention))
            throw new IllegalArgumentException("control points have defferent dimention");
        
        this.knots = knots;
        this.controlPoints = controlPoints;
        this.degree = degree;
        this.domain = new Closed(knots.get(degree), knots.get(knots.size() - degree - 1));
        this.dimention = dimention;
    }

    @Override
    public Integer getDimention() {
        return dimention;
    }
    
    @Override
    public Interval getDomain() {
        return domain;
    }

    @Override
    public final Array<Point> getControlPoints() {
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
    public final BSplineCurve insertKnot(Double u){
        if(!getDomain().isIn(u))
            throw new IllegalArgumentException("New knot to add is out of domain.");
        
        final Array<Double> oknots = getKnots();
        final Integer k = oknots.lastIndexWhere(knot -> knot <= u);
        final Integer n = getDegree();
        
        final List<Point> ocps = getControlPoints().toList();
        List<Point> tmp = List.empty();
        for(int i = k; i >= k-n+1; --i){
            Double a = (u - oknots.get(i)) / (oknots.get(i+n) - oknots.get(i));
            tmp = tmp.prepend(Point.create(ocps.get(i - 1).getVec().scale(1.0 - a).add(ocps.get(i).getVec().scale(a))));
        }
        
        Array<Point> ncps = Array.ofAll(ocps.subSequence(k, ocps.size()).prependAll(tmp).prependAll(ocps.subSequence(0, k-n + 1)));
        Array<Double> nknots = oknots.insert(k + 1, u);
        
        final SplineCurve original = this;        
        return new AbstractBSplineCurve(nknots, ncps, n, getDimention()) {
            @Override
            public Point evaluate(Double t) {
                return original.evaluate(t);
            }
        };
    }
    
    @Override
    public abstract Point evaluate(Double t);
}
