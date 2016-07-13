/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import java.util.LinkedList;
import javaslang.collection.Array;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.WeightedPoint;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point1D;
import org.jumpaku.curves.vector.Vec;
import org.jumpaku.curves.vector.Vec1;

/**
 *
 * @author Jumpaku
 */
public class RationalBezierCurveBernstein implements RationalBezierCurve {

    private final Array<? extends WeightedPoint> weightedPoints;
    
    private final BezierCurve1D weightBezier;
    
    private final BezierCurve bezier;
    
    private final Integer dimention;
    
    private static final Interval DOMAIN = new Closed(0.0, 1.0);
    
    public RationalBezierCurveBernstein(Array<? extends WeightedPoint> wcps, Integer dimention) {
        this.dimention = dimention;
        this.weightedPoints = wcps;
        this.weightBezier = BezierCurve1D.create(wcps.map(wcp -> new Point1D(wcp.getWeight())));
        this.bezier = BezierCurve.create(wcps.map(wcp -> wcp.getProduct()), dimention);
    }

    public RationalBezierCurveBernstein(Array<? extends Point> cps, Array<Double> weights, Integer dimention) {
        this(cps.zip(weights).map(cpw -> WeightedPoint.of(cpw._1(), cpw._2())), dimention);
    }
    
    @Override
    public Array<Double> getWeights() {
        return weightedPoints.map(wp -> wp.getWeight());
    }

    @Override
    public Array<? extends WeightedPoint> getWeightedPoints() {
        return weightedPoints;
    }

    @Override
    public Interval getDomain() {
        return DOMAIN;
    }

    @Override
    public Array<? extends Point> getControlPoints() {
        return weightedPoints;
    }

    @Override
    public Integer getDegree() {
        return weightedPoints.size() - 1;
    }

    @Override
    public Point evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("Parameter t out of domain [0,1]");
        
        return Point.of(bezier.evaluate(t).getVec().scale(1.0/weightBezier.evaluate(t).getX()));
    }

    @Override
    public Integer getDimention() {
        return dimention;
    }

    @Override
    public Vec computeTangent(Double t) {
        return bezier.computeTangent(t).sub(weightBezier.computeTangent(t).getX(), evaluate(t).getVec()).scale(1.0/weightBezier.evaluate(t).getX());
    }

    private static Array<Array<? extends WeightedPoint>> createDividedControlPoints(Vec[] wcp, Double[] w, Double t){
        LinkedList<WeightedPoint> wcp0 = new LinkedList<>();
        LinkedList<WeightedPoint> wcp1 = new LinkedList<>();
        int n = wcp.length - 1;
        wcp0.addLast(WeightedPoint.of(Point.of(wcp[0].scale(1.0/w[0])), w[0]));
        wcp1.addFirst(WeightedPoint.of(Point.of(wcp[n].scale(1.0/w[n])), w[n]));
        while(n > 0){
            for(int i = 0; i < n; ++i){
                wcp[i] = Vec.add(1-t, wcp[i], t, wcp[i+1]);
                w[i] = (1-t) * w[i] + t * w[i+1];
            }
            wcp0.addLast(WeightedPoint.of(Point.of(wcp[0].scale(1.0/w[0])), w[0]));
            wcp1.addFirst(WeightedPoint.of(Point.of(wcp[n-1].scale(1.0/w[n-1])), w[n-1]));
            --n;
        }
        
        return Array.of(Array.ofAll(wcp0), Array.ofAll(wcp1));
    }
    
    @Override
    public Array<? extends BezierCurve> divide(Double t) {
        Array<Array<? extends WeightedPoint> wcps = createDividedControlPoints(getWeightedPoints().map(WeightedPoint::getProduct).map(Point::getVec).toJavaArray(Vec.class), getWeights().toJavaArray(Double.class), t);
         return Array.of(new RationalBezierCurveBernstein(, dimention))
    }

    @Override
    public BezierCurve elevate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BezierCurve reduce() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BezierCurve reverse() {
        return new RationalBezierCurveBernstein(weightedPoints.reverse(), dimention);
    }
}
