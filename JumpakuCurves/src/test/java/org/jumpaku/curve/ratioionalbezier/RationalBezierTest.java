/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.control.Option;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.bezier.Bezier;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jumpaku
 */
public class RationalBezierTest {
    
    public RationalBezierTest() {
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Iterable() {
        System.out.println("create");
        Iterable<WeightedPoint> weightedControlPoints = null;
        RationalBezier expResult = null;
        RationalBezier result = RationalBezier.create(weightedControlPoints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Interval_Iterable() {
        System.out.println("create");
        Interval i = null;
        Iterable<WeightedPoint> weightedControlPoints = null;
        RationalBezier expResult = null;
        RationalBezier result = RationalBezier.create(i, weightedControlPoints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_WeightedPointArr() {
        System.out.println("create");
        WeightedPoint[] weightedControlPoints = null;
        RationalBezier expResult = null;
        RationalBezier result = RationalBezier.create(weightedControlPoints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Interval_WeightedPointArr() {
        System.out.println("create");
        Interval i = null;
        WeightedPoint[] weightedControlPoints = null;
        RationalBezier expResult = null;
        RationalBezier result = RationalBezier.create(i, weightedControlPoints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Iterable_Iterable() {
        System.out.println("create");
        Iterable<? extends Point> controlPoints = null;
        Iterable<Double> weights = null;
        RationalBezier expResult = null;
        RationalBezier result = RationalBezier.create(controlPoints, weights);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_3args() {
        System.out.println("create");
        Interval i = null;
        Iterable<? extends Point> controlPoints = null;
        Iterable<Double> weights = null;
        RationalBezier expResult = null;
        RationalBezier result = RationalBezier.create(i, controlPoints, weights);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of byRepresentPoints method, of class RationalBezier.
     */
    @Test
    public void testByRepresentPoints_4args() {
        System.out.println("fromRepresentPoints");
        Double weight = null;
        Point rp0 = null;
        Point rp1 = null;
        Point rp2 = null;
        ConicSection.ByRepresentPoints expResult = null;
        ConicSection.ByRepresentPoints result = RationalBezier.byRepresentPoints(weight, rp0, rp1, rp2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of byRepresentPoints method, of class RationalBezier.
     */
    @Test
    public void testByRepresentPoints_5args() {
        System.out.println("byRepresentPoints");
        Interval i = null;
        Double weight = null;
        Point rp0 = null;
        Point rp1 = null;
        Point rp2 = null;
        ConicSection.ByRepresentPoints expResult = null;
        ConicSection.ByRepresentPoints result = RationalBezier.byRepresentPoints(i, weight, rp0, rp1, rp2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromBezier method, of class RationalBezier.
     */
    @Test
    public void testFromBezier() {
        System.out.println("fromBezier");
        Bezier bezier = null;
        RationalBezier expResult = null;
        RationalBezier result = RationalBezier.fromBezier(bezier);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toJson method, of class RationalBezier.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        RationalBezier bezier = null;
        String expResult = "";
        String result = RationalBezier.toJson(bezier);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromJson method, of class RationalBezier.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        String json = "";
        Option<RationalBezier> expResult = null;
        Option<RationalBezier> result = RationalBezier.fromJson(json);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
