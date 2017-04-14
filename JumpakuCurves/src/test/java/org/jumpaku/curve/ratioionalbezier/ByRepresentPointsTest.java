/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.ratioionalbezier.ByRepresentPointsMatcher.conicSectionOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class ByRepresentPointsTest {
    
    public ByRepresentPointsTest() {
    }
    /**
     * Test of evaluate method, of class ConicSection.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        Double t = null;
        ByRepresentPoints instance = null;
        Point expResult = null;
        Point result = instance.evaluate(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDomain method, of class ByRepresentPoints.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        ByRepresentPoints instance = null;
        Interval expResult = null;
        Interval result = instance.getDomain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of differentiate method, of class ByRepresentPoints.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        ByRepresentPoints instance = null;
        Derivative expResult = null;
        Derivative result = instance.differentiate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of restrict method, of class ByRepresentPoints.
     */
    @Test
    public void testRestrict_Interval() {
        System.out.println("restrict");
        Interval i = null;
        ByRepresentPoints instance = null;
        ByRepresentPoints expResult = null;
        ByRepresentPoints result = instance.restrict(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of restrict method, of class ByRepresentPoints.
     */
    @Test
    public void testRestrict_Double_Double() {
        System.out.println("restrict");
        Double begin = null;
        Double end = null;
        ByRepresentPoints instance = null;
        ByRepresentPoints expResult = null;
        ByRepresentPoints result = instance.restrict(begin, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reverse method, of class ByRepresentPoints.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        ByRepresentPoints instance = null;
        ByRepresentPoints expResult = null;
        ByRepresentPoints result = instance.reverse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDegree method, of class ByRepresentPoints.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        ByRepresentPoints instance = null;//new ByRepresentPointsImpl();
        Integer expResult = null;
        Integer result = instance.getDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class ByRepresentPoints.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ByRepresentPoints expected = new ByRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        ByRepresentPoints actual = new JsonByRepresentPoints().fromJson(expected.toString()).get();
        assertThat(actual, is(conicSectionOf(expected)));
    }
    
    /**
     * Test of getRepresentPoints method, of class ByRepresentPoints.
     */
    @Test
    public void testGetRepresentPoints() {
        System.out.println("getRepresentPoints");
        ByRepresentPoints instance = null;//new ByRepresentPointsImpl();
        Array<Point> expResult = null;
        Array<Point> result = instance.getRepresentPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toCrispRationalBezier method, of class ByRepresentPoints.
     */
    @Test
    public void testToCrispRationalBezier() {
        System.out.println("toCrispRationalBezier");
        ByRepresentPoints instance = null;
        ByRepresentPoints expResult = null;
        ByRepresentPoints result = instance.reverse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCrispRationalBezier method, of class ByRepresentPoints.
     */
    @Test
    public void testCreateCrispControlPoints() {
        System.out.println("createCrispControlPoints");
        ByRepresentPoints instance = null;
        ByRepresentPoints expResult = null;
        ByRepresentPoints result = instance.reverse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeight method, of class ByRepresentPoints.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        ByRepresentPoints instance = null;//new ByRepresentPointsImpl();
        Double expResult = null;
        Double result = instance.getWeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complement method, of class ByRepresentPoints.
     */
    @Test
    public void testComplement() {
        System.out.println("complement");
        ByRepresentPoints instance = null;//new ByRepresentPointsImpl();
        ByRepresentPoints expResult = null;
        ByRepresentPoints result = instance.complement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
