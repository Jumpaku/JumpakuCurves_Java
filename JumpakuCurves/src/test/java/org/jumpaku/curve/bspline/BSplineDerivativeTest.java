package org.jumpaku.curve.bspline;

import static org.jumpaku.curve.bspline.BSplineMatcher.bSplineOf;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import static org.jumpaku.affine.VectorMatcher.vectorOf;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.Knot;
import org.jumpaku.curve.bezier.BezierDerivative;
import org.junit.Test;

/**
 * Created by jumpaku on 2017/05/05.
 */
public class BSplineDerivativeTest {

    @Test
    public void test_ToJson() {
        System.out.println("ToJson");
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        BSplineDerivative actual = BSplineDerivative.fromJson(BSplineDerivative.toJson(bSpline)).get();
        assertThat(actual.toBSpline(), is(bSplineOf(bSpline.toBSpline())));

    }

    @Test
    public void test_FromJson() {
        System.out.println("FromJson");
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        BSplineDerivative actual = BSplineDerivative.fromJson(
                "{controlVectors:[{x:-1.0,y:0.0,z:0.0,r:0.0},{x:-1.0,y:1.0,z:1.0,r:0.0},{x:0.0,y:1.0,z:2.0,r:0.0},{x:0.0,y:0.0,z:1.0,r:0.0},{x:1.0,y:0.0,z:0.0,r:0.0}]," +
                        "knots:[{value:0.0,multiplicity:4},{value:1.0,multiplicity:1},{value:2.0,multiplicity:4}]}").get();
        assertThat(actual.toBSpline(), is(bSplineOf(bSpline.toBSpline())));
    }

    @Test
    public void test_Create() {
        System.out.println("Create");
        BSplineDerivative derivative = BSplineDerivative.create(BSpline.create(
                3,
                Stream.of(Point.fuzzy(-1.0, 0.0, 0.0), Point.fuzzy(-1.0, 1.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5)));
        
        BSpline bSpline = BSpline.create(
                3,
                Stream.of(Point.crisp(-1.0, 0.0), Point.crisp(-1.0, 1.0), Point.crisp(0.0, 1.0), Point.crisp(0.0, 0.0), Point.crisp(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        
        assertThat(derivative.toBSpline(), is(bSplineOf(bSpline)));
    }

    @Test
    public void test_Create1() {
        System.out.println("Create1");
        BSplineDerivative derivative = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1.0, 1.0), Vector.crisp(0.0, 1.0), Vector.crisp(0.0, 0.0), Vector.crisp(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        BSpline bSpline = BSpline.create(
                3,
                Stream.of(Point.crisp(-1.0, 0.0), Point.crisp(-1.0, 1.0), Point.crisp(0.0, 1.0), Point.crisp(0.0, 0.0), Point.crisp(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(derivative.toBSpline(), is(bSplineOf(bSpline)));
    }

    @Test
    public void test_Restrict() {
        System.out.println("Restrict");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .restrict(Interval.closed(1.0, 1.5));
        BSplineDerivative expected = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-0.25, 0.75, 1.5), Vector.crisp(-0.125, 5/8.0, 1.5), Vector.crisp(-1/16.0, 7/16.0, 11/8.0), Vector.crisp(3/32.0, 9/32.0, 9/8.0)),
                Knot.clampedUniformKnots(1.0, 1.5, 3, 4));
        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_Restrict1() {
        System.out.println("Restrict1");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .restrict(1.0, 1.5);
        BSplineDerivative expected = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-0.25, 0.75, 1.5), Vector.crisp(-0.125, 5/8.0, 1.5), Vector.crisp(-1/16.0, 7/16.0, 11/8.0), Vector.crisp(3/32.0, 9/32.0, 9/8.0)),
                Knot.clampedUniformKnots(1.0, 1.5, 3, 4));
        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_Evaluate() {
        System.out.println("Evaluate");
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1.0, 1.0), Vector.crisp(0.0, 1.0), Vector.crisp(0.0, 0.0), Vector.crisp(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(bSpline.evaluate(0.0), is(vectorOf(-1.0, 0.0, 0.0, 0.0)));
        assertThat(bSpline.evaluate(0.5), is(vectorOf(-23/32.0, 27/32.0, 0.0, 0.0)));
        assertThat(bSpline.evaluate(1.0), is(vectorOf(-1/4.0, 3/4.0, 0.0, 0.0)));
        assertThat(bSpline.evaluate(1.5), is(vectorOf(3/32.0, 9/32.0, 0.0, 0.0)));
        assertThat(bSpline.evaluate(2.0), is(vectorOf( 1.0, 0.0, 0.0, 0.0)));
    }

    @Test
    public void test_GetDomain() {
        System.out.println("GetDomain");
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertEquals(0.0, bSpline.getDomain().getBegin(), 1.0e-10);
        assertEquals(2.0, bSpline.getDomain().getEnd(), 1.0e-10);
    }

    @Test
    public void test_Reverse() {
        System.out.println("Reverse");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .reverse();
        BSplineDerivative expected = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(1.0, 0.0, 0.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(-1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_Differentiate() {
        System.out.println("Differentiate");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1.0, 1.0), Vector.crisp(0.0, 1.0), Vector.crisp(0.0, 0.0), Vector.crisp(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .differentiate();
        BSplineDerivative expected = BSplineDerivative.create(2,
                Stream.of(Vector.crisp(0.0, 3.0), Vector.crisp(1.5, 0.0), Vector.crisp(0.0, -1.5), Vector.crisp(3.0, 0.0)),
                Knot.clampedUniformKnots(2, 4));

        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_GetControlVectors() {
        System.out.println("GetControlVectors");
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(bSpline.getControlVectors().get(0), is(vectorOf(Vector.crisp(-1.0, 0.0, 0.0))));
        assertThat(bSpline.getControlVectors().get(1), is(vectorOf(Vector.crisp(-1.0, 1.0, 1.0))));
        assertThat(bSpline.getControlVectors().get(2), is(vectorOf(Vector.crisp( 0.0, 1.0, 2.0))));
        assertThat(bSpline.getControlVectors().get(3), is(vectorOf(Vector.crisp( 0.0, 0.0, 1.0))));
        assertThat(bSpline.getControlVectors().get(4), is(vectorOf(Vector.crisp( 1.0, 0.0, 0.0))));
        assertEquals(5, bSpline.getControlVectors().size());
    }

    @Test
    public void test_GetKnots() {
        System.out.println("GetKnots");
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
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
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
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
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        assertEquals(3, bSpline.getDegree().intValue());
    }

    @Test
    public void test_InsertKnot() {
        System.out.println("InsertKnot");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnot(0.5);

        BSplineDerivative expected = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 0.5, 0.5), Vector.crisp(-0.75, 1.0, 1.25), Vector.crisp(0.0, 0.75, 1.75), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(0.5, 1), Knot.of(1.0, 1), Knot.of(2.0, 4)));

        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_InsertKnotMultiple() {
        System.out.println("InsertKnotMultiple");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnotMultiple(0.5, 3);

        BSplineDerivative expected = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 0.5, 0.5),
                        Vector.crisp(-7/8.0, 0.75, 7/8.0), Vector.crisp(-23/32.0, 27/32.0, 9/8.0),
                        Vector.crisp(-9/16.0, 15/16.0, 11/8.0), Vector.crisp(0.0, 0.75, 1.75),
                        Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(0.5, 3), Knot.of(1.0, 1), Knot.of(2.0, 4)));

        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_InsertKnot1() {
        System.out.println("InsertKnot1");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnot(4);

        BSplineDerivative expected = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(-0.5, 1.0, 1.5), Vector.crisp(0.0, 0.5, 1.5), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(1.0, 2), Knot.of(2.0, 4)));

        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_InsertKnotMultiple1() {
        System.out.println("InsertKnotMultiple1");
        BSplineDerivative actual = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .insertKnotMultiple(4, 2);

        BSplineDerivative expected = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(-0.5, 1.0, 1.5), Vector.crisp(-0.25, 0.75, 1.5), Vector.crisp(0.0, 0.5, 1.5), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Stream.of(Knot.of(0.0, 4), Knot.of(1.0, 3), Knot.of(2.0, 4)));

        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void test_ToBeziers() {
        System.out.println("ToBeziers");
        Array<BezierDerivative> beziers = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .toBeziers();
        assertThat(beziers.get(0).toBezier(), is(bezierOf(BezierDerivative.create(Stream.of(
                Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0),
                Vector.crisp(-0.5, 1.0, 1.5), Vector.crisp(-0.25, 0.75, 1.5))).toBezier())));
        assertThat(beziers.get(1).toBezier(), is(bezierOf(BezierDerivative.create(Stream.of(
                Vector.crisp(-0.25, 0.75, 1.5), Vector.crisp(0.0, 0.5, 1.5),
                Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0))).toBezier())));
        assertEquals(2, beziers.size());    }

    @Test
    public void test_Subdivide() {
        System.out.println("Subdivide");
        Tuple2<BSplineDerivative, BSplineDerivative> beziers = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5))
                .subdivide(1.0);
        assertThat(beziers._1().toBSpline(), is(bSplineOf(BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(-0.5, 1.0, 1.5), Vector.crisp(-0.25, 0.75, 1.5)),
                Knot.clampedUniformKnots(3, 4)).toBSpline())));
        assertThat(beziers._2().toBSpline(), is(bSplineOf(BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-0.25, 0.75, 1.5), Vector.crisp(0.0, 0.5, 1.5), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(1.0, 2.0, 3, 4)).toBSpline())));
    }

    @Test
    public void test_ToBSpline() {
        System.out.println("ToBSpline");
        BSpline instance = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1.0, 1.0), Vector.crisp(0.0, 1.0), Vector.crisp(0.0, 0.0), Vector.crisp(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 5)).toBSpline();

        BSpline bSpline = BSpline.create(
                3,
                Stream.of(Point.crisp(-1.0, 0.0), Point.crisp(-1.0, 1.0), Point.crisp(0.0, 1.0), Point.crisp(0.0, 0.0), Point.crisp(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));

        assertThat(instance, is(bSplineOf(bSpline)));
    }

    @Test
    public void test_ToString() {
        System.out.println("ToString");
        BSplineDerivative bSpline = BSplineDerivative.create(3,
                Stream.of(Vector.crisp(-1.0, 0.0, 0.0), Vector.crisp(-1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 2.0), Vector.crisp(0.0, 0.0, 1.0), Vector.crisp(1.0, 0.0, 0.0)),
                Knot.clampedUniformKnots(3, 5));
        BSplineDerivative actual = BSplineDerivative.fromJson(bSpline.toString()).get();
        assertThat(actual.toBSpline(), is(bSplineOf(bSpline.toBSpline())));
    }

}