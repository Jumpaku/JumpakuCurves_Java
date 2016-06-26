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
public class Point3DTest {
    
    public Point3DTest() {
    }

    /**
     * Test of toPoint method, of class Point3D.
     */
    @Test
    public void testToPoint() {
        System.out.println("toPoint");
        Vec v = new Vec3(1.0, -0.0, 1000.0);
        Point3D instance = new Point3D(4.2, 9.0, 4.9);
        Point expResult = new Point3D(1.0, -0.0, 1000.0);
        Point result = instance.toPoint(v);
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of getVector method, of class Point3D.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        Point3D instance = new Point3D(2.8, -0.0001, 10000000.2);
        Vec expResult = new Vec3(2.8, -0.0001, 10000000.2);
        Vec result = instance.getVector();
        assert(checkEquals(expResult, result));
    }
 
    private static boolean checkEquals(Point p1, Point p2){
        return checkEqualsDouble(p1.get(0), p2.get(0)) && checkEqualsDouble(p1.get(1), p2.get(1)) && checkEqualsDouble(p1.get(2), p2.get(2));
    }
    
    private static boolean checkEquals(Vec v1, Vec v2){
        return checkEqualsDouble(v1.get(0), v2.get(0)) && checkEqualsDouble(v1.get(0), v2.get(0)) && checkEqualsDouble(v1.get(2), v2.get(2));
    }
        
    private static boolean checkEqualsDouble(Double a, Double b){
        return Precision.equals(a, b, 1.0e-10);
    }
}
