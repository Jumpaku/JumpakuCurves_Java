/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.affine.WeightedPoint;
import org.jumpaku.curve.*;

/**
 * @author jumpaku
 */

public final class QuadraticByRepresentPoints implements FuzzyCurve, Differentiable, Reversible<QuadraticByRepresentPoints> {

    public static Array<WeightedPoint> createCrispControlPoints(Double weight, Iterable<Point> representPoint){
        if(!Double.isFinite(1.0/weight))
            throw new IllegalArgumentException("weight must be non zero.");

        Vector.Crisp[] rvs = Stream.ofAll(representPoint)
                .map(p -> p.toCrisp().toVector()).toJavaArray(Vector.Crisp.class);
        Point[] cps = new Point[]{
                new Point.Crisp(rvs[0]),
                new Point.Crisp(rvs[0].scale(-0.5/weight).add(1+1/weight, rvs[1]).add(-0.5/weight, rvs[2]).toCrisp()),
                new Point.Crisp(rvs[2])
        };
        return Stream.of(cps).zipWith(Stream.of(1.0, weight, 1.0), WeightedPoint::new).toArray();
    }

    private final RationalBezier crispRationalBezier;

    private final Array<Point> representPoints;

    public QuadraticByRepresentPoints(Interval domain, Double weight,
                                      Point representPoint0, Point representPoint1, Point representPoint2) {
        this.crispRationalBezier = RationalBezier.create(
                domain,
                createCrispControlPoints(
                    weight,
                    Stream.of(representPoint0, representPoint1, representPoint2)));
        this.representPoints = Array.of(representPoint0, representPoint1, representPoint2);
    }

    @Override
    public Interval getDomain() {
        return crispRationalBezier.getDomain();
    }

    @Override
    public Point evaluate(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

        Point.Crisp p = crispRationalBezier.evaluate(t).toCrisp();

        Double rs[] = getRepresentPoints().map(Point::getR).toJavaArray(Double.class);

        Double wt = RationalBezier.weightBezier(t, Array.of(1.0, getWeight(), 1.0));
        Double r = FastMath.abs(rs[0]*(1-t)*(1-2*t)/wt)
                + FastMath.abs(rs[1]*2*(getWeight()+1)*t*(1-t)/wt)
                + FastMath.abs(rs[2]*t*(2*t-1)/wt);

        return Point.fuzzy(p, r);
    }

    @Override
    public Derivative differentiate() {
        return crispRationalBezier.differentiate();
    }

    @Override public QuadraticByRepresentPoints restrict(Interval i) {

        return new QuadraticByRepresentPoints(i, getWeight(),
                getRepresentPoints().get(0),
                getRepresentPoints().get(1),
                getRepresentPoints().get(2));
    }

    @Override
    public QuadraticByRepresentPoints restrict(Double begin, Double end) {
        return restrict(Interval.of(begin, end));
    }

    @Override
    public QuadraticByRepresentPoints reverse() {
        return new QuadraticByRepresentPoints(Interval.of(1-getDomain().getEnd(), 1-getDomain().getBegin()),
                getWeight(),
                getRepresentPoints().get(2),
                getRepresentPoints().get(1),
                getRepresentPoints().get(0));
    }

    public Array<Point> getRepresentPoints() {
        return representPoints;
    }


    public RationalBezier toCrispRationalBezier(){
        return crispRationalBezier;
    }

    public Double getWeight() {
        return crispRationalBezier.getWeights().get(1);
    }

    @Override
    public String toString() {
        return JsonQuadraticByRepresentPoints.CONVERTER.toJson(this);
    }

    public QuadraticByRepresentPoints complement() {
        return new QuadraticByRepresentPoints(
                getDomain(), getWeight()*-1,
                getRepresentPoints().get(0),
                getRepresentPoints().get(1),
                getRepresentPoints().get(2));
    }

    public Integer getDegree() {
        return 2;
    }


}
