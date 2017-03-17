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
import static org.jumpaku.curve.ratioionalbezier.ConicSectionMatcher.conicSectionOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class ConicSectionTest {
    
    public ConicSectionTest() {
    }
    /**
     * Test of evaluate method, of class ConicSection.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        Double t = null;
        ConicSection instance = null;
        Point expResult = null;
        Point result = instance.evaluate(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDomain method, of class ConicSection.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        ConicSection instance = null;
        Interval expResult = null;
        Interval result = instance.getDomain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of differentiate method, of class ConicSection.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        ConicSection instance = null;
        Derivative expResult = null;
        Derivative result = instance.differentiate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of restrict method, of class ConicSection.
     */
    @Test
    public void testRestrict_Interval() {
        System.out.println("restrict");
        Interval i = null;
        ConicSection instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.restrict(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of restrict method, of class ConicSection.
     */
    @Test
    public void testRestrict_Double_Double() {
        System.out.println("restrict");
        Double begin = null;
        Double end = null;
        ConicSection instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.restrict(begin, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reverse method, of class ConicSection.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        ConicSection instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.reverse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeightedControlPoints method, of class ConicSection.
     */
    @Test
    public void testGetWeightedControlPoints() {
        System.out.println("getWeightedControlPoints");
        ConicSection instance = null;
        Array<WeightedPoint> expResult = null;
        Array<WeightedPoint> result = instance.getWeightedControlPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of elevate method, of class ConicSection.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        ConicSection instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.elevate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reduce method, of class ConicSection.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");
        ConicSection instance = null;
        RationalBezier expResult = null;
        RationalBezier result = instance.reduce();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subdivide method, of class ConicSection.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        Double t = null;
        ConicSection instance = null;
        Tuple2<RationalBezier, RationalBezier> expResult = null;
        Tuple2<RationalBezier, RationalBezier> result = instance.subdivide(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDegree method, of class ConicSection.
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
     * Test of toString method, of class ConicSection.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ConicSection expected = RationalBezier.byRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        ConicSection actual = new JsonConicSection().fromJson(expected.toString()).get();
        assertThat(actual, is(conicSectionOf(expected)));
    }
    
    /**
     * Test of getRepresentPoints method, of class ConicSection.
     */
    @Test
    public void testGetRepresentPoints() {
        System.out.println("getRepresentPoints");
        ConicSection instance = new ConicSectionImpl();
        Array<Point> expResult = null;
        Array<Point> result = instance.getRepresentPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeight method, of class ConicSection.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        ConicSection instance = new ConicSectionImpl();
        Double expResult = null;
        Double result = instance.getWeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complement method, of class ConicSection.
     */
    @Test
    public void testComplement() {
        System.out.println("complement");
        ConicSection instance = new ConicSectionImpl();
        ConicSection expResult = null;
        ConicSection result = instance.complement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
