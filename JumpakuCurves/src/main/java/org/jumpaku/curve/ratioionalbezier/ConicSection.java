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
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;

/**
 * @author jumpaku
 */
public interface ConicSection extends RationalBezier{
    
    public static final class ByRepresentPoints implements ConicSection{
    
        private final Point rp0;

        private final Point rp1;

        private final Point rp2;

        private final Double weight;

        private final Interval domain;

        public ByRepresentPoints(Interval domain, Double weight,
                Point representPoint0, Point representPoint1, Point representPoint2) {
            this.rp0 = representPoint0;
            this.rp1 = representPoint1;
            this.rp2 = representPoint2;
            this.weight = weight;
            this.domain = domain;
        }

        @Override public Interval getDomain() {
            return domain;
        }

        @Override public Point evaluate(Double t) {
            if(!getDomain().includes(t))
                throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

            Vector rv0 = rp0.toVector();
            Vector rv1 = rp1.toVector();
            Vector rv2 = rp2.toVector();

            Double wt = Decasteljau.weightBezier(t, Array.of(1.0, getWeight(), 1.0));
            Point.Crisp p = new Point.Crisp(rv0.scale((1-t)*(1-2*t)/wt)
                    .add(2*(getWeight()+1)*(1-2*t)/wt, rv1)
                    .add(t*(2*t-1)/wt, rv2)
                    .toCrisp());
            Double r = FastMath.abs(rv0.getR()*(1-t)*(1-2*t)/wt)
                    + FastMath.abs(rv1.getR()*2*(getWeight()+1)*(1-2*t)/wt)
                    + FastMath.abs(rv2.getR()*t*(2*t-1)/wt);

            return Point.fuzzy(p, r);
        }

        @Override public Derivative differentiate() {
            return new Derivative() {
                @Override public Vector.Crisp evaluate(Double t) {
                    if(!getDomain().includes(t))
                        throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

                    Vector rv0 = rp0.toVector();
                    Vector rv1 = rp1.toVector();
                    Vector rv2 = rp2.toVector();

                    return rv0.scale(4*t-3).add((getWeight()+1)*(-4*t+2), rv1).add(4*t-1, rv2).toCrisp();
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

        @Override public Array<Point> getRepresentPoints() {
            return Array.of(rp0, rp1, rp2);
        }


        public Decasteljau toCrispRationalBezier(){
            return new Decasteljau(getDomain(), createCrispControlPoints(getWeight(), getRepresentPoints()));
        }

        public static Array<WeightedPoint> createCrispControlPoints(Double weight, Array<Point> representPoint){
            if(!Double.isFinite(1.0/weight))
                throw new IllegalArgumentException("weight must be non zero.");

            Vector.Crisp[] rvs = representPoint.map(p -> p.toCrisp().toVector()).toJavaArray(Vector.Crisp.class);
            Point[] cps = new Point[]{
                new Point.Crisp(rvs[0]),
                new Point.Crisp(rvs[0].scale(-0.5/weight).add(1+1/weight, rvs[1]).add(-0.5/weight, rvs[2]).toCrisp()),
                new Point.Crisp(rvs[2])
            };
            return Stream.of(cps).zipWith(Stream.of(1.0, weight, 1.0), WeightedPoint::new).toArray();
        }

        @Override public Double getWeight() {
            return weight;
        }

        @Override public String toString() {
            return JsonConicSection.CONVERTER.toJson(this);
        }

        @Override public ConicSection complement() {
            return new ByRepresentPoints(domain, weight*-1, rp0, rp1, rp2);
        }
    }
    
    @Override default Integer getDegree() {
        return 2;
    }
    
    /**
     * 
     * @return {evaluate(0), evaluate(0.5), evaluate(1)}
     */
    Array<Point> getRepresentPoints();

    Double getWeight();
    
    ConicSection complement();

}
