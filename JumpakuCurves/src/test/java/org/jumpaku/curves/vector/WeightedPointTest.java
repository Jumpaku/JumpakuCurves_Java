/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.util.Precision;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.jumpaku.curves.test.TestUtils.*;

/**
 *
 * @author ito tomohiko
 */
public class WeightedPointTest {
    
    public WeightedPointTest() {
    }

    /**
     * Test of of method, of class WeightedPoint.
     */
    @Test
    public void testOf_Double_Point() {
        System.out.println("of");
        Double w = 5.0;
        Point p = new Point3D(-1.0, 1.0, 4.0);
        WeightedPoint expResult = new WeightedPoint3D(5.0, -1.0, 1.0, 4.0);
        WeightedPoint result = WeightedPoint.of(w, p);
        assertTrue(equalsVec(expResult.getVec(), result.getVec()) && Precision.equals(expResult.getWeight(), result.getWeight(), 1.0e-10));
    }

    /**
     * Test of of method, of class WeightedPoint.
     */
    @Test
    public void testOf_Double_DoubleArr() {
        System.out.println("of");
        Double w = 5.0;
        Double[] elements = new Double[]{-1.0, 1.0, 4.0};
        WeightedPoint expResult = new WeightedPoint3D(5.0, -1.0, 1.0, 4.0);
        WeightedPoint result = WeightedPoint.of(w, elements);
        assertTrue(equalsVec(expResult.getVec(), result.getVec()) && Precision.equals(expResult.getWeight(), result.getWeight(), 1.0e-10));
    }

    /**
     * Test of getWeight method, of class WeightedPoint.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        WeightedPoint instance = WeightedPoint.of(2.0, -1.0, 2.0, 5.0);
        Double expResult = 2.0;
        Double result = instance.getWeight();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getProduct method, of class WeightedPoint.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        WeightedPoint instance = WeightedPoint.of(2.0, -1.0, 2.0, 5.0);
        Point expResult = Point.of(-2.0, 4.0, 10.0);
        Point result = instance.getProduct();
        assertTrue(equalsVec(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of equals method, of class WeightedPoint.
     */
    @Test
    public void testEquals_WeightedPoint_Double() {
        System.out.println("equals");
        WeightedPoint p = WeightedPoint.of(3.0, 2.0, -1.0, 4.2);
        Double eps = 1.0e-10;
        WeightedPoint instance = WeightedPoint.of(3.00000000001, 2.00000000001, -1.0, 4.2);
        Boolean expResult = true;
        Boolean result = instance.equals(p, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class WeightedPoint.
     */
    @Test
    public void testEquals_WeightedPoint_Integer() {
        System.out.println("equals");
        WeightedPoint p = WeightedPoint.of(3.0, 2.0, 12345678901.0, -1.5, -0.0);
        Integer ulp = 55000;
        WeightedPoint instance = WeightedPoint.of(3.0, 2.0, 12345678901.1, -1.5, 0.0);
        Boolean expResult = true;
        Boolean result = instance.equals(p, ulp);
        assertEquals(expResult, result);
    }
}
