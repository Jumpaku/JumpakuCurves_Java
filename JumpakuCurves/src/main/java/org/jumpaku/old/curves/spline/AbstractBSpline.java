/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.spline;

import java.util.ArrayList;
import javaslang.collection.List;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.old.curves.domain.Closed;
import org.jumpaku.old.curves.domain.Interval;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 */
public abstract class AbstractBSpline implements BSpline {
        
    private final Interval domain;
    private final Array<Double> knots;
    private final Array<Point> controlPoints;
    private final Integer degree;
    private final Integer dimention;

    public AbstractBSpline(Array<Double> knots, Array<Point> controlPoints, Integer degree, Integer dimention) {
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
    public final BSpline insertKnot(Double u){
        if(!getDomain().contains(u))
            throw new IllegalArgumentException("New knot to add is out of domain.");
        
        final Array<Double> oknots = getKnots();
        final Integer k = oknots.lastIndexWhere(knot -> knot <= u);
        final Integer n = getDegree();
        
        final List<Point> ocps = getControlPoints().toList();
        List<Point> tmp = List.empty();
        for(int i = k; i >= k-n+1; --i){
            Double a = (u - oknots.get(i)) / (oknots.get(i+n) - oknots.get(i));
            tmp = tmp.prepend(Point.of(ocps.get(i - 1).getVec().scale(1.0 - a).add(ocps.get(i).getVec().scale(a))));
        }
        
        Array<Point> ncps = Array.ofAll(ocps.subSequence(k, ocps.size()).prependAll(tmp).prependAll(ocps.subSequence(0, k-n + 1)));
        Array<Double> nknots = oknots.insert(k + 1, u);
        
        final Spline original = this;        
        return new AbstractBSpline(nknots, ncps, n, getDimention()) {
            @Override
            public Point evaluate(Double t) {
                return original.evaluate(t);
            }
        };
    }

    @Override
    public Vec computeTangent(Double t) {
        Array<Point> cp = getControlPoints();
        Integer size = cp.size() - 1;
        java.util.List<Vec> terms = new ArrayList<>(size);
        Integer p = getDegree() - 1;
        for(int i = 1; i <= size; ++i){
            terms.add(cp.get(i).from(cp.get(i-1)).scale(BSpline.bSplineBasis(p, i, t, getKnots())));
        }
        
        return Stream.ofAll(terms).reduce((v1, v2) -> v1.add(v2));
    }
    
    
    
    @Override
    public abstract Point evaluate(Double t);
}
