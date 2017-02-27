/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
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
public class AbstractBezierTest {
    /**
     * Test of decasteljau method, of class AbstractBezier.
     */
    @Test
    public void testDecasteljau() {
        System.out.println("decasteljau");
        Array<Point> expResult = Array.of(Point.of(0.75, 0.75, -0.5), Point.of(-0.25, 2.0, 3.0));
        Array<Point> result = AbstractBezier.decasteljau(0.25, Array.of(Point.of(1.0, 0.0, -2.0), Point.of(0.0, 3.0, 4.0), Point.of(-1.0, -1.0, 0.0)));
        assertTrue(expResult.zipWith(result, (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && expResult.size() == result.size());
    }

    /**
     * Test of getDomain method, of class AbstractBezier.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Array<? extends Point> controlPoints = Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        Bezier result = Bezier.create(controlPoints, domain);
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of getDegree method, of class AbstractBezier.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Array<? extends Point> controlPoints = Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        Bezier result = Bezier.create(controlPoints, domain);
        assertEquals(3, result.getDegree().intValue());
    }

    /**
     * Test of differentiate method, of class AbstractBezier.
     */
    @Test
    public void testDifferentiate_Double() {
        System.out.println("differentiate");
        Bezier instance = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        assertTrue(Vector.equals(Vector.of(0.0, 3.0), instance.differentiate(0.0), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(1.5, 0.0), instance.differentiate(0.5), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(0.0, 3.0), instance.differentiate(1.0), 1.0e-10));        
    }

    /**
     * Test of differentiate method, of class AbstractBezier.
     */
    @Test
    public void testDifferentiate_0args() {
        System.out.println("differentiate");
        Bezier instance = Bezier.create(Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0)), Interval.of(0.2, 0.8));
        BezierDerivative expResult = BezierDerivative.create(Array.of(Point.of(0.0, 3.0), Point.of(3.0, -3.0), Point.of(0.0, 3.0)).map(Point::getVector), Interval.of(0.2, 0.8));
        assertThat(instance.differentiate(), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of restrict method, of class AbstractBezier.
     */
    @Test
    public void testRestrict() {
        System.out.println("restrict");
        Bezier instance = Bezier.create(Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0)), Interval.of(0.2, 0.8));
        Bezier expResult = Bezier.create(Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0)), Interval.of(0.2, 0.5));
        assertThat(instance.restrict(0.2, 0.5), is(bezierOf(expResult)));
    }

    /**
     * Test of restrict method, of class AbstractBezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRestrictException() {
        System.out.println("restrict");
        Bezier instance = Bezier.create(Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0)), Interval.of(0.2, 0.8));
        instance.restrict(0.3, 0.9);
    }
    
    /**
     * Test of reverse method, of class AbstractBezier.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        Bezier instance = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Bezier expResult = Bezier.create(Point.of(1.0, 1.0), Point.of(1.0, 0.0), Point.of(0.0, 1.0), Point.of(0.0, 0.0));
        assertThat(instance.reverse(), is(bezierOf(expResult)));
    }

    /**
     * Test of getControlPoints method, of class AbstractBezier.
     */
    @Test
    public void testGetControlPoints() {
        System.out.println("getControlPoints");
        Array<? extends Point> controlPoints = Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        Bezier result = Bezier.create(controlPoints, domain);
        assertTrue(controlPoints.zipWith(result.getControlPoints(), (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && controlPoints.size() == result.getControlPoints().size());
    }

    /**
     * Test of elevate method, of class AbstractBezier.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        Bezier instance = Bezier.create(Array.of(Point.of(-1.0, 0.0), Point.of(0.0, 2.0), Point.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        Bezier expected = Bezier.create(Array.of(Point.of(-1.0, 0.0), Point.of(-1/3.0, 4/3.0), Point.of(1/3.0, 4/3.0), Point.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(instance.elevate(), is(bezierOf(expected)));
    }

    /**
     * Test of reduce method, of class AbstractBezier.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");
        Bezier b1 = Bezier.create(Array.of(Point.of(-1.0, -1.0), Point.of(1.0, 1.0)), Interval.of(0.2, 0.8));
        Bezier e1 = Bezier.create(Array.of(Point.of(0.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(b1.reduce(), is(bezierOf(e1)));

        Bezier b2 = Bezier.create(Array.of(Point.of(-1.0, 0.0), Point.of(0.0, 0.0), Point.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        Bezier e2 = Bezier.create(Array.of(Point.of(-1.0, 0.0), Point.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(b2.reduce(), is(bezierOf(e2)));

        Bezier b3 = Bezier.create(Array.of(Point.of(-1.0, 0.0), Point.of(-1/3.0, 4/3.0), Point.of(1/3.0, 4/3.0), Point.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        Bezier e3 = Bezier.create(Array.of(Point.of(-1.0, 0.0), Point.of(0.0, 2.0), Point.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(b3.reduce(), is(bezierOf(e3)));
    }
    
    /**
     * Test of reduce method, of class AbstractBezier.
     */
    @Test(expected = IllegalStateException.class)
    public void testReduceException() {
        System.out.println("reduce");
        Bezier.create(Array.of(Point.of(0.0, 0.0)), Interval.of(0.2, 0.8)).reduce();
    }

    /**
     * Test of subdivide method, of class AbstractBezier.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        Double t = 0.25;
        Bezier instance = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Bezier first = instance.subdivide(t)._1();
        Bezier second = instance.subdivide(t)._2();
        assertThat(first, is(bezierOf(Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 0.25), Point.of(1/16.0, 3/8.0), Point.of(5/32.0, 7/16.0)))));
        assertThat(second, is(bezierOf(Bezier.create(Point.of(5/32.0, 7/16.0), Point.of(7/16.0, 5/8.0), Point.of(1.0, 0.250), Point.of(1.0, 1.0)))));
    }

    /**
     * Test of toString method, of class AbstractBezier.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Bezier expected = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Bezier actual = new JsonBezier().fromJson(expected.toString()).get();
        assertThat(actual, is(bezierOf(expected)));
    }
}
