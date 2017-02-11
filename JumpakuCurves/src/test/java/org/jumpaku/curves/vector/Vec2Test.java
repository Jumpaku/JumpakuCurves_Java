/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.jumpaku.old.curves.vector.Vec;
import org.jumpaku.old.curves.vector.Vec2;
import org.junit.Test;
import static org.jumpaku.curves.test.TestUtils.*;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class Vec2Test {
    
    public Vec2Test() {
    }

    /**
     * Test of add method, of class Vec2.
     */
    @Test
    public void testAdd_Vec() {
        System.out.println("add");
        Vec v = new Vec2(1.0, -0.5);
        Vec2 instance = new Vec2(2.0, 3.8);
        Vec2 expResult = new Vec2(3.0, 3.3);
        Vec2 result = instance.add(v);
        assertTrue(equalsVec2(result, expResult));
    }

    /**
     * Test of add method, of class Vec2.
     */
    @Test
    public void testAdd_Double_Vec() {
        System.out.println("add");
        Double a = -0.5;
        Vec v = new Vec2(4.3, -0.2);
        Vec2 instance = new Vec2(4.2, 0.1);
        Vec2 expResult = new Vec2(2.05, 0.2);
        Vec2 result = instance.add(a, v);
        assertTrue(equalsVec2(expResult, result));
    }

    /**
     * Test of negate method, of class Vec2.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Vec2 instance = new Vec2(-3.2, 5.3);
        Vec2 expResult = new Vec2(3.2, -5.3);
        Vec2 result = instance.negate();
        assertTrue(equalsVec2(expResult, result));
    }

    /**
     * Test of normalize method, of class Vec2.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        Vec2 instance = new Vec2(-3.0, 4.0);
        Vec2 expResult = new Vec2(-0.6, 0.8);
        Vec2 result = instance.normalize();
        assertTrue(equalsVec2(expResult, result));
    }

    /**
     * Test of sub method, of class Vec2.
     */
    @Test
    public void testSub_Vec() {
        System.out.println("sub");
        Vec v = new Vec2(2.8, 4.6);
        Vec2 instance = new Vec2(3.2, -6.2);
        Vec2 expResult = new Vec2(0.4, -10.8);
        Vec2 result = instance.sub(v);
        assertTrue(equalsVec2(expResult, result));
    }

    /**
     * Test of sub method, of class Vec2.
     */
    @Test
    public void testSub_Double_Vec() {
        System.out.println("sub");
        Double a = -0.5;
        Vec v = new Vec2(4.2, -2.0);
        Vec2 instance = new Vec2(5.0, 3.0);
        Vec2 expResult = new Vec2(7.1, 2.0);
        Vec2 result = instance.sub(a, v);
        assertTrue(equalsVec2(expResult, result));
    }

    /**
     * Test of scale method, of class Vec2.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Double a = -300.0;
        Vec2 instance = new Vec2(0.4, -0.5);
        Vec2 expResult = new Vec2(-120.0, 150.0);
        Vec2 result = instance.scale(a);
        assertTrue(equalsVec2(expResult, result));

    }

    /**
     * Test of getDimention method, of class Vec2.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Vec2 instance = new Vec2(4.2, 5.4);
        Integer expResult = 2;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Vec2.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Integer i1 = 0, i2 = 1;
        Vec2 instance = new Vec2(4.3, 5.43);
        Double expResult1 = 4.3;
        Double expResult2 = 5.43;
        Double result1 = instance.get(i1);
        Double result2 = instance.get(i2);
        assertTrue(equalsDouble(expResult1, result1));
        assertTrue(equalsDouble(expResult2, result2));
    }

    /**
     * Test of dot method, of class Vec2.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec v = new Vec2(3.2, -4.2);
        Vec2 instance = new Vec2(-0.5, 0.1);
        Double expResult = -2.02;
        Double result = instance.dot(v);
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getX method, of class Vec2.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vec2 instance = new Vec2(2.3, -3.2);
        Double expResult = 2.3;
        Double result = instance.getX();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getX method, of class Vec2.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Vec2 instance = new Vec2(2.3, -3.2);
        Double expResult = -3.2;
        Double result = instance.getY();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of cross method, of class Vec2.
     */
    @Test
    public void testCross() {
        System.out.println("cross");
        Vec2 v = new Vec2(3.0,1.0);
        Vec2 instance = new Vec2(4.0,-1.0);
        Double expResult = 7.0;
        Double result = instance.cross(v);
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of angle method, of class Vec2.
     */
    @Test
    public void testAngle_Vec2() {
        System.out.println("angle");
        Vec2 v = new Vec2(1.0, -1.0);
        Vec2 instance = new Vec2(-1.0,-1.0);
        Double expResult = Math.PI/2;
        Double result = instance.angle(v);
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of angle method, of class Vec2.
     */
    @Test
    public void testAngle_Vec2_Vec2() {
        System.out.println("angle");
        Vec2 from = new Vec2(1.0,1.0);
        Vec2 to = new Vec2(-1.0,1.0);
        Double expResult = Math.PI/2;
        Double result = Vec2.angle(from, to);
        assertTrue(equalsDouble(expResult, result));
    }
}
