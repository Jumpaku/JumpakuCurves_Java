/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import static org.jumpaku.curves.test.TestUtils.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class Point1DTest {
    
    public Point1DTest() {
    }
    
    /**
     * Test of getVec method, of class Point1D.
     */
    @Test
    public void testGetVec() {
        System.out.println("getVec");
        Point1D instance = new Point1D(2.9);
        Vec1 expResult = new Vec1(2.9);
        Vec1 result = instance.getVec();
        assertTrue(equalsVec1(expResult, result));
    }

    /**
     * Test of getX method, of class Point1D.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Point1D instance = new Point1D(3.0);
        Double expResult = 3.0;
        Double result = instance.getX();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of differenceFrom method, of class Point1D.
     */
    @Test
    public void testDifferenceFrom() {
        System.out.println("differenceFrom");
        Point p = new Point1D(1.2);
        Point1D instance = new Point1D(-3.2);
        Vec1 expResult = new Vec1(-4.4);
        Vec1 result = instance.differenceFrom(p);
        assertTrue(equalsVec1(expResult, result));
    }

    /**
     * Test of divide method, of class Point1D.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        Double t = 0.25;
        Point p = new Point1D(2.4);
        Point1D instance = new Point1D(3.2);
        Point1D expResult = new Point1D(3.0);
        Point1D result = instance.divide(t, p);
        assertTrue(equalsVec1(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of move method, of class Point1D.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Vec v = Vec.of(3.2);
        Point1D instance = new Point1D(-0.2);
        Point1D expResult = new Point1D(3.0);
        Point1D result = instance.move(v);
        assertTrue(equalsVec1(expResult.getVec(), result.getVec()));
    }
}
