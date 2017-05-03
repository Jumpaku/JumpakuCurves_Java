
package org.jumpaku.curve.bspline;

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

    public static Double bSplineBasis(Integer n, Integer j, Double t, Array<Double> knots){
        if(n == 0) {
            return (knots.get(j).compareTo(t) <= 0 && knots.get(j + 1).compareTo(t) > 0) ? 1.0 : 0.0;
        }

        Double left = bSplineBasisHelper(t, knots.get(j), knots.get(j+n), knots.get(j));
        if(left.compareTo(0.0) != 0){
            left = left * bSplineBasis(n-1, j, t, knots);
        }
        Double right = bSplineBasisHelper(knots.get(j+n+1), t, knots.get(j+n+1), knots.get(j+1));
        if(right.compareTo(0.0) != 0){
            right = right * bSplineBasis(n-1, j+1, t, knots);
        }
        return left + right;
    }

    private static Double bSplineBasisHelper(Double a, Double b, Double c, Double d){
        return Double.isFinite((a-b)/(c-d)) ? (a-b)/(c-d) : 0.0;
    }

    public static BSpline create(Iterable<? extends Point> controlPoints, Iterable<Knot> knots){
        Array<Point> cps = Array.ofAll(controlPoints);
        Integer m = Stream.ofAll(knots).map(Knot::getMultiplicity).sum().intValue();
        return create(m - cps.size() - 1, cps, knots);
    }

    public static BSpline create(Integer degree, Iterable<? extends Point> controlPoints, Iterable<Knot> knots){
        Array<Point> cps = Array.ofAll(controlPoints);
        Array<Knot> ts = Array.ofAll(knots);

        if(ts.exists(Objects::isNull))
            throw new IllegalArgumentException("knots contains null");

        if(cps.exists(Objects::isNull))
            throw new IllegalArgumentException("control points contains null");

        for(int i = 0; i < ts.size()-1; ++i){
            if(ts.get(i).getValue() > ts.get(i+1).getValue())
                throw new IllegalArgumentException("knots must be in ascending order, but knot[" + i +  "] > knot[" + (i+1)+ "]");
        }

        if(!(ts.head().getMultiplicity().equals(degree+1) && ts.last().getMultiplicity().equals(degree+1))){
            throw new IllegalArgumentException("first and last knots must be degree+1 multiple to clamp");
        }

        if(cps.isEmpty())
            throw new IllegalArgumentException("control points must be not empty");

        if(degree < 0)
            throw new IllegalArgumentException("degree must be positive or 0");

        if(cps.size() != ts.map(Knot::getMultiplicity).sum().intValue() - degree - 1)
            throw new IllegalArgumentException("the number of control points or knots, or degree are wrong");

        return new BSpline(Interval.of(ts.head().getValue(), ts.last().getValue()), degree, cps, ts);
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
            throw new IllegalArgumentException("t is out of domain, t = " + t);

        double s = makeParameterLittleSmaller(getDomain(), t);

        Array<Double> ts = getKnotValues();
        Integer l = ts.lastIndexWhere(knot -> knot <= s);
        Point[] result = getControlPoints().toJavaArray(Point.class);
        Integer d = getDegree();

        for(int k = 1; k <= d; ++k){
            for(int i = l; i >= l-d+k; --i){
                Double aki = (s - ts.get(i)) / (ts.get(i+d+1-k) - ts.get(i));
                result[i] = result[i-1].divide(aki, result[i]);
            }
        }

        return result[l];
    }

    /**
     * BSpline curve S(t) : I -> R^3 is a function from I = [a, b].
     * However, De Boor's algorithm can be applied to t in [a, b).
     * To apply the algorithm to t = b, make t a little smaller.
     * @param i
     * @param t
     * @return
     */
    private static Double makeParameterLittleSmaller(Interval i, Double t){
        return 1.0e-10*i.getBegin() + (1.0-1.0e-10)*t;
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

        return BSplineDerivative.create(getDomain(), d-1, cvs, knots);
    }

    @Override
    public BSpline restrict(Double begin, Double end) {
        if(!getDomain().includes(Interval.of(begin, end))){
            throw new IllegalArgumentException("Interval i must be a subset of this domain");
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
    
    public BSpline insertKnot(Double t){
        return insertKnotMultiple(t, 1);
    }


    public static Knot initialKnot(Double t, Array<Knot> knots){
        return knots.find(knot -> Precision.equals(knot.getValue(), t, 1.0e-10))
                .getOrElse(Knot.of(t, 0));
    }

    public static Integer whereKnotExists(Double t, Array<Double> knotValues){
        return knotValues.indexWhere(knot -> knot <= t);
    }

    public static Array<Knot> insertedKnots(Knot initialKnot, Integer m, Array<Knot> knots){
        return (initialKnot.getMultiplicity().equals(0) ? knots.prepend(initialKnot).sorted() : knots)
                .map(knot -> Precision.equals(initialKnot.getValue(), knot.getValue(), 1.0e-10) ? knot.elevateMultiplicity(m) : knot);
    }

    public BSpline insertKnotMultiple(Double knotValue, Integer m){
        if(!getDomain().includes(knotValue)) {
            throw new IllegalArgumentException("inserted knot is out of domain.");
        }
        if(m < 0) {
            throw new IllegalArgumentException("iinserted knot multiplicity is too small");
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
                Stream.concat(front, middle.reverse(), back.reverse()).toArray(),
                insertedKnots(initialKnot, m, getKnots()));
    }

    public BSpline insertKnot(Integer i){
        return insertKnotMultiple(i, 1);
    }

    public BSpline insertKnotMultiple(Integer i, Integer m){
        return insertKnotMultiple(getKnotValues().get(i), m);
    }

    public Array<Bezier> toBeziers(){
        List<Bezier> beziers = List.empty();
        BSpline bSpline = this;
        for(Knot knot : getKnots().subSequence(1, getKnots().size() - 2)){
            Tuple2<BSpline, BSpline> tmp = bSpline.subdivide(knot.getValue());
            beziers = beziers.prepend(Bezier.create(tmp._1().getControlPoints()));
            bSpline = tmp._2();
        }
        return beziers.reverse().toArray();
    }
    
    public Tuple2<BSpline, BSpline> subdivide(Double t){
        int s = initialKnot(t, getKnots()).getMultiplicity();
        int d = getDegree();
        int m = d - s;
        int l = whereKnotExists(t, getKnotValues());

        BSpline inserted = insertKnotMultiple(t, m);
        BSpline.create(inserted.getControlPoints().take(l - d + m + 1), inserted.getKnots().takeUntil(knot -> knot.getValue().compareTo(t) > 0));
        return null;
    }
}
