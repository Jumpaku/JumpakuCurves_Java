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
public class Vec3Test {

    /**
     * Test of add method, of class Vec3.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Vec v = new Vec3(3.5, -3.4, -4.2);
        Vec3 instance = new Vec3(-0.5, 3.5, -9.2);
        Vec3 expResult = new Vec3(3.0, 0.1, -13.4);
        Vec3 result = (Vec3)instance.add(v);
        assertTrue(chechEquals(result, expResult));
    }

    /**
     * Test of scale method, of class Vec3.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Double a = 0.2;
        Vec3 instance = new Vec3(0.3, -0.09, 3.5);
        Vec expResult = new Vec3(0.06, -0.018, 0.7);
        Vec result = instance.scale(a);
        assertTrue(chechEquals(result, expResult));
    }

    /**
     * Test of getDimention method, of class Vec3.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Vec3 instance = new Vec3(3.0, 4.2, 3.0);
        Integer expResult = 3;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Vec3.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Integer i0 = 0;
        Integer i1 = 1;
        Integer i2 = 2;
        Vec3 instance = new Vec3(5.9, 4.3, -0.0);
        Double expResult0 = 5.9;
        Double expResult1 = 4.3;
        Double expResult2 = -0.0;
        Double result0 = instance.get(i0);
        Double result1 = instance.get(i1);
        Double result2 = instance.get(i2);
        assertTrue(checkEqualsDouble(result0, expResult0));
        assertTrue(checkEqualsDouble(result1, expResult1));
        assertTrue(checkEqualsDouble(result2, expResult2));
    }

    /**
     * Test of dot method, of class Vec3.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec v = new Vec3(3.0, -2.8, -0.003);
        Vec3 instance = new Vec3(-0.3, 0.5, 10000.9);
        Double expResult = -0.9 + -1.4 + -30.0027;
        Double result = instance.dot(v);
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of getX method, of class Vec3.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vec3 instance = new Vec3(6.8, 4.4, 3.2);
        Double expResult = 6.8;
        Double result = instance.getX();
        assertTrue(checkEqualsDouble(result, expResult));
    }
    
    /**
     * Test of getX method, of class Vec3.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Vec3 instance = new Vec3(6.8, 4.4, 3.2);
        Double expResult = 4.4;
        Double result = instance.getY();
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of getX method, of class Vec3.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        Vec3 instance = new Vec3(6.8, 4.4, 3.2);
        Double expResult = 3.2;
        Double result = instance.getZ();
        assertTrue(checkEqualsDouble(result, expResult));
    }
    
    /**
     * Test of equals method, of class Vec3.
     */
    @Test
    public void testEquals_Vec_Double() {
        System.out.println("equals");
        Vec v = new Vec3(1.0, 1000000000000.0, 0.0000000000100001);
        Double eps = 1.0e-10;
        Vec3 instance = new Vec3(1.00000000001, 1000000000000.0, 0.00000000001);
        Boolean expResult = true;
        Boolean result = instance.equals(v, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Vec3.
     */
    @Test
    public void testEquals_Vec_Integer() {
        System.out.println("equals");
        Vec v = new Vec3(0.0, 1000000000.0001, 0.0000000000100001);
        Integer ulp = 40000;
        Vec3 instance = new Vec3(-0.0, 1000000000.0, 0.00000000001);
        Boolean expResult = false;
        Boolean result = instance.equals(v, ulp);
        assertEquals(expResult, result);
    }
 
    private static boolean chechEquals(Vec v1, Vec v2){
        return checkEqualsDouble(v1.get(0), v1.get(0)) && checkEqualsDouble(v1.get(1), v2.get(1)) && checkEqualsDouble(v1.get(2), v2.get(2));
    }
    private static boolean checkEqualsDouble(Double a, Double b){
        return Precision.equals(a, b, 1.0e-10);
    }

    /**
     * Test of add method, of class Vec3.
     */
    @Test
    public void testAdd_Vec() {
        System.out.println("add");
        Vec v = null;
        Vec3 instance = null;
        Vec expResult = null;
        Vec result = instance.add(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Vec3.
     */
    @Test
    public void testAdd_Double_Vec() {
        System.out.println("add");
        Double a = null;
        Vec v = null;
        Vec3 instance = null;
        Vec3 expResult = null;
        Vec3 result = instance.add(a, v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of negate method, of class Vec3.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Vec3 instance = null;
        Vec3 expResult = null;
        Vec3 result = instance.negate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalize method, of class Vec3.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        Vec3 instance = null;
        Vec3 expResult = null;
        Vec3 result = instance.normalize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sub method, of class Vec3.
     */
    @Test
    public void testSub_Vec() {
        System.out.println("sub");
        Vec v = null;
        Vec3 instance = null;
        Vec3 expResult = null;
        Vec3 result = instance.sub(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sub method, of class Vec3.
     */
    @Test
    public void testSub_Double_Vec() {
        System.out.println("sub");
        Double a = null;
        Vec v = null;
        Vec3 instance = null;
        Vec3 expResult = null;
        Vec3 result = instance.sub(a, v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cross method, of class Vec3.
     */
    @Test
    public void testCross() {
        System.out.println("cross");
        Vec3 v = null;
        Vec3 instance = null;
        Vec3 expResult = null;
        Vec3 result = instance.cross(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of angle method, of class Vec3.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        Vec3 v = null;
        Vec3 instance = null;
        Double expResult = null;
        Double result = instance.angle(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
