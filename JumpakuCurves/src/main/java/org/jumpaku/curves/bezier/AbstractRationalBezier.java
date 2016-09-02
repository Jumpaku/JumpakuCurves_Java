/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import java.util.LinkedList;
import javaslang.collection.Array;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point1D;
import org.jumpaku.curves.vector.Vec;
import org.jumpaku.curves.vector.WeightedPoint;

/**
 *
 * @author ito tomohiko
 */
public abstract class AbstractRationalBezierCurve implements RationalBezierCurve{

    private final Array<? extends WeightedPoint> weightedPoints;
    
    private final BezierCurve productBezier;
    
    private final BezierCurve1D weightBezier;
    
    private final Integer dimention;

    public AbstractRationalBezierCurve(Array<? extends WeightedPoint> weightedPoints, Integer dimention) {
        this.weightedPoints = weightedPoints;
        this.productBezier = BezierCurve.create(weightedPoints.map(WeightedPoint::getProduct), dimention);
        this.weightBezier = BezierCurve1D.create(weightedPoints.map(wcp -> new Point1D(wcp.getWeight())));
        this.dimention = dimention;
    }
    
    @Override
    public Array<Double> getWeights() {
        return weightedPoints.map(WeightedPoint::getWeight);
    }

    @Override
    public Array<? extends WeightedPoint> getWeightedPoints() {
        return weightedPoints;
    }

    @Override
    public Array<? extends Point> getControlPoints() {
        return weightedPoints;
    }

    @Override
    public Integer getDimention() {
        return dimention;
    }

    @Override
    abstract public Point evaluate(Double t);

    @Override
    public Vec computeTangent(Double t) {
        return productBezier.computeTangent(t)
                .sub(weightBezier.computeTangent(t).getX(), evaluate(t).getVec())
                .scale(1.0/weightBezier.evaluate(t).getX());
    }

    @Override
    public Array<? extends RationalBezierCurve> divide(Double t) {
        Array<Array<? extends WeightedPoint>> wcps = createDividedControlPoints(t);
        return Array.of(new RationalBezierCurveBernstein(wcps.get(0), dimention), new RationalBezierCurveBernstein(wcps.get(1), dimention));
    }

    private Array<Array<? extends WeightedPoint>> createDividedControlPoints(Double t){
        Double[] w = getWeights().toJavaArray(Double.class);
        Vec[] wcp = getWeightedPoints().map(x -> x.getProduct().getVec()).toJavaArray(Vec.class);
        LinkedList<WeightedPoint> wcp0 = new LinkedList<>();
        LinkedList<WeightedPoint> wcp1 = new LinkedList<>();
        int n = wcp.length - 1;
        wcp0.addLast(WeightedPoint.of(w[0], Point.of(wcp[0].scale(1.0/w[0]))));
        wcp1.addFirst(WeightedPoint.of(w[n], Point.of(wcp[n].scale(1.0/w[n]))));
        while(n > 0){
            for(int i = 0; i < n; ++i){
                wcp[i] = Vec.add(1-t, wcp[i], t, wcp[i+1]);
                w[i] = (1-t) * w[i] + t * w[i+1];
            }
            wcp0.addLast(WeightedPoint.of(w[0], Point.of(wcp[0].scale(1.0/w[0]))));
            wcp1.addFirst(WeightedPoint.of(w[n-1], Point.of(wcp[n-1].scale(1.0/w[n-1]))));
            --n;
        }
        
        return Array.of(Array.ofAll(wcp0), Array.ofAll(wcp1));
    }
    @Override
    public RationalBezierCurve elevate() {
        return new RationalBezierCurveBernstein(productBezier.elevate().getControlPoints().zip(weightBezier.elevate().getControlPoints())
                .map(t -> t.transform((wp, w) -> WeightedPoint.of(w.getX(), Point.of(wp.getVec().scale(1.0/w.getX()))))), dimention);
    }

    @Override
    public RationalBezierCurve reduce() {
        return new RationalBezierCurveBernstein(productBezier.reduce().getControlPoints().zip(weightBezier.reduce().getControlPoints())
                .map(t -> t.transform((wp, w) -> WeightedPoint.of(w.getX(), Point.of(wp.getVec().scale(1.0/w.getX()))))), dimention);    }

    @Override
    public RationalBezierCurve reverse() {
        return new RationalBezierCurveBernstein(getWeightedPoints().reverse(), getDimention());
    }

    protected BezierCurve getProductBezier() {
        return productBezier;
    }

    protected BezierCurve1D getWeightBezier() {
        return weightBezier;
    }
}
