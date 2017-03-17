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
        Decasteljau instance = null;
        Interval expResult = null;
        Interval result = instance.getDomain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        Interval i = null;
        Decasteljau instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.restrict(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of restrict method, of class Decasteljau.
     */
    @Test
    public void testRestrict_Double_Double() {
        System.out.println("restrict");
        Double begin = null;
        Double end = null;
        Decasteljau instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.restrict(begin, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reverse method, of class Decasteljau.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        Decasteljau instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.reverse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeightedControlPoints method, of class Decasteljau.
     */
    @Test
    public void testGetWeightedControlPoints() {
        System.out.println("getWeightedControlPoints");
        Decasteljau instance = null;
        Array<WeightedPoint> expResult = null;
        Array<WeightedPoint> result = instance.getWeightedControlPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        ConicSection instance = new ConicSectionImpl();
        Integer expResult = null;
        Integer result = instance.getDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
