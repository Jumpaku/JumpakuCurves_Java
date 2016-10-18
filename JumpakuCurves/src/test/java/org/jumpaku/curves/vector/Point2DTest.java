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
public class Point2DTest {
    
    public Point2DTest() {
    }
    
    /**
     * Test of getVec method, of class Point2D.
     */
    @Test
    public void testGetVec() {
        System.out.println("getVec");
        Point2D instance = new Point2D(2.9, -3.1);
        Vec2 expResult = new Vec2(2.9, -3.1);
        Vec2 result = instance.getVec();
        assertTrue(equalsVec2(expResult, result));
    }

    /**
     * Test of getX method, of class Point2D.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Point2D instance = new Point2D(3.0, -3.2);
        Double expResult = 3.0;
        Double result = instance.getX();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getY method, of class Point2D.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Point2D instance = new Point2D(3.0, -3.2);
        Double expResult = -3.2;
        Double result = instance.getY();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of from method, of class Point2D.
     */
    @Test
    public void testFrom() {
        System.out.println("differenceFrom");
        Point p = new Point2D(1.2, -0.2);
        Point2D instance = new Point2D(-3.2, -3.2);
        Vec2 expResult = new Vec2(-4.4, -3.0);
        Vec2 result = instance.from(p);
        assertTrue(equalsVec2(expResult, result));
    }
    
    /**
     * Test of to method, of class Point1D.
     */
    @Test
    public void testTo() {
        System.out.println("to");
        Point p = new Point2D(1.2, -0.2);
        Point2D instance = new Point2D(-3.2, -3.2);
        Vec2 expResult = new Vec2(4.4, 3.0);
        Vec2 result = instance.to(p);
        assertTrue(equalsVec2(expResult, result));
    }
    
    /**
     * Test of divide method, of class Point2D.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        Double t = 0.25;
        Point p = new Point2D(2.4, 3.2);
        Point2D instance = new Point2D(3.2, 2.4);
        Point2D expResult = new Point2D(3.0, 2.6);
        Point2D result = instance.divide(t, p);
        assertTrue(equalsVec2(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of move method, of class Point2D.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Vec v = Vec.of(3.2, -4.2);
        Point2D instance = new Point2D(-0.2, -0.8);
        Point2D expResult = new Point2D(3.0, -5.0);
        Point2D result = instance.move(v);
        assertTrue(equalsVec2(expResult.getVec(), result.getVec()));
    }
}
