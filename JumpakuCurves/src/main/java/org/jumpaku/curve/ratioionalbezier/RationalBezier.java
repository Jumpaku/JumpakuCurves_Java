/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Differentiable;
import org.jumpaku.curve.FuzzyCurve;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Reverseable;
import org.jumpaku.curve.bezier.Bezier;

/**
 *
 * @author Jumpaku
 */
public interface RationalBezier extends FuzzyCurve, Differentiable, Reverseable<RationalBezier>{
    
    public static RationalBezier create(Iterable<WeightedPoint> weightedControlPoints){
        return create(Interval.ZERO_ONE, weightedControlPoints);
    }
    
    public static RationalBezier create(Interval i, Iterable<WeightedPoint> weightedControlPoints){
        return new Decasteljau(i, weightedControlPoints);
    }
        
    public static RationalBezier create(WeightedPoint... weightedControlPoints){
        return create(Interval.ZERO_ONE, weightedControlPoints);
    }
    
    public static RationalBezier create(Interval i, WeightedPoint... weightedControlPoints){
        return create(i, Stream.of(weightedControlPoints));
    }

    public static RationalBezier create(Iterable<? extends Point> controlPoints, Iterable<Double> weights){
        return create(Interval.ZERO_ONE, controlPoints, weights);
    }
    
    public static RationalBezier create(Interval i, Iterable<? extends Point> controlPoints, Iterable<Double> weights){
        Array<Double> ws = Array.ofAll(weights);
        Array<Point> cps = Array.ofAll(controlPoints);

        if(ws.size() != cps.size())
            throw new IllegalArgumentException("size of weights must equal to size of controlPoints,"
                            + " but size of weights = "+ws.size()+", size of controlPoints = "+cps.size());

        if(ws.isEmpty() || cps.isEmpty())
            throw new IllegalArgumentException("weights and controlPoints mustn't be empty");
        
        return create(i, ws.zipWith(cps, WeightedPoint::new));
    }
    
    public static ConicSection.ByRepresentPoints byRepresentPoints(Double weight, Point rp0, Point rp1, Point rp2){
        return byRepresentPoints(Interval.ZERO_ONE, weight, rp0, rp1, rp2);
    }
    
    public static ConicSection.ByRepresentPoints byRepresentPoints(Interval i, Double weight, Point rp0, Point rp1, Point rp2){
        return new ConicSection.ByRepresentPoints(i, weight, rp0, rp1, rp2);
    }
    
    public static RationalBezier fromBezier(Bezier bezier){
        return create(bezier.getDomain(), bezier.getControlPoints(), Stream.fill(bezier.getDegree()+1, ()->1.0));
    }
    
    public static String toJson(RationalBezier bezier){
        return JsonRationalBezier.CONVERTER.toJson(bezier);
    }
    
    public static Option<RationalBezier> fromJson(String json){
        return JsonRationalBezier.CONVERTER.fromJson(json);
    }

    @Override Interval getDomain();
    
    @Override Point evaluate(Double t);

    @Override Derivative differentiate();

    @Override default RationalBezier restrict(Double begin, Double end){
        return restrict(Interval.of(begin, end));
    }

    @Override RationalBezier restrict(Interval i);

    @Override RationalBezier reverse();

    default Array<Point> getControlPoints(){
        return getWeightedControlPoints().map(WeightedPoint::getPoint);
    }
    
    default Array<Double> getWeights(){
        return getWeightedControlPoints().map(WeightedPoint::getWeight);
    }

    default Integer getDegree(){
        return getWeightedControlPoints().size() - 1;
    }

    Array<WeightedPoint> getWeightedControlPoints();
    
    RationalBezier elevate();
    
    RationalBezier reduce();
    
    Tuple2<RationalBezier, RationalBezier> subdivide(Double t);
}
