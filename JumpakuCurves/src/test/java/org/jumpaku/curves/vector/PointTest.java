/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Arrays;
import static org.jumpaku.curves.test.TestUtils.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class PointTest {
    
    public PointTest() {
    }

    /**
     * Test of origin method, of class Point.
     */
    @Test
    public void testOrigin() {
        System.out.println("origin");
        Integer dimention = 10;
        Point result = Point.origin(dimention);
        for(int i = 0; i < dimention; ++i){
            assertTrue(equalsDouble(0.0, result.get(i)));
        }
    }

    /**
     * Test of of method, of class Point.
     */
    @Test
    public void testOf_Vec() {
        System.out.println("of");
        Vec v = new Vec3(2.3, 4.5, 6.7);
        Point expResult = new Point3D(2.3, 4.5, 6.7);
        Point result = Point.of(v);
        assertTrue(equalsVec(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of of method, of class Point.
     */
    @Test
    public void testOf_DoubleArr() {
        System.out.println("of");
        Double[] elements = new Double[]{2.3, 4.5, 6.7};
        Point expResult = new Point3D(2.3, 4.5, 6.7);
        Point result = Point.of(elements);
        assertTrue(equalsVec(expResult.getVec(), result.getVec())); 
    }

    /**
     * Test of affineCombination method, of class Point.
     */
    @Test
    public void testAffineCombination() {
        System.out.println("affineCombination");
        Iterable<Double> cofficients = Arrays.asList(0.3, 0.4, 0.2, 0.1);
        Iterable<Point> points = Arrays.asList(Point.of(3.0), Point.of(4.0), Point.of(5.0), Point.of(1.0));
        Point expResult = Point.of(3.6);
        Point result = Point.affineCombination(cofficients, points);
        assertTrue(equalsDouble(expResult.get(0), result.get(0)));
    }

    /**
     * Test of getVec method, of class Point.
     */
    @Test
    public void testGetVec() {
        System.out.println("getVec");
        Point instance = Point.of(1.2, 3.4, -4.5, -5.6);
        Vec expResult = Vec.of(1.2, 3.4, -4.5, -5.6);
        Vec result = instance.getVec();
        assertTrue(equalsVec(expResult, result));
    }

    /**
     * Test of move method, of class Point.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Vec v = Vec.of(2.1, 3.2);
        Point instance = Point.of(-2.3, 7.4);
        Point expResult = Point.of(-0.2, 10.6);
        Point result = instance.move(v);
        assertTrue(equalsVec(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of divide method, of class Point.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        Double t = 0.333333333333333333333;
        Point p = Point.of(1.0, 2.0, 3.0, 4.0);
        Point instance = Point.of(5.0, 4.0, 3.0, 2.0);
        Point expResult = Point.of(11.0/3, 10.0/3, 3.0, 8.0/3);
        Point result = instance.divide(t, p);
        assertTrue(equalsVec(expResult.getVec(), result.getVec()));
    }

    /**
     * Test of getDimention method, of class Point.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Point instance = Point.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
        Integer expResult = 9;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Point.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Point instance = Point.of(0.0, 1.0, 2.0, 3.0, 4.0, 5.0);
        for(Integer i = 0; i < 6; ++i){
            Double expResult = i.doubleValue();
            Double result = instance.get(i);
            assertTrue(equalsDouble(expResult, result));
        }
    }
    
    /**
     * Test of from method, of class Point.
     */
    @Test
    public void testFrom() {
        System.out.println("from");
        Point p = Point.of(1.2, 3.4, 5.4, 6.5);
        Point instance = Point.of(1.0, 2.0, 3.0, 4.0);
        Vec expResult = Vec.of(-0.2, -1.4, -2.4, -2.5);
        Vec result = instance.from(p);
        assertTrue(equalsVec(expResult, result));
    }

    /** Test of from method, of class Point.
     */
    @Test
    public void testTo() {
        System.out.println("to");
        Point p = Point.of(1.2, 3.4, 5.4, 6.5);
        Point instance = Point.of(1.0, 2.0, 3.0, 4.0);
        Vec expResult = Vec.of(0.2, 1.4, 2.4, 2.5);
        Vec result = instance.to(p);
        assertTrue(equalsVec(expResult, result));
    }
    /**
     * Test of distance method, of class Point.
     */
    @Test
    public void testDistance() {
        System.out.println("distance");
        Point p = Point.origin(3);
        Point instance = Point.of(2.0, 2.0, 2.0);
        Double expResult = Math.sqrt(3.0)*2;
        Double result = instance.distance(p);
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of distanceSquare method, of class Point.
     */
    @Test
    public void testDistanceSquare() {
        System.out.println("distanceSquare");
        Point p = Point.origin(3);
        Point instance = Point.of(2.0, 2.0, 2.0);
        Double expResult = 12.0;
        Double result = instance.distanceSquare(p);
        assertTrue(equalsDouble(expResult, result));
    }
}
