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
public class Point1DTest {
    
    public Point1DTest() {
    }

 
    private static boolean checkEquals(Point p1, Point p2){
        return checkEqualsDouble(p1.get(0), p2.get(0));
    }
    
    private static boolean checkEquals(Vec v1, Vec v2){
        return checkEqualsDouble(v1.get(0), v2.get(0));
    }
        
    private static boolean checkEqualsDouble(Double a, Double b){
        return Precision.equals(a, b, 1.0e-10);
    }    

    /**
     * Test of getVec method, of class Point1D.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");

    }

    /**
     * Test of getVec method, of class Point1D.
     */
    @Test
    public void testGetVec() {
        System.out.println("getVec");
        Point1D instance = new Point1D(2.9);
        Vec expResult = new Vec1(2.9);
        Vec result = instance.getVec();
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of getVec1 method, of class Point1D.
     */
    @Test
    public void testGetVec1() {
        System.out.println("getVec1");
        Point1D instance = null;
        Vec1 expResult = null;
        Vec1 result = instance.getVec1();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getX method, of class Point1D.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Point1D instance = null;
        Double expResult = null;
        Double result = instance.getX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
