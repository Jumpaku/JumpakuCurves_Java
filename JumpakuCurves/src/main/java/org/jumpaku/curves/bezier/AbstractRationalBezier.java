/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import java.util.LinkedList;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import org.jumpaku.curves.utils.FuncUtils;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Point1D;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author ito tomohiko
 */
public abstract class AbstractRationalBezier implements RationalBezier{

    private final Array<? extends Point> controlPoints;
    
    private final Array<Double> weights;
    
    private final Integer dimention;

    public AbstractRationalBezier(Array<? extends Point> controlPoints, Array<Double> weights, Integer dimention) {
        this.controlPoints = controlPoints;
        this.weights = weights;
        this.dimention = dimention;
    }
    
    protected final Array<Tuple2<? extends Point, Double>> getPairs(){
        return Array.ofAll(getControlPoints().zip(getWeights()));
    }
    
    protected final Array<? extends Point> getProducts(){
        return getPairs().map(t -> t.transform((p, w) -> Point.of(p.getVec().scale(w))));
    }
    
    protected final Bezier getProductBezier(){
        return Bezier.create(getProducts(), dimention);
    }
    
    protected final Bezier1D getWeightBezier(){
        return Bezier1D.create(getWeights().map(Point1D::new));
    }
    
    @Override
    public Array<Double> getWeights() {
        return weights;
    }

    @Override
    public Array<? extends Point> getControlPoints() {
        return controlPoints;
    }

    @Override
    public Integer getDimention() {
        return dimention;
    }

    @Override
    abstract public Point evaluate(Double t);

    @Override
    public Vec computeTangent(Double t) {
        Bezier1D wBezier = getWeightBezier();
        Bezier pBezier = getProductBezier();
        return pBezier.computeTangent(t)
                .sub(wBezier.computeTangent(t).getX(), evaluate(t).getVec())
                .scale(1.0/wBezier.evaluate(t).getX());
    }

    @Override
    public Array<? extends RationalBezier> subdivide(Double t) {
        Array<Tuple2<Array<? extends Point>, Array<Double>>> wcps = createDividedControlPoints(t);
        return Array.of(new RationalBezierFast(wcps.get(0)._1(), wcps.get(0)._2(), getDimention()),
                new RationalBezierFast(wcps.get(1)._1(), wcps.get(1)._2(), getDimention()));
    }

    private Array<Tuple2<Array<? extends Point>, Array<Double>>> createDividedControlPoints(Double t){
        Double[] ws = getWeights().toJavaArray(Double.class);
        Vec[] cp = getControlPoints().map(Point::getVec).toJavaArray(Vec.class);
        LinkedList<Point> cp0 = new LinkedList<>();
        LinkedList<Double> ws0 = new LinkedList<>();
        LinkedList<Point> cp1 = new LinkedList<>();
        LinkedList<Double> ws1 = new LinkedList<>();
        
        int n = cp.length - 1;
        cp0.addLast(Point.of(cp[0]));
        ws0.addLast(ws[0]);
        cp1.addFirst(Point.of(cp[n]));
        ws1.addFirst(ws[n]);
        while(n > 0){
            for(int i = 0; i < n; ++i){
                Double w = (1-t) * ws[i] + t * ws[i+1];
                cp[i] = Vec.add((1-t)*ws[i]/w, cp[i], t*ws[i+1]/w, cp[i+1]);
                ws[i] = w;
            }
            cp0.addLast(Point.of(cp[0]));
            ws0.addLast(ws[0]);
            cp1.addFirst(Point.of(cp[n-1]));
            ws1.addFirst(ws[n-1]); 
            --n;
        }
        
        return Array.of(Tuple.of(Array.ofAll(cp0), Array.ofAll(ws0)), Tuple.of(Array.ofAll(cp1), Array.ofAll(ws1)));
    }
    
    @Override
    public RationalBezier elevate() {
        Array<? extends Point> elevatedCps = getProductBezier().elevate().getControlPoints();
        Array<Double> elevatedWeights = getWeightBezier().elevate().getControlPoints().map(Point1D::getX);
        return new RationalBezierFast(
                Array.ofAll(FuncUtils.zipWith(elevatedCps, elevatedWeights, (p, w) -> Point.of(p.getVec().scale(1.0/w)))),
                elevatedWeights,
                getDimention());
    }

    @Override
    public RationalBezier reduce() {
        Array<? extends Point> reducedCps = getProductBezier().reduce().getControlPoints();
        Array<Double> reducedWeights = getWeightBezier().reduce().getControlPoints().map(Point1D::getX);
        return new RationalBezierFast(
                Array.ofAll(FuncUtils.zipWith(reducedCps, reducedWeights, (p, w) -> Point.of(p.getVec().scale(1.0/w)))),
                reducedWeights,
                getDimention());
    }
    
    @Override
    public RationalBezier reverse() {
        return new RationalBezierFast(getControlPoints().reverse(), getWeights().reverse(), getDimention());
    }
}
