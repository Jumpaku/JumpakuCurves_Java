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
     * Test of getVector method, of class Point1D.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        Point1D instance = new Point1D(2.9);
        Vec expResult = new Vec1(2.9);
        Vec result = instance.getVector();
        assertTrue(checkEquals(expResult, result));
    }
}
