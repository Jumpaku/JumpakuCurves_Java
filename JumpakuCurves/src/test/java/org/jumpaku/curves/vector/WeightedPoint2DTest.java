/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import static org.jumpaku.curves.test.TestUtils.equalsDouble;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class WeightedPoint2DTest {
    
    public WeightedPoint2DTest() {
    }

    /**
     * Test of getWeight method, of class WeightedPoint2D.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        WeightedPoint2D instance = new WeightedPoint2D(4.9, 2.0, 3.0);
        Double expResult = 4.9;
        Double result = instance.getWeight();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getProduct method, of class WeightedPoint2D.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        WeightedPoint2D instance = new WeightedPoint2D(3.2, 4.0, -0.3);
        Point2D expResult = new Point2D(12.8, -0.96);
        Point2D result = instance.getProduct();
        assertTrue(expResult.equals(result, 1.0e-10));
    }
    
}
