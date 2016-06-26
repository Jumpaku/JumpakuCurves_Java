/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.Precision;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class Vec2Test {
    
    public Vec2Test() {
    }

    /**
     * Test of getVector1d method, of class Vec2.
     */
    @Test
    public void testGetVector2d() {
        System.out.println("getVector2d");
        Vec2 instance = new Vec2(2.8, 4.8);
        Vector2D expResult = new Vector2D(2.8, 4.8);
        Vector2D result = instance.getVector2d();
        assertTrue(checkEqualsDouble(result.getX(), expResult.getX()) && checkEqualsDouble(result.getY(), expResult.getY()));
    }

    /**
     * Test of add method, of class Vec2.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Vec v = new Vec2(3.5, -3.4);
        Vec2 instance = new Vec2(-0.5, 3.5);
        Vec2 expResult = new Vec2(3.0, 0.1);
        Vec2 result = (Vec2)instance.add(v);
        assertTrue(chechEquals(result, expResult));
    }

    /**
     * Test of scale method, of class Vec2.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Double a = 0.2;
        Vec2 instance = new Vec2(0.3, -0.09);
        Vec expResult = new Vec2(0.06, -0.018);
        Vec result = instance.scale(a);
        assertTrue(chechEquals(result, expResult));
    }

    /**
     * Test of getDimention method, of class Vec2.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Vec2 instance = new Vec2(3.0, 4.2);
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
        Integer i0 = 0;
        Integer i1 = 1;
        Vec2 instance = new Vec2(5.9, 4.3);
        Double expResult0 = 5.9;
        Double expResult1 = 4.3;
        Double result0 = instance.get(i0);
        Double result1 = instance.get(i1);
        assertTrue(checkEqualsDouble(result0, expResult0));
        assertTrue(checkEqualsDouble(result1, expResult1));
    }

    /**
     * Test of dot method, of class Vec2.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec v = new Vec2(3.0, -2.8);
        Vec2 instance = new Vec2(-0.3, -0.5);
        Double expResult = 0.5;
        Double result = instance.dot(v);
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of getX method, of class Vec2.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vec2 instance = new Vec2(6.8, 4.4);
        Double expResult = 6.8;
        Double result = instance.getX();
        assertTrue(checkEqualsDouble(result, expResult));
    }
    
    /**
     * Test of getX method, of class Vec2.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Vec2 instance = new Vec2(6.8, 4.4);
        Double expResult = 4.4;
        Double result = instance.getY();
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of equals method, of class Vec2.
     */
    @Test
    public void testEquals_Vec_Double() {
        System.out.println("equals");
        Vec v = new Vec2(1.0, 1000000000000.1);
        Double eps = 1.0e-10;
        Vec2 instance = new Vec2(1.00000000001, 1000000000000.0);
        Boolean expResult = false;
        Boolean result = instance.equals(v, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Vec2.
     */
    @Test
    public void testEquals_Vec_Integer() {
        System.out.println("equals");
        Vec v = new Vec2(-0.0, 1000000000.001);
        Integer ulp = 40000;
        Vec2 instance = new Vec2(0.0, 1000000000.0);
        Boolean expResult = true;
        Boolean result = instance.equals(v, ulp);
        assertEquals(expResult, result);
    }
 
    private static boolean chechEquals(Vec v1, Vec v2){
        return checkEqualsDouble(v1.get(0), v1.get(0)) && checkEqualsDouble(v1.get(1), v2.get(1));
    }
    private static boolean checkEqualsDouble(Double a, Double b){
        return Precision.equals(a, b, 1.0e-10);
    }
}
