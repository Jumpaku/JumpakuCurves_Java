/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.junit.Test;
import static org.jumpaku.curves.test.TestUtils.*;
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
    public void testAdd_Vec() {
        System.out.println("add");
        Vec v = new Vec1(1.0);
        Vec1 instance = new Vec1(2.0);
        Vec1 expResult = new Vec1(3.0);
        Vec1 result = instance.add(v);
        assertTrue(equalsVec1(result, expResult));
    }

    /**
     * Test of add method, of class Vec1.
     */
    @Test
    public void testAdd_Double_Vec() {
        System.out.println("add");
        Double a = -0.5;
        Vec v = new Vec1(4.3);
        Vec1 instance = new Vec1(4.2);
        Vec1 expResult = new Vec1(2.05);
        Vec1 result = instance.add(a, v);
        assertTrue(equalsVec1(expResult, result));
    }

    /**
     * Test of negate method, of class Vec1.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Vec1 instance = new Vec1(-3.2);
        Vec1 expResult = new Vec1(3.2);
        Vec1 result = instance.negate();
        assertTrue(equalsVec1(expResult, result));
    }

    /**
     * Test of normalize method, of class Vec1.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        Vec1 instance = new Vec1(-1.5);
        Vec1 expResult = new Vec1(-1.0);
        Vec1 result = instance.normalize();
        assertTrue(equalsVec1(expResult, result));
    }

    /**
     * Test of sub method, of class Vec1.
     */
    @Test
    public void testSub_Vec() {
        System.out.println("sub");
        Vec v = new Vec1(2.8);
        Vec1 instance = new Vec1(3.2);
        Vec1 expResult = new Vec1(0.4);
        Vec1 result = instance.sub(v);
        assertTrue(equalsVec1(expResult, result));
    }

    /**
     * Test of sub method, of class Vec1.
     */
    @Test
    public void testSub_Double_Vec() {
        System.out.println("sub");
        Double a = -0.5;
        Vec v = new Vec1(4.2);
        Vec1 instance = new Vec1(5.0);
        Vec1 expResult = new Vec1(7.1);
        Vec1 result = instance.sub(a, v);
        assertTrue(equalsVec1(expResult, result));
    }

    /**
     * Test of scale method, of class Vec1.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Double a = -300.0;
        Vec1 instance = new Vec1(0.4);
        Vec1 expResult = new Vec1(-120.0);
        Vec1 result = instance.scale(a);
        assertTrue(equalsVec1(expResult, result));

    }

    /**
     * Test of getDimention method, of class Vec1.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Vec1 instance = new Vec1(4.2);
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
        Vec1 instance = new Vec1(4.3);
        Double expResult = 4.3;
        Double result = instance.get(i);
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of dot method, of class Vec1.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec v = new Vec1(3.2);
        Vec1 instance = new Vec1(-0.5);
        Double expResult = -1.6;
        Double result = instance.dot(v);
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getX method, of class Vec1.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vec1 instance = new Vec1(2.3);
        Double expResult = 2.3;
        Double result = instance.getX();
        assertTrue(equalsDouble(expResult, result));
    }
}
