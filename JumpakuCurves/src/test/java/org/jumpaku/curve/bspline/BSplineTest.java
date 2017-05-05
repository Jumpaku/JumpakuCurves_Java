package org.jumpaku.curve.bspline;

import static org.jumpaku.curve.bspline.BSplineMatcher.bSplineOf;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import static org.junit.Assert.*;
import static org.jumpaku.affine.PointMatcher.pointOf;
import static org.hamcrest.core.Is.is;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Knot;
import org.jumpaku.curve.bezier.Bezier;
import org.junit.Test;


/**
 * Created by jumpaku on 2017/05/04.
 */
public class BSplineTest {

    @Test
    public void test_ToJson() {
        System.out.println("ToJson");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        BSpline actual = BSpline.fromJson(BSpline.toJson(bSpline)).get();
        assertThat(actual, is(bSplineOf(bSpline)));

    }

    @Test
    public void test_FromJson() {
        System.out.println("FromJson");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        BSpline actual = BSpline.fromJson(
                "{controlPoints:[{x:-1.0,y:0.0,z:0.0,r:0.0},{x:-1.0,y:1.0,z:0.0,r:1.0},{x:0.0,y:1.0,z:0.0,r:2.0},{x:0.0,y:0.0,z:0.0,r:1.0},{x:1.0,y:0.0,z:0.0,r:0.0}]," +
                        "knots:[{value:0.0,multiplicity:4},{value:1.0,multiplicity:1},{value:2.0,multiplicity:4}]}").get();
        assertThat(actual, is(bSplineOf(bSpline)));
    }

    @Test
    public void test_BSplineBasis() {
        System.out.println("BSplineBasis");
        Array<Double> knots = Knot.clampedUniformKnots(2, 4).flatMap(Knot::toArray);

        assertEquals(1.0,   BSpline.bSplineBasis(0.0, 2, 0, knots), 1.0e-10);
        assertEquals(0.0,   BSpline.bSplineBasis(0.0, 2, 1, knots), 1.0e-10);
        assertEquals(0.0,   BSpline.bSplineBasis(0.0, 2, 2, knots), 1.0e-10);
        assertEquals(0.0,   BSpline.bSplineBasis(0.0, 2, 3, knots), 1.0e-10);

        assertEquals(0.25,  BSpline.bSplineBasis(0.5, 2, 0, knots), 1.0e-10);
        assertEquals(5/8.0, BSpline.bSplineBasis(0.5, 2, 1, knots), 1.0e-10);
        assertEquals(0.125, BSpline.bSplineBasis(0.5, 2, 2, knots), 1.0e-10);
        assertEquals(0.0,   BSpline.bSplineBasis(0.5, 2, 3, knots), 1.0e-10);

        assertEquals(0.0,   BSpline.bSplineBasis(1.0, 2, 0, knots), 1.0e-10);
        assertEquals(0.5,   BSpline.bSplineBasis(1.0, 2, 1, knots), 1.0e-10);
        assertEquals(0.5,   BSpline.bSplineBasis(1.0, 2, 2, knots), 1.0e-10);
        assertEquals(0.0,   BSpline.bSplineBasis(1.0, 2, 3, knots), 1.0e-10);

        assertEquals(0.0,   BSpline.bSplineBasis(1.5, 2, 0, knots), 1.0e-10);
        assertEquals(0.125, BSpline.bSplineBasis(1.5, 2, 1, knots), 1.0e-10);
        assertEquals(5/8.0, BSpline.bSplineBasis(1.5, 2, 2, knots), 1.0e-10);
        assertEquals(0.25,  BSpline.bSplineBasis(1.5, 2, 3, knots), 1.0e-10);

        assertEquals(0.0,   BSpline.bSplineBasis(2.0, 2, 0, knots), 1.0e-10);
        assertEquals(0.0,   BSpline.bSplineBasis(2.0, 2, 1, knots), 1.0e-10);
        assertEquals(0.0,   BSpline.bSplineBasis(2.0, 2, 2, knots), 1.0e-10);
        assertEquals(1.0,   BSpline.bSplineBasis(2.0, 2, 3, knots), 1.0e-10);
    }

    @Test
    public void test_Create() {
        System.out.println("Create");
        BSpline bSpline = BSpline.create(
                3,
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(bSpline.getControlPoints().get(0), is(pointOf(Point.fuzzy(-1.0, 0.0, 0.0))));
        assertThat(bSpline.getControlPoints().get(1), is(pointOf(Point.fuzzy(-1.0, 1.0, 1.0))));
        assertThat(bSpline.getControlPoints().get(2), is(pointOf(Point.fuzzy( 0.0, 1.0, 2.0))));
        assertThat(bSpline.getControlPoints().get(3), is(pointOf(Point.fuzzy( 0.0, 0.0, 1.0))));
        assertThat(bSpline.getControlPoints().get(4), is(pointOf(Point.fuzzy( 1.0, 0.0, 0.0))));
        assertEquals(5, bSpline.getControlPoints().size());
        assertEquals(0.0, bSpline.getKnotValues().get(0), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(1), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(2), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(3), 1.0e-10);
        assertEquals(1.0, bSpline.getKnotValues().get(4), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(5), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(6), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(7), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(8), 1.0e-10);
        assertEquals(9, bSpline.getKnotValues().size());
    }

    @Test
    public void test_Create1() {
        System.out.println("Create1");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(bSpline.getControlPoints().get(0), is(pointOf(Point.fuzzy(-1.0, 0.0, 0.0))));
        assertThat(bSpline.getControlPoints().get(1), is(pointOf(Point.fuzzy(-1.0, 1.0, 1.0))));
        assertThat(bSpline.getControlPoints().get(2), is(pointOf(Point.fuzzy( 0.0, 1.0, 2.0))));
        assertThat(bSpline.getControlPoints().get(3), is(pointOf(Point.fuzzy( 0.0, 0.0, 1.0))));
        assertThat(bSpline.getControlPoints().get(4), is(pointOf(Point.fuzzy( 1.0, 0.0, 0.0))));
        assertEquals(5, bSpline.getControlPoints().size());
        assertEquals(0.0, bSpline.getKnotValues().get(0), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(1), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(2), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(3), 1.0e-10);
        assertEquals(1.0, bSpline.getKnotValues().get(4), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(5), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(6), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(7), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(8), 1.0e-10);
        assertEquals(9, bSpline.getKnotValues().size());
    }

    @Test
    public void test_GetDomain() {
        System.out.println("GetDomain");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertEquals(0.0, bSpline.getDomain().getBegin(), 1.0e-10);
        assertEquals(2.0, bSpline.getDomain().getEnd(), 1.0e-10);
    }

    @Test
    public void test_Evaluate() {
        System.out.println("Evaluate");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(bSpline.evaluate(0.0), is(pointOf(-1.0, 0.0, 0.0, 0.0)));
        assertThat(bSpline.evaluate(0.5), is(pointOf(-23/32.0, 27/32.0, 0.0, 9/8.0)));
        assertThat(bSpline.evaluate(1.0), is(pointOf(-1/4.0, 3/4.0, 0.0, 1.5)));
        assertThat(bSpline.evaluate(1.5), is(pointOf(3/32.0, 9/32.0, 0.0, 9/8.0)));
        assertThat(bSpline.evaluate(2.0), is(pointOf( 1.0, 0.0, 0.0, 0.0)));
    }

    @Test
    public void test_Differentiate() {
        System.out.println("Differentiate");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .differentiate()
                .toBSpline();
        BSpline expected = BSpline.create(
                Stream.of(Point.crisp(0.0, 3.0), Point.crisp(1.5, 0.0), Point.crisp(0.0, -1.5), Point.crisp(3.0, 0.0)),
                Knot.clampedUniformKnots(2, 4));

        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_Restrict() {
        System.out.println("Restrict");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .restrict(Interval.closed(1.0, 1.5));
        BSpline expected = BSpline.create(
                Stream.of(Point.fuzzy(-0.25, 0.75, 1.5), Point.fuzzy(-0.125, 5/8.0, 1.5), Point.fuzzy(-1/16.0, 7/16.0, 11/8.0), Point.fuzzy(3/32.0, 9/32.0, 9/8.0)),
                Knot.clampedUniformKnots(1.0, 1.5, 3, 4));
        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_Restrict1() {
        System.out.println("Restrict1");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .restrict(1.0, 1.5);
        BSpline expected = BSpline.create(
                Stream.of(Point.fuzzy(-0.25, 0.75, 1.5), Point.fuzzy(-0.125, 5/8.0, 1.5), Point.fuzzy(-1/16.0, 7/16.0, 11/8.0), Point.fuzzy(3/32.0, 9/32.0, 9/8.0)),
                Knot.clampedUniformKnots(1.0, 1.5, 3, 4));
        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_Reverse() {
        System.out.println("Reverse");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .reverse();
        BSpline expected = BSpline.create(
                Stream.of(Point.fuzzy(1.0, 0.0, 0.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(-1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_ToString() {
        System.out.println("ToString");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        BSpline actual = BSpline.fromJson(bSpline.toString()).get();
        assertThat(actual, is(bSplineOf(bSpline)));
    }

    @Test
    public void test_GetControlPoints() {
        System.out.println("GetControlPoints");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(bSpline.getControlPoints().get(0), is(pointOf(Point.fuzzy(-1.0, 0.0, 0.0))));
        assertThat(bSpline.getControlPoints().get(1), is(pointOf(Point.fuzzy(-1.0, 1.0, 1.0))));
        assertThat(bSpline.getControlPoints().get(2), is(pointOf(Point.fuzzy( 0.0, 1.0, 2.0))));
        assertThat(bSpline.getControlPoints().get(3), is(pointOf(Point.fuzzy( 0.0, 0.0, 1.0))));
        assertThat(bSpline.getControlPoints().get(4), is(pointOf(Point.fuzzy( 1.0, 0.0, 0.0))));
        assertEquals(5, bSpline.getControlPoints().size());
    }

    @Test
    public void test_GetKnots() {
        System.out.println("GetKnots");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertEquals(0.0, bSpline.getKnots().get(0).getValue(), 1.0e-10);
        assertEquals(4,   bSpline.getKnots().get(0).getMultiplicity().intValue());
        assertEquals(1.0, bSpline.getKnots().get(1).getValue(), 1.0e-10);
        assertEquals(1,   bSpline.getKnots().get(1).getMultiplicity().intValue());
        assertEquals(2.0, bSpline.getKnots().get(2).getValue(), 1.0e-10);
        assertEquals(4,   bSpline.getKnots().get(2).getMultiplicity().intValue());
        assertEquals(3, bSpline.getKnots().size());
    }

    @Test
    public void test_GetKnotValues() {
        System.out.println("GetKnotValues");
        BSpline bSpline = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertEquals(0.0, bSpline.getKnotValues().get(0), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(1), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(2), 1.0e-10);
        assertEquals(0.0, bSpline.getKnotValues().get(3), 1.0e-10);
        assertEquals(1.0, bSpline.getKnotValues().get(4), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(5), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(6), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(7), 1.0e-10);
        assertEquals(2.0, bSpline.getKnotValues().get(8), 1.0e-10);
        assertEquals(9, bSpline.getKnotValues().size());
    }

    @Test
    public void test_GetDegree() {
        System.out.println("GetDegree");
        BSpline bSpline = BSpline.create(Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertEquals(3, bSpline.getDegree().intValue());
    }

    @Test
    public void test_InsertKnot() {
        System.out.println("InsertKnot");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnot(0.5);

        BSpline expected = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 0.5, 0.5), Point.fuzzy(-0.75, 1.0, 1.25), Point.fuzzy(0.0, 0.75, 1.75), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(0.5, 1), Knot.of(1.0, 1), Knot.of(2.0, 4)));

        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_InsertKnotMultiple() {
        System.out.println("InsertKnotMultiple");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnotMultiple(0.5, 3);

        BSpline expected = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 0.5, 0.5),
                        Point.fuzzy(-7/8.0, 0.75, 7/8.0), Point.fuzzy(-23/32.0, 27/32.0, 9/8.0),
                        Point.fuzzy(-9/16.0, 15/16.0, 11/8.0), Point.fuzzy(0.0, 0.75, 1.75),
                        Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(0.5, 3), Knot.of(1.0, 1), Knot.of(2.0, 4)));

        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_InsertKnot1() {
        System.out.println("InsertKnot1");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnot(4);

        BSpline expected = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(-0.5, 1.0, 1.5), Point.fuzzy(0.0, 0.5, 1.5), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(1.0, 2), Knot.of(2.0, 4)));

        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_InsertKnotMultiple1() {
        System.out.println("InsertKnotMultiple1");
        BSpline actual = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnotMultiple(4, 2);

        BSpline expected = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(-0.5, 1.0, 1.5), Point.fuzzy(-0.25, 0.75, 1.5), Point.fuzzy(0.0, 0.5, 1.5), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(1.0, 3), Knot.of(2.0, 4)));

        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void test_ToBeziers() {
        System.out.println("ToBeziers");
        Array<Bezier> beziers = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .toBeziers();
        assertThat(beziers.get(0), is(bezierOf(Bezier.create(
                Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(-0.5, 1.0, 1.5), Point.fuzzy(-0.25, 0.75, 1.5)))));
        assertThat(beziers.get(1), is(bezierOf(Bezier.create(
                Point.fuzzy(-0.25, 0.75, 1.5), Point.fuzzy(0.0, 0.5, 1.5), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)))));
        assertEquals(2, beziers.size());
    }

    @Test
    public void test_Subdivide() {
        System.out.println("Subdivide");
        Tuple2<BSpline, BSpline> beziers = BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .subdivide(1.0);
        assertThat(beziers._1(), is(bSplineOf(BSpline.create(
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(-0.5, 1.0, 1.5), Point.fuzzy(-0.25, 0.75, 1.5)),
                Knot.clampedUniformKnots(3, 4)))));
        assertThat(beziers._2(), is(bSplineOf(BSpline.create(
                Stream.of(Point.fuzzy(-0.25, 0.75, 1.5), Point.fuzzy(0.0, 0.5, 1.5), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(1.0, 2.0, 3, 4)))));
    }

}