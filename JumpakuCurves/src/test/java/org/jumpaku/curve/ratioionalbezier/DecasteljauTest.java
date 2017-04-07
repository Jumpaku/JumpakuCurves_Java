/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.ratioionalbezier.RationalBezierMatcher.rationalBezierOf;
import static org.jumpaku.curve.ratioionalbezier.WeightedPointMatcher.weightedPointOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class DecasteljauTest {
    
    public DecasteljauTest() {
    }

    /**
     * Test of evaluate method, of class Decasteljau.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        Double t = null;
        Decasteljau instance = null;
        Point expResult = null;
        Point result = instance.evaluate(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDomain method, of class Decasteljau.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Interval result = RationalBezier.create(
                Interval.of(0.2, 0.9),
                new WeightedPoint(1.0, Point.fuzzy(0.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(0.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(1.0, 1.0))).getDomain();
        assertEquals(0.2, result.getBegin(), 1.0e-10);
        assertEquals(0.9, result.getEnd(), 1.0e-10);
    }

    /**
     * Test of decasteljau method, of class Decasteljau.
     */
    @Test
    public void testDecasteljau() {
        System.out.println("decasteljau");
        Double t = null;
        Array<WeightedPoint> wcps = null;
        Array<WeightedPoint> expResult = null;
        Array<WeightedPoint> result = Decasteljau.decasteljau(t, wcps);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of weightBezier method, of class Decasteljau.
     */
    @Test
    public void testWeightBezier() {
        System.out.println("weightBezier");
        Double t = null;
        Array<Double> ws = null;
        Double expResult = null;
        Double result = Decasteljau.weightBezier(t, ws);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of differentiate method, of class Decasteljau.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        Decasteljau instance = null;
        Derivative expResult = null;
        Derivative result = instance.differentiate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of restrict method, of class Decasteljau.
     */
    @Test
    public void testRestrict_Interval() {
        System.out.println("restrict");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(1.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(2.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(4.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(8.0, 1.0)));
        RationalBezier result = RationalBezier.create(Interval.of(0.2, 0.9), points).restrict(Interval.of(0.3, 0.8));
        assertThat(result, is(rationalBezierOf(
                RationalBezier.create(Interval.of(0.3, 0.8), points))));
    }

    /**
     * Test of restrict method, of class Decasteljau.
     */
    @Test
    public void testRestrict_Double_Double() {
        System.out.println("restrict");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(1.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(2.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(4.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(8.0, 1.0)));
        RationalBezier result = RationalBezier.create(Interval.of(0.2, 0.9), points).restrict(0.3, 0.8);
        assertThat(result, is(rationalBezierOf(
                RationalBezier.create(Interval.of(0.3, 0.8), points))));
    }

    /**
     * Test of reverse method, of class Decasteljau.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(1.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(2.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(4.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(8.0, 1.0)));
        RationalBezier result = RationalBezier.create(Interval.of(0.2, 0.9), points).reverse();
        assertThat(result, is(rationalBezierOf(
                RationalBezier.create(Interval.of(0.1, 0.8), points.reverse()))));
    }

    /**
     * Test of getWeightedControlPoints method, of class Decasteljau.
     */
    @Test
    public void testGetWeightedControlPoints() {
        System.out.println("getWeightedControlPoints");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(1.0, Point.fuzzy(0.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(0.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(1.0, 1.0)));
        RationalBezier result = RationalBezier.create(points);
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
    }

    /**
     * Test of elevate method, of class Decasteljau.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        Decasteljau instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.elevate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reduce method, of class Decasteljau.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");
        Decasteljau instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.reduce();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subdivide method, of class Decasteljau.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        Double t = null;
        Decasteljau instance = null;
        Tuple2<RationalBezier, RationalBezier> expResult = null;
        Tuple2<RationalBezier, RationalBezier> result = instance.subdivide(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of getDegree method, of class Decasteljau.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(1.0, Point.fuzzy(0.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(0.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(1.0, 1.0)));
        int result = RationalBezier.create(points).getDegree();
        assertEquals(3, result);
    }


    /**
     * Test of toString method, of class Decasteljau.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        RationalBezier expected = RationalBezier.create(
                Interval.of(0.2, 0.9),
                new WeightedPoint(1.0, Point.zero(1.0)),
                new WeightedPoint(2.0, Point.fuzzy(0.0, 1.0, 0.5)),
                new WeightedPoint(3.0, Point.fuzzy(1.0, 0.0, 2.0)),
                new WeightedPoint(4.0, Point.crisp(1.0, 1.0)));
        RationalBezier actual = RationalBezier.fromJson(expected.toString()).get();
        assertThat(actual, is(rationalBezierOf(expected)));
    }
    
}
