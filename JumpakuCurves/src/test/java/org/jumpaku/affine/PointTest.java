/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class PointTest {
    
    public PointTest() {
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertEquals(false, Point.equals(Point.of(1.0004,2.0,-0.00512), Point.of(1.0,2.0,-0.00512), 1.0e-10));
        assertEquals(true, Point.equals(Point.of(1.0,2.0,-0.00512), Point.of(1.0,2.0,-0.00512), 1.0e-10));
    }

    /**
     * Test of of method, of class Point.
     */
    @Test
    public void testOf_Vector() {
        System.out.println("of");
        Point p = Point.of(Vector.of(1.0004,2.0,-0.00512));
        assertEquals(1.0004, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(-0.00512, p.getZ(), 1.0e-10);
    }

    /**
     * Test of of method, of class Point.
     */
    @Test
    public void testOf_3args() {
        System.out.println("of");
        Point p = Point.of(1.0004,2.0,-0.00512);
        assertEquals(1.0004, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(-0.00512, p.getZ(), 1.0e-10);
    }

    /**
     * Test of of method, of class Point.
     */
    @Test
    public void testOf_Double_Double() {
        System.out.println("of");
        Point p = Point.of(1.0004,2.0);
        assertEquals(1.0004, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
    }

    /**
     * Test of of method, of class Point.
     */
    @Test
    public void testOf_Double() {
        System.out.println("of");
        Point p = Point.of(1.0004);
        assertEquals(1.0004, p.getX(), 1.0e-10);
        assertEquals(0.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
    }

    /**
     * Test of toVector method, of class Point.
     */
    @Test
    public void testToVector() {
        System.out.println("toVector");
        Point p = Point.of(1.0004,2.0,-0.00512);
        assertTrue(Vector.equals(Vector.of(1.0004,2.0,-0.00512), p.toVector(), 1.0e-10));
    }

    /**
     * Test of move method, of class Point.
     */
    @Test
    public void testMove() {
        System.out.println("translate");
        assertTrue(Point.equals(Point.of(4.8,6.2,4.7), Point.of(1.4,2.0,-0.7).move(Vector.of(3.4, 4.2, 5.4)), 1.0e-10));
    }

    /**
     * Test of getX method, of class Point.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Point p = Point.of(1.0004,2.0,-0.00512);
        assertEquals(1.0004, p.getX(), 1.0e-10);
    }

    /**
     * Test of getY method, of class Point.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Point p = Point.of(1.0004,2.0,-0.00512);
        assertEquals(2.0, p.getY(), 1.0e-10);
    }

    /**
     * Test of getZ method, of class Point.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        Point p = Point.of(1.0004,2.0,-0.00512);
        assertEquals(-0.00512, p.getZ(), 1.0e-10);
    }

    /**
     * Test of diff method, of class Point.
     */
    @Test
    public void testDiff() {
        System.out.println("diff");
        assertTrue(Vector.equals(Vector.of(-1.1,7.3,-5.4), Point.of(1.4,2.0,-5.1).diff(Point.of(2.5, -5.3, 0.3)), 1.0e-10));
    }

    /**
     * Test of dist method, of class Point.
     */
    @Test
    public void testDist_Point() {
        System.out.println("dist");
        assertEquals(3.0, Point.of(1.0, -2.0, 2.0).dist(Point.of(2.0, -4.0, 0.0)), 1.0e-10);
    }

    /**
     * Test of distSquare method, of class Point.
     */
    @Test
    public void testDistSquare() {
        System.out.println("distSquare");
        assertEquals(9.0, Point.of(1.0, -2.0, 2.0).distSquare(Point.of(2.0, -4.0, 0.0)), 1.0e-10);
    }

    /**
     * Test of divide method, of class Point.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        assertTrue(Point.equals(Point.of(1.3, -2.6, 1.4), Point.of(1.0, -2.0, 2.0).divide(0.3, Point.of(2.0, -4.0, 0.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(0.0, 0.0, 4.0), Point.of(1.0, -2.0, 2.0).divide(-1.0, Point.of(2.0, -4.0, 0.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(3.0, -6.0, -2.0), Point.of(1.0, -2.0, 2.0).divide(2.0, Point.of(2.0, -4.0, 0.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.0, -2.0, 2.0), Point.of(1.0, -2.0, 2.0).divide(0.0, Point.of(2.0, -4.0, 0.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(2.0, -4.0, 0.0), Point.of(1.0, -2.0, 2.0).divide(1.0, Point.of(2.0, -4.0, 0.0)), 1.0e-10));
    }

    /**
     * Test of area method, of class Point.
     */
    @Test
    public void testArea() {
        System.out.println("area");
        assertEquals(7.5, Point.of(1.0, 1.0, -1.0).area(Point.of(-3.0, 1.0, 2.0), Point.of(1.0, 4.0, -1.0)), 1.0e-10);
    }

    /**
     * Test of area method, of class Point.
     */
    @Test
    public void testVolume() {
        System.out.println("area");
        assertEquals(1.0/3.0, Point.of(0.0, 0.0, 0.0).volume(Point.of(1.0, 1.0, 0.0), Point.of(-1.0, 1.0, 0.0), Point.of(1.0, 1.0, -1.0)), 1.0e-10);
    }

    /**
     * Test of normal method, of class Point.
     */
    @Test
    public void testNormal() {
        System.out.println("normal");
        assertTrue(Vector.equals(Vector.of(0.0,1.0,0.0), Point.of(1.0, 1.0, 0.0).normal(Point.of(-1.0, 1.0, 0.0), Point.of(0.0, 1.0, 1.0)), 1.0e-10));
    }
    
    @Test
    public void testTransform(){
        System.out.println("transform");
        assertTrue(Point.equals(Point.of(1.0, 3.0, -0.5), Point.of(3.3, -2.4, -1.0).transform(
                Transform.translation(Vector.of(-2.3, 5.4, 0.5))), 1.0e-10));
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0), Point.of(1.0, 1.0, -1.0).transform(
                Transform.rotation(Vector.of(1.0, 1.0, 1.0), Math.PI*2.0/3.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(6.9, -10.8, -0.5), Point.of(3.0, -2.0, -1.0).transform(
                Transform.scaling(2.3, 5.4, 0.5)), 1.0e-10));
    }
    
    @Test
    public void testToJson(){
        System.out.println("toJson");
        JsonPoint instance = new JsonPoint();
        assertTrue(Point.equals(Point.of(1.23, 4.56, -7.89), instance.fromJson(Point.toJson(Point.of(1.23, 4.56, -7.89))).get(), 1.0e-10));
    }

    /**
     * Test of fromJson method, of class Point.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonPoint instance = new JsonPoint();
        assertTrue(Point.equals(Point.of(1.23, 4.56, -7.89), instance.fromJson("{x:1.23, y:4.56, z:-7.89}").get(), 1.0e-10));
    }

    /**
     * Test of dist method, of class Point.
     */
    @Test
    public void testDist_Point_Point() {
        System.out.println("dist");
        Point a = Point.of(1.0, 0.0, -1.0);
        Point b = Point.of(0.0, 1.0, 1.0);
        Point instance = Point.of(1.0, 1.0, 0.0);
        assertEquals(Math.pow(2, 0.5)/2, instance.dist(a, b), 1.0e-10);
    }
}
