
package org.jumpaku.curve.bspline;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.List;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.apache.commons.math3.util.Precision;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.*;
import org.jumpaku.curve.bezier.Bezier;

import java.util.Arrays;
import java.util.Objects;


/**
 * Represents clamped B-spline curve.
 * <p>B-spline curve s : I = [a, b] -> R^3 : is a function.
 * If d is degree, cp[i] (i = 0, ..., n-1) is control points, and t[i] (i = 0, ..., m-1) is knot,
 * then t[0] = ... = t[d] , t[m-d] = ... = t[m-1], s(a) = cp[0], and s(b) = cp[n-1].
 * </p>
 * @author Jumpaku
 */
public final class BSpline implements FuzzyCurve, Differentiable, Reversible<Curve>, Restrictable<Curve> {

    public static String toJson(BSpline bSpline){
        return JsonBSpline.CONVERTER.toJson(bSpline);
    }

    public static Option<BSpline> fromJson(String json){
        return JsonBSpline.CONVERTER.fromJson(json);
    }

    public static Double bSplineBasis(Double t, Integer degree, Integer i, Array<Double> knots){
        if(!Interval.closed(knots.get(degree), knots.get(knots.size()-degree-1)).includes(t)){
            throw new IllegalArgumentException("out of domain, t must be in [knots[degree], knots[knots.size() - degree - 1]]");
        }
        if(Precision.equals(t, knots.get(degree), 1.0e-10)){
            return i == 0 ? 1.0 : 0.0;
        }
        if(Precision.equals(t, knots.get(knots.size() - degree- 1 ), 1.0e-10)){
            return (i == knots.size() - degree - 2) ? 1.0 : 0.0;
        }

        double[] ns = new double[degree+1];
        Arrays.fill(ns, 0.0);
        int l = knots.lastIndexWhere(knot -> knot <= t);
        if(i <= l && l <= i + degree) {
            ns[l - i] = 1.0;
        }

        for(int j = 1; j < ns.length; ++j){
            for(int k = 0; k < ns.length - j; ++k){
                double left = basisHelper(t, knots.get(k+i), knots.get(k+i+j), knots.get(k+i));
                double right = basisHelper(knots.get(k+1+i+j), t, knots.get(k+1+i+j), knots.get(k+1+i));
                ns[k] = left*ns[k]+right*ns[k+1];
            }
        }

        return ns[0];
    }

    private static Double basisHelper(Double a, Double b, Double c, Double d){
        return Double.isFinite((a-b)/(c-d)) ? (a-b)/(c-d) : 0.0;
    }

    public static BSpline create(Iterable<? extends Point> controlPoints, Iterable<Knot> knots){
        Array<Point> cps = Array.ofAll(controlPoints);
        Integer m = Stream.ofAll(knots).map(Knot::getMultiplicity).sum().intValue();
        return create(m - cps.size() - 1, cps, knots);
    }

    public static BSpline create(Integer degree, Iterable<? extends Point> controlPoints, Iterable<Knot> knots){
        Array<Point> cps = Array.ofAll(controlPoints);
        Array<Knot> ks = Array.ofAll(knots);

        if(ks.exists(Objects::isNull))
            throw new IllegalArgumentException("knots contains contains null");

        if(cps.exists(Objects::isNull))
            throw new IllegalArgumentException("control points contains null");

        if(degree < 0)
            throw new IllegalArgumentException("degree was negative");

        if(ks.zip(ks.tail()).exists(t->t._1().compareTo(t._2()) > 0))
            throw new IllegalArgumentException("knots must be in ascending order");

        if(!(ks.head().getMultiplicity().equals(degree+1) && ks.last().getMultiplicity().equals(degree+1)))
            throw new IllegalArgumentException("first and last knots must be degree+1 multiple to clamp");

        if(cps.size() <= degree)
            throw new IllegalArgumentException("controlPoints.size() is too small");

        if(cps.size() != ks.map(Knot::getMultiplicity).sum().intValue() - degree - 1)
            throw new IllegalArgumentException("n = m - degree - 1 must be satisfied, where n = controlPoints.size(), m = knots.size()");

        return new BSpline(Interval.closed(ks.head().getValue(), ks.last().getValue()), degree, cps, ks);
    }

    private final Interval domain;

    private final Integer degree;

    private final Array<Point> controlPoints;

    private final Array<Knot> knots;

    public BSpline(Interval domain, Integer degree, Array<Point> controlPoints, Array<Knot> knots) {
        this.domain = domain;
        this.degree = degree;
        this.controlPoints = controlPoints;
        this.knots = knots;
    }

    @Override
    public Interval getDomain(){
        return domain;
    }
    
    @Override
    public Point evaluate(Double t){
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t is out closed domain, t = " + t);

        if(Precision.equals(t, getDomain().getBegin(), 1.0e-10)){
            return getControlPoints().head();
        }
        if(Precision.equals(t, getDomain().getEnd(), 1.0e-10)){
            return getControlPoints().last();
        }

        Array<Double> ts = getKnotValues();
        Integer l = ts.lastIndexWhere(knot -> knot <= t);
        Point[] result = getControlPoints().toJavaArray(Point.class);
        Integer d = getDegree();

        for(int k = 1; k <= d; ++k){
            for(int i = l; i >= l-d+k; --i){
                Double aki = (t - ts.get(i)) / (ts.get(i+d+1-k) - ts.get(i));
                result[i] = result[i-1].divide(aki, result[i]);
            }
        }

        return result[l];
    }

    @Override
    public BSplineDerivative differentiate(){
        int d = getDegree();
        Array<Knot> knots = getKnots().zipWithIndex(
                (knot, i) -> (i == 0  || i == getKnots().size()-1) ? knot.reduceMultiplicity() : knot);
        Array<Double> ts = getKnotValues();
        Array<Vector.Crisp> cvs = getControlPoints()
                .zipWith(getControlPoints().tail(), (a, b) -> b.toCrisp().diff(a.toCrisp()))
                .zipWithIndex((v, i) -> v.scale(d/(ts.get(d+i+1) - ts.get(i+1))));

        return BSplineDerivative.create(d-1, cvs, knots);
    }

    @Override
    public BSpline restrict(Double begin, Double end) {
        if(!getDomain().includes(Interval.closed(begin, end))){
            throw new IllegalArgumentException("Interval i must be a subset closed this domain");
        }

        return subdivide(end)._1().subdivide(begin)._2();
    }

    @Override
    public BSpline restrict(Interval i){
        return restrict(i.getBegin(), i.getEnd());
    }

    @Override
    public BSpline reverse(){
        Array<Knot> ks = getKnots()
                .reverse()
                .map(k -> Knot.of(getKnots().last().getValue() - k.getValue() + getKnots().head().getValue(), k.getMultiplicity()));

        return new BSpline(getDomain(), getDegree(), getControlPoints().reverse(), ks);
    }

    @Override
    public String toString(){
        return BSpline.toJson(this);
    }

    public Array<Point> getControlPoints(){
        return controlPoints;
    }

    public Array<Knot> getKnots(){
        return knots;
    }

    public Array<Double> getKnotValues(){
        return knots.flatMap(Knot::toArray);
    }
    
    public Integer getDegree(){
        return degree;
    }

    private static Knot initialKnot(Double t, Array<Knot> knots){
        return knots.find(knot -> Precision.equals(knot.getValue(), t, 1.0e-10))
                .getOrElse(Knot.of(t, 0));
    }

    private static Integer whereKnotExists(Double t, Array<Double> knotValues){
        return knotValues.lastIndexWhere(knot -> knot <= t);
    }

    private static Array<Knot> createInsertedKnots(Knot initialKnot, Integer m, Array<Knot> knots){
        return (initialKnot.getMultiplicity().equals(0) ? knots.prepend(initialKnot).sorted() : knots)
                .map(knot -> Precision.equals(initialKnot.getValue(), knot.getValue(), 1.0e-10) ? knot.elevateMultiplicity(m) : knot);
    }

    public BSpline insertKnotMultiple(Double knotValue, Integer m){
        if(!getDomain().includes(knotValue)) {
            throw new IllegalArgumentException("inserted knot is out closed domain.");
        }
        if(m < 0) {
            throw new IllegalArgumentException("inserted knot multiplicity is too small");
        }

        final Knot initialKnot = initialKnot(knotValue, getKnots());
        final int s = initialKnot.getMultiplicity();
        final int d = getDegree();
        if (m > d - s){
            throw new IllegalArgumentException("inserted knot multiplicity is too large.");
        }

        final double t = initialKnot.getValue();
        final Array<Double> ts = getKnotValues();
        final int l = whereKnotExists(t, ts);

        List<Point> front = getControlPoints().take(l - d).toList().reverse();
        List<Point> back = getControlPoints().drop(l - s + 1).toList();
        Array<Point> middle = getControlPoints().subSequence(l - d, l - s + 1);
        for(int i = 1; i <= m; ++i){
            int r = i;
            front = front.prepend(middle.head());
            back = back.prepend(middle.last());
            middle = Stream.rangeClosed(l-d+i, l - initialKnot.getMultiplicity())
                    .map(j -> (initialKnot.getValue() - ts.get(j))/(ts.get(j+d-r+1) - ts.get(j)))
                    .zipWith(middle.zip(middle.tail()),
                            (a, pp)->pp._1().divide(a, pp._2()))
                    .toArray();
        }


        return create(
                getDegree(),
                Stream.concat(front.reverse(), middle, back).toArray(),
                createInsertedKnots(initialKnot, m, getKnots()));
    }

    public BSpline insertKnot(Double t){
        return insertKnotMultiple(t, 1);
    }

    public BSpline insertKnotMultiple(Integer i, Integer m){
        return insertKnotMultiple(getKnotValues().get(i), m);
    }

    public BSpline insertKnot(Integer i){
        return insertKnotMultiple(i, 1);
    }

    public Array<Bezier> toBeziers(){
        return getKnots()
                .subSequence(1, getKnots().size() - 1)
                .foldLeft(List.of(this), (bSplines, knot)->{
                    Tuple2<BSpline, BSpline> tmp = bSplines.head().subdivide(knot.getValue());
                    return bSplines.tail().prepend(tmp._1()).prepend(tmp._2());
                })
                .map(bSpline -> Bezier.create(bSpline.getControlPoints()))
                .reverse()
                .toArray();
    }
    
    public Tuple2<BSpline, BSpline> subdivide(Double t){
        int s = initialKnot(t, getKnots()).getMultiplicity();
        int d = getDegree();
        int m = d - s;
        int l = whereKnotExists(t, getKnotValues());

        BSpline inserted = insertKnotMultiple(t, m);
        BSpline first = BSpline.create(
                inserted.getControlPoints().take(l - d + m + 1),
                inserted.getKnots().takeWhile(knot -> knot.getValue().compareTo(t) < 0).append(Knot.of(t, d+1)));
        BSpline second = BSpline.create(
                inserted.getControlPoints().drop(l - d + m),
                inserted.getKnots().dropUntil(knot -> knot.getValue().compareTo(t) > 0).prepend(Knot.of(t, d+1)));

        return Tuple.of(first, second);
    }
}
