/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.jumpaku.curves.test.TestUtils.*;

/**
 *
 * @author ito tomohiko
 */
public class WeightedPoint1DTest {
    
    public WeightedPoint1DTest() {
    }

    /**
     * Test of getWeight method, of class WeightedPoint1D.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        WeightedPoint1D instance = new WeightedPoint1D(4.9, 2.0);
        Double expResult = 4.9;
        Double result = instance.getWeight();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getProduct method, of class WeightedPoint1D.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        WeightedPoint1D instance = new WeightedPoint1D(3.2, 4.0);
        Point1D expResult = new Point1D(12.8);
        Point1D result = instance.getProduct();
        assertTrue(expResult.equals(result, 1.0e-10));
    }
    
}
