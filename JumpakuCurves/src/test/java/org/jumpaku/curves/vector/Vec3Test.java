/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.jumpaku.old.curves.vector.Vec;
import org.jumpaku.old.curves.vector.Vec3;
import org.junit.Test;
import static org.jumpaku.curves.test.TestUtils.*;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class Vec3Test {
    
    public Vec3Test() {
    }

    /**
     * Test of add method, of class Vec3.
     */
    @Test
    public void testAdd_Vec() {
        System.out.println("add");
        Vec v = new Vec3(1.0, -0.5, 3.5);
        Vec3 instance = new Vec3(2.0, 3.8, 2.1);
        Vec3 expResult = new Vec3(3.0, 3.3, 5.6);
        Vec3 result = instance.add(v);
        assertTrue(equalsVec3(result, expResult));
    }

    /**
     * Test of add method, of class Vec3.
     */
    @Test
    public void testAdd_Double_Vec() {
        System.out.println("add");
        Double a = -0.5;
        Vec v = new Vec3(4.3, -0.2, 0.2);
        Vec3 instance = new Vec3(4.2, 0.1, 4.2);
        Vec3 expResult = new Vec3(2.05, 0.2, 4.1);
        Vec3 result = instance.add(a, v);
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of negate method, of class Vec3.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Vec3 instance = new Vec3(-3.2, 5.3, -3.2);
        Vec3 expResult = new Vec3(3.2, -5.3, 3.2);
        Vec3 result = instance.negate();
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of normalize method, of class Vec3.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        Vec3 instance = new Vec3(-2.0, -4.0, 4.0);
        Vec3 expResult = new Vec3(-2.0/6, -4.0/6, 4.0/6);
        Vec3 result = instance.normalize();
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of sub method, of class Vec3.
     */
    @Test
    public void testSub_Vec() {
        System.out.println("sub");
        Vec v = new Vec3(2.8, 4.6, 5.3);
        Vec3 instance = new Vec3(3.2, -6.2, 2.3);
        Vec3 expResult = new Vec3(0.4, -10.8, -3.0);
        Vec3 result = instance.sub(v);
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of sub method, of class Vec3.
     */
    @Test
    public void testSub_Double_Vec() {
        System.out.println("sub");
        Double a = -0.5;
        Vec v = new Vec3(4.2, -2.0, 3.2);
        Vec3 instance = new Vec3(5.0, 3.0, 3.4);
        Vec3 expResult = new Vec3(7.1, 2.0, 5.0);
        Vec3 result = instance.sub(a, v);
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of scale method, of class Vec3.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Double a = -300.0;
        Vec3 instance = new Vec3(0.4, -0.5, 4.0);
        Vec3 expResult = new Vec3(-120.0, 150.0, -1200.0);
        Vec3 result = instance.scale(a);
        assertTrue(equalsVec3(expResult, result));

    }

    /**
     * Test of getDimention method, of class Vec3.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Vec3 instance = new Vec3(4.2, 5.4, 4.2);
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
        Integer i1 = 0, i2 = 1, i3 = 2;
        Vec3 instance = new Vec3(4.3, 5.43, 4.32);
        Double expResult1 = 4.3;
        Double expResult2 = 5.43;
        Double expResult3 = 4.32;
        Double result1 = instance.get(i1);
        Double result2 = instance.get(i2);
        Double result3 = instance.get(i3);
        assertTrue(equalsDouble(expResult1, result1));
        assertTrue(equalsDouble(expResult2, result2));
        assertTrue(equalsDouble(expResult3, result3));
    }

    /**
     * Test of dot method, of class Vec3.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec v = new Vec3(3.2, -4.2, 3.2);
        Vec3 instance = new Vec3(-0.5, 0.1, -3.0);
        Double expResult = -2.02-9.6;
        Double result = instance.dot(v);
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getX method, of class Vec3.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vec3 instance = new Vec3(2.3, -3.2, 4.3);
        Double expResult = 2.3;
        Double result = instance.getX();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getZ method, of class Vec3.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Vec3 instance = new Vec3(2.3, -3.2, 4.3);
        Double expResult = -3.2;
        Double result = instance.getY();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getZ method, of class Vec3.
     */
    @Test
    public void testGetZ() {
        System.out.println("getY");
        Vec3 instance = new Vec3(2.3, -3.2, 4.3);
        Double expResult = 4.3;
        Double result = instance.getZ();
        assertTrue(equalsDouble(expResult, result));
    }
    
    /**
     * Test of cross method, of class Vec3.
     */
    @Test
    public void testCross() {
        System.out.println("cross");
        Vec3 v = new Vec3(3.0, 1.0, 2.0);
        Vec3 instance = new Vec3(4.0, -1.0, 3.0);
        Vec3 expResult = new Vec3(-5.0, 1.0, 7.0);
        Vec3 result = instance.cross(v);
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of angle method, of class Vec3.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        Vec3 v = new Vec3(1.0, 0.0, 1.0);
        Vec3 instance = new Vec3(1.0, 0.0, -1.0);
        Double expResult = Math.PI/2;
        Double result = instance.angle(v);
        assertTrue(equalsDouble(expResult, result));
    }
}
