/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.PointMatcher;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import static org.jumpaku.curve.bezier.BezierDerivativeMatcher.bezierDerivativeOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class DecasteljauTest {
    /**
     * Test of decasteljau method, of class AbstractBezier.
     */
    @Test
    public void testDecasteljau() {
        System.out.println("decasteljau");
        Array<Point> expResult = Array.of(Point.fuzzy(0.75, 0.75, -0.5, 0.75), Point.fuzzy(-0.25, 2.0, 3.0, 0.5));
        Array<Point> result = Decasteljau.decasteljau(0.25,
                Array.of(Point.fuzzy(1.0, 0.0, -2.0, 1.0), Point.fuzzy(0.0, 3.0, 4.0, 0.0), Point.fuzzy(-1.0, -1.0, 0.0, 2.0)));
        assertEquals(result.size(), expResult.size());
        assertThat(result.get(0), PointMatcher.pointOf(expResult.get(0)));
        assertThat(result.get(1), PointMatcher.pointOf(expResult.get(1)));
    }

    /**
     * Test of getDomain method, of class AbstractBezier.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Array<Point> controlPoints = Array.of(Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.9);
        Bezier result = Bezier.create(domain, controlPoints);
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of getDegree method, of class AbstractBezier.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Array<Point> controlPoints = Array.of(Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        Bezier result = Bezier.create(domain, controlPoints);
        assertEquals(3, result.getDegree().intValue());
    }

    /**
     * Test of differentiate method, of class AbstractBezier.
     */
    @Test
    public void testDifferentiate_Double() {
        System.out.println("differentiate");
        Bezier instance = Bezier.create(Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(1.0, 0.0, 3.0), Point.fuzzy(1.0, 1.0, 4.0));
        assertTrue(Vector.equals(Vector.crisp(0.0, 3.0), instance.differentiate(0.0), 1.0e-10));
        assertTrue(Vector.equals(Vector.crisp(1.5, 0.0), instance.differentiate(0.5), 1.0e-10));
        assertTrue(Vector.equals(Vector.crisp(0.0, 3.0), instance.differentiate(1.0), 1.0e-10));        
    }

    /**
     * Test of differentiate method, of class AbstractBezier.
     */
    @Test
    public void testDifferentiate_0args() {
        System.out.println("differentiate");
        Bezier instance = Bezier.create(Interval.of(0.2, 0.8), Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(0.0, 1.0, 2.0), Point.fuzzy(1.0, 0.0, 3.0), Point.fuzzy(1.0, 1.0, 4.0));
        BezierDerivative expResult = BezierDerivative.create(Interval.of(0.2, 0.8), Array.of(Vector.crisp(0.0, 3.0), Vector.crisp(3.0, -3.0), Vector.crisp(0.0, 3.0)));
        assertThat(instance.differentiate(), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of restrict method, of class AbstractBezier.
     */
    @Test
    public void testRestrict() {
        System.out.println("restrict");
        Bezier instance = Bezier.create(Interval.of(0.2, 0.8), Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Bezier expResult = Bezier.create(Interval.of(0.2, 0.5), Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        assertThat(instance.restrict(0.2, 0.5), is(bezierOf(expResult)));
    }

    /**
     * Test of restrict method, of class AbstractBezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRestrictException() {
        System.out.println("restrict");
        Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0))
                .restrict(0.1, 0.9);
    }
    
    /**
     * Test of reverse method, of class AbstractBezier.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        Bezier instance = Bezier.create(Interval.of(0.3, 0.9), Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Bezier expResult = Bezier.create(Interval.of(0.1, 0.7), Point.fuzzy(1.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(0.0, 0.0));
        assertThat(instance.reverse(), is(bezierOf(expResult)));
    }

    /**
     * Test of getControlPoints method, of class AbstractBezier.
     */
    @Test
    public void testGetControlPoints() {
        System.out.println("getControlPoints");
        Array<Point> controlPoints = Array.of(Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.9);
        Bezier result = Bezier.create(domain, controlPoints);
        assertThat(result.getControlPoints().get(0), is(PointMatcher.pointOf(controlPoints.get(0))));
        assertThat(result.getControlPoints().get(1), is(PointMatcher.pointOf(controlPoints.get(1))));
        assertThat(result.getControlPoints().get(2), is(PointMatcher.pointOf(controlPoints.get(2))));
        assertThat(result.getControlPoints().get(3), is(PointMatcher.pointOf(controlPoints.get(3))));
        assertEquals(result.getControlPoints().size(), controlPoints.size());
    }

    /**
     * Test of elevate method, of class AbstractBezier.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        Bezier instance = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(-1.0, 0.0), Point.fuzzy(0.0, 2.0), Point.fuzzy(1.0, 0.0));
        Bezier expected = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(-1.0, 0.0), Point.fuzzy(-1/3.0, 4/3.0), Point.fuzzy(1/3.0, 4/3.0), Point.fuzzy(1.0, 0.0));
        assertThat(instance.elevate(), is(bezierOf(expected)));
    }

    /**
     * Test of reduce method, of class AbstractBezier.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");
        Bezier b1 = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(-1.0, -1.0), Point.fuzzy(1.0, 1.0));
        Bezier e1 = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(0.0, 0.0));
        assertThat(b1.reduce(), is(bezierOf(e1)));

        Bezier b2 = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(-1.0, 0.0), Point.fuzzy(0.0, 0.0), Point.fuzzy(1.0, 0.0));
        Bezier e2 = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(-1.0, 0.0), Point.fuzzy(1.0, 0.0));
        assertThat(b2.reduce(), is(bezierOf(e2)));

        Bezier b3 = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(-1.0, 0.0), Point.fuzzy(-1/3.0, 4/3.0), Point.fuzzy(1/3.0, 4/3.0), Point.fuzzy(1.0, 0.0));
        Bezier e3 = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(-1.0, 0.0), Point.fuzzy(0.0, 2.0), Point.fuzzy(1.0, 0.0));
        assertThat(b3.reduce(), is(bezierOf(e3)));
    }
    
    /**
     * Test of reduce method, of class AbstractBezier.
     */
    @Test(expected = IllegalStateException.class)
    public void testReduceException() {
        System.out.println("reduce");
        Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(0.0, 0.0)).reduce();
    }

    /**
     * Test of subdivide method, of class AbstractBezier.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        Double t = 0.25;
        Bezier instance = Bezier.create(Interval.of(0.2, 0.9), Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Bezier first = instance.subdivide(t)._1();
        Bezier second = instance.subdivide(t)._2();
        assertThat(first, is(bezierOf(Bezier.create(Interval.of(0.8, 1.0), Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 0.25), Point.fuzzy(1/16.0, 3/8.0), Point.fuzzy(5/32.0, 7/16.0)))));
        assertThat(second, is(bezierOf(Bezier.create(Interval.of(0.0, 13/15.0), Point.fuzzy(5/32.0, 7/16.0), Point.fuzzy(7/16.0, 5/8.0), Point.fuzzy(1.0, 0.250), Point.fuzzy(1.0, 1.0)))));
    }

    /**
     * Test of toString method, of class AbstractBezier.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Bezier expected = Bezier.create(Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Bezier actual = new JsonBezier().fromJson(expected.toString()).get();
        assertThat(actual, is(bezierOf(expected)));
    }
}
