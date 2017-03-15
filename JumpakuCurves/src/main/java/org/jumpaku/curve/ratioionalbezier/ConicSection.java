/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.affine.FuzzyVector;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;

/**
 * @author jumpaku
 */
public interface ConicSection extends RationalBezier{
    
    public static final class ByRepresentPoints implements ConicSection{
    
        private final FuzzyPoint rp0;

        private final FuzzyPoint rp1;

        private final FuzzyPoint rp2;

        private final Double weight;

        private final Interval domain;

        public ByRepresentPoints(Interval domain, Double weight,
                FuzzyPoint representPoint0, FuzzyPoint representPoint1, FuzzyPoint representPoint2) {
            this.rp0 = representPoint0;
            this.rp1 = representPoint1;
            this.rp2 = representPoint2;
            this.weight = weight;
            this.domain = domain;
        }

        @Override public Interval getDomain() {
            return domain;
        }

        @Override public FuzzyPoint evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            FuzzyVector rv0 = rp0.toVector();
            FuzzyVector rv1 = rp1.toVector();
            FuzzyVector rv2 = rp2.toVector();

            Double wt = Decasteljau.weightBezier(t, Array.of(1.0, getWeight(), 1.0));
            Point p = Point.of(rv0.scale((1-t)*(1-2*t)/wt)
                    .add(2*(getWeight()+1)*(1-2*t)/wt, rv1)
                    .add(t*(2*t-1)/wt, rv2));
            Double r = rv0.getR()*(1-t)*(1-2*t)/wt
                    + rv1.getR()*2*(getWeight()+1)*(1-2*t)/wt
                    + rv2.getR()*t*(2*t-1)/wt;

            return FuzzyPoint.of(p, FastMath.abs(r));
        }

        @Override public Derivative differentiate() {
            return new Derivative() {
                @Override public Vector evaluate(Double t) {
                    if(!getDomain().includes(t))
                        throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

                    FuzzyVector rv0 = rp0.toVector();
                    FuzzyVector rv1 = rp1.toVector();
                    FuzzyVector rv2 = rp2.toVector();

                    return rv0.scale(4*t-3).add((getWeight()+1)*(-4*t+2), rv1).add(4*t-1, rv2);
                }

                @Override public Interval getDomain() {
                    return ByRepresentPoints.this.getDomain();
                }
            };
        }

        @Override public ByRepresentPoints restrict(Interval i) {
            return new ByRepresentPoints(i, getWeight(), rp0, rp1, rp2);
        }

        @Override public ByRepresentPoints reverse() {
            return new ByRepresentPoints(Interval.of(1-getDomain().getEnd(), 1-getDomain().getBegin()),
                    getWeight(), rp2, rp1, rp0);
        }

        @Override
        public Array<WeightedPoint> getWeightedControlPoints() {
            return createCrispControlPoints(getWeight(), getRepresentPoints());
        }

        @Override public RationalBezier elevate() {
            return toCrispRationalBezier().elevate();
        }

        @Override public RationalBezier reduce() {
            return toCrispRationalBezier().reduce();
        }

        @Override
        public Tuple2<RationalBezier, RationalBezier> subdivide(Double t) {
            return toCrispRationalBezier().subdivide(t);
        }

        @Override public Array<FuzzyPoint> getRepresentPoints() {
            return Array.of(rp0, rp1, rp2);
        }


        public Decasteljau toCrispRationalBezier(){
            return new Decasteljau(getDomain(), createCrispControlPoints(getWeight(), getRepresentPoints()));
        }

        public static Array<WeightedPoint> createCrispControlPoints(Double weight, Array<FuzzyPoint> representPoint){
            if(!Double.isFinite(1.0/weight))
                throw new IllegalArgumentException("weight must be non zero.");

            Vector[] rvs = representPoint.map(FuzzyPoint::toVector).toJavaArray(FuzzyVector.class);
            FuzzyPoint[] cps = new FuzzyPoint[]{
                FuzzyPoint.of(rvs[0]),
                FuzzyPoint.of(rvs[0].scale(-0.5/weight).add(1+1/weight, rvs[1]).add(-0.5/weight, rvs[2])),
                FuzzyPoint.of(rvs[2])
            };
            return Array.of(1.0, weight, 1.0).zipWith(Stream.of(cps), WeightedPoint::new);
        }

        @Override public Double getWeight() {
            return weight;
        }

        @Override public String toString() {
            return JsonConicSection.CONVERTER.toJson(this);
        }
    }
    
    @Override default Integer getDegree() {
        return 2;
    }
    
    /**
     * 
     * @return {evaluate(0), evaluate(0.5), evaluate(1)}
     */
    Array<FuzzyPoint> getRepresentPoints();

    Double getWeight();

}
