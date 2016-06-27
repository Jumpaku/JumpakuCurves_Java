/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.util.Precision;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class Vec1Test {
    
    public Vec1Test() {
    }

    /**
     * Test of add method, of class Vec1.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Vec v = new Vec1(3.5);
        Vec1 instance = new Vec1(-0.5);
        Vec1 expResult = new Vec1(3.0);
        Vec1 result = (Vec1)instance.add(v);
        assertTrue(chechEquals(result, expResult));
    }

    /**
     * Test of scale method, of class Vec1.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Double a = 0.2;
        Vec1 instance = new Vec1(0.3);
        Vec expResult = new Vec1(0.06);
        Vec result = instance.scale(a);
        assertTrue(chechEquals(result, expResult));
    }

    /**
     * Test of getDimention method, of class Vec1.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Vec1 instance = new Vec1(3.0);
        Integer expResult = 1;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Vec1.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Integer i = 0;
        Vec1 instance = new Vec1(5.9);
        Double expResult = 5.9;
        Double result = instance.get(i);
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of dot method, of class Vec1.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec v = new Vec1(3.0);
        Vec1 instance = new Vec1(-0.3);
        Double expResult = -0.9;
        Double result = instance.dot(v);
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of getX method, of class Vec1.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vec1 instance = new Vec1(6.8);
        Double expResult = 6.8;
        Double result = instance.getX();
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of equals method, of class Vec1.
     */
    @Test
    public void testEquals_Vec_Double() {
        System.out.println("equals");
        Vec v = new Vec1(1.0);
        Double eps = 1.0e-10;
        Vec1 instance = new Vec1(1.00000000001);
        Boolean expResult = true;
        Boolean result = instance.equals(v, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Vec1.
     */
    @Test
    public void testEquals_Vec_Integer() {
        System.out.println("equals");
        Vec v = new Vec1(-0.0);
        Integer ulp = 2;
        Vec1 instance = new Vec1(0.0);
        Boolean expResult = true;
        Boolean result = instance.equals(v, ulp);
        assertEquals(expResult, result);
    }
 
    private static boolean chechEquals(Vec v1, Vec v2){
        return checkEqualsDouble(v1.get(0), v1.get(0));
    }
    private static boolean checkEqualsDouble(Double a, Double b){
        return Precision.equals(a, b, 1.0e-10);
    }
}
