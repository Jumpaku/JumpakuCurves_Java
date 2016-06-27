/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.WeightedPoint;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point1D;

/**
 *
 * @author Jumpaku
 */
public class RationalBezierCurveBernstein implements RationalBezierCurve {

    private final Array<WeightedPoint> weightedPoints;
    private final BezierCurve pointBezier;
    private final BezierCurve1D weightBezier;
    public RationalBezierCurveBernstein(Array<WeightedPoint> wcps, Integer dimention) {
        pointBezier = BezierCurve.create(wcps.map(wcp -> wcp.getPoint()), dimention);
        weightBezier = BezierCurve1D.create(wcps.map(wcp -> new Point1D(wcp.getWeight())));
        this.weightedPoints = wcps;
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
        return pointBezier.getDomain();
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
        return Point.create(pointBezier.evaluate(t).getVec().scale(1.0/weightBezier.evaluate(t).getX()));
    }

    @Override
    public Integer getDimention() {
        return pointBezier.getDimention();
    }
}
