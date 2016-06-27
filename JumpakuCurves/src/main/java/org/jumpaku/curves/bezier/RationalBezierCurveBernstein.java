/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Domain;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.WeightedPoint;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point1D;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 */
public class RationalBezierCurveBernstein implements RationalBezierCurve {

    private final Array<WeightedPoint> weightedPoints;
    
    private final Array<Double> cofficients;
    
    private final BezierCurve1D weightBezier;
    
    private final Integer dimention;
    
    private final Integer degree;
    
    private static final Interval domain = new Closed(0.0, 1.0);
    
    public RationalBezierCurveBernstein(Array<WeightedPoint> wcps, Integer dimention) {
        this.dimention = dimention;
        this.degree = wcps.size() - 1;
        this.weightedPoints = wcps;
        this.cofficients = BezierCurve.combinations(degree).toStream()
                .zip(wcps.toStream().map(wcp -> wcp.getWeight()))
                .map(bw -> bw.transform((b, w) -> b*w))
                .toArray();
        this.weightBezier = BezierCurve1D.create(wcps.map(wcp -> new Point1D(wcp.getWeight())));
    }

    @Override
    public Array<Double> getWeights() {
        return weightedPoints.map(wp -> wp.getWeight());
    }

    @Override
    public Array<WeightedPoint> getWeightedPoints() {
        return weightedPoints;
    }

    @Override
    public Interval getDomain() {
        return domain;
    }

    @Override
    public Array<Point> getControlPoints() {
        return weightedPoints.map(wp -> wp.getPoint());
    }

    @Override
    public Integer getDegree() {
        return weightedPoints.size() - 1;
    }

    @Override
    public Point evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("Parameter t out of domain [0,1]");
        
        Array<Point> cps = getControlPoints();

        if(!Double.isFinite(1.0/(1.0-t))){
            return cps.get(degree);
        }
        if(!Double.isFinite(1.0/t)){
            return cps.get(0);
        }
        
        Double ct = Math.pow(1-t, degree);
        Vec v = Vec.zero(getDimention());
        
        for(int i = 0; i <= degree; ++i){
            v = v.add(cps.get(i).getVec().scale(cofficients.get(i)*ct));
            ct *= (t / (1 - t));
        }
        
        return Point.create(v.scale(1.0/weightBezier.evaluate(t).getX()));
    }

    @Override
    public Integer getDimention() {
        return dimention;
    }
}
