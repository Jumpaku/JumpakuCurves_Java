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
public class WeightedPoint3DTest {
    
    public WeightedPoint3DTest() {
    }

    /**
     * Test of getWeight method, of class WeightedPoint3D.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        WeightedPoint3D instance = new WeightedPoint3D(4.9, 2.0, 2.5, 4.0);
        Double expResult = 4.9;
        Double result = instance.getWeight();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getProduct method, of class WeightedPoint3D.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        WeightedPoint3D instance = new WeightedPoint3D(3.2, 4.0, -1.0, -6.0);
        Point3D expResult = new Point3D(12.8, -3.2, -19.2);
        Point3D result = instance.getProduct();
        assertTrue(expResult.equals(result, 1.0e-10));
    }
}
