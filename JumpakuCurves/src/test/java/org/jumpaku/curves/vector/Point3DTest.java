/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import org.jumpaku.old.curves.vector.Point3D;
import org.jumpaku.old.curves.vector.Vec;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Vec3;
import static org.jumpaku.curves.test.TestUtils.*;
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
     * Test of getVec method, of class Point3D.
     */
    @Test
    public void testGetVec() {
        System.out.println("getVec");
        Point3D instance = new Point3D(2.9, -3.1, 3.2);
        Vec3 expResult = new Vec3(2.9, -3.1, 3.2);
        Vec3 result = instance.getVec();
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of getX method, of class Point3D.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Point3D instance = new Point3D(3.0, -3.2, 3.2);
        Double expResult = 3.0;
        Double result = instance.getX();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getY method, of class Point3D.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Point3D instance = new Point3D(3.0, -3.2, 3.4);
        Double expResult = -3.2;
        Double result = instance.getY();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getY method, of class Point3D.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        Point3D instance = new Point3D(3.0, -3.2, 3.2);
        Double expResult = 3.2;
        Double result = instance.getZ();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of from method, of class Point3D.
     */
    @Test
    public void testFrom() {
        System.out.println("differenceFrom");
        Point p = new Point3D(1.2, -0.2, 0.5);
        Point3D instance = new Point3D(-3.2, -3.2, 3.5);
        Vec3 expResult = new Vec3(-4.4, -3.0, 3.0);
        Vec3 result = instance.from(p);
        assertTrue(equalsVec3(expResult, result));
    }
    
    /**
     * Test of to method, of class Point1D.
     */
    @Test
    public void testTo() {
        System.out.println("to");
        Point p = new Point3D(1.2, -0.2, 0.5);
        Point3D instance = new Point3D(-3.2, -3.2, 3.5);
        Vec3 expResult = new Vec3(4.4, 3.0, -3.0);
        Vec3 result = instance.to(p);
        assertTrue(equalsVec3(expResult, result));
    }

    /**
     * Test of divide method, of class Point3D.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        Double t = 0.25;
        Point p = new Point3D(2.4, 3.2, 1.0);
        Point3D instance = new Point3D(3.2, 2.4, 0.0);
        Point3D expResult = new Point3D(3.0, 2.6, 0.25);
        Point3D result = instance.divide(t, p);
        assertTrue(equalsVec3(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of move method, of class Point3D.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Vec v = Vec.of(3.2, -4.2, 3.5);
        Point3D instance = new Point3D(-0.2, -0.8, -0.5);
        Point3D expResult = new Point3D(3.0, -5.0, 3.0);
        Point3D result = instance.move(v);
        assertTrue(equalsVec3(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of normal method, of class Point3D.
     */
    @Test
    public void testNormal() {
        System.out.println("normal");
        Point3D p1 = new Point3D(1.0, 1.3, 2.4);
        Point3D p2 = new Point3D(1.0, 1.4, -3.2);
        Point3D p3 = new Point3D(1.0, -3.2, -2.1);
        Vec3 expResult = new Vec3(-1.0, 0.0, 0.0);
        Vec3 result = Point3D.normal(p1, p2, p3);
        assertTrue(equalsVec3(expResult, result));
    }
}
