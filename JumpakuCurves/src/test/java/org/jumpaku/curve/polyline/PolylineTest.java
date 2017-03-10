/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import java.util.LinkedList;
import javaslang.collection.Array;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.curve.Curve;
import org.jumpaku.curve.FuzzyCurve;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class PolylineTest {
    
    public PolylineTest() {
    }

    /**
     * Test create create method, create class Polyline.
     */
    @Test
    public void testCreate_FuzzyPointArr() {
        System.out.println("create");
        FuzzyPoint[] ps = null;
        Polyline expResult = null;
        Polyline result = Polyline.create(ps);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test create create method, create class Polyline.
     */
    @Test
    public void testCreate_Iterable() {
        System.out.println("create");
        Iterable<FuzzyPoint> ps = null;
        Polyline expResult = null;
        Polyline result = Polyline.create(ps);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test create approximate method, create class Polyline.
     */
    @Test
    public void testApproximate() {
        System.out.println("approximate");
        Curve curve = null;
        Integer n = null;
        Double accuracy = null;
        Polyline expResult = null;
        Polyline result = Polyline.approximate(curve, n, accuracy);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test create toLines method, create class Polyline.
     */
    @Test
    public void testToLines() {
        System.out.println("toLines");
        FuzzyCurve curve = null;
        FuzzyPoint pa = null;
        Double a = null;
        FuzzyPoint pb = null;
        Double b = null;
        Double accuracy = null;
        LinkedList<FuzzyPoint> expResult = null;
        LinkedList<FuzzyPoint> result = Polyline.toLines(curve, pa, a, pb, b, accuracy);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        Polyline instance = new PolylineImpl();
        Array<? extends FuzzyPoint> expResult = null;
        Array<? extends FuzzyPoint> result = instance.getPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        Polyline instance = new PolylineImpl();
        Array<? extends FuzzyPoint> expResult = null;
        Array<? extends FuzzyPoint> result = instance.getPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testEvaluateAllByArcLengthParams() {
        System.out.println("evaluateAllByArcLengthParams");
        Polyline instance = new PolylineImpl();
        Array<? extends FuzzyPoint> expResult = null;
        Array<? extends FuzzyPoint> result = instance.getPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
