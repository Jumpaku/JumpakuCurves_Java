/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Arrays;
import static org.jumpaku.curves.vector.TestUtils.*;
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
        for(int i = 0; i < 3; ++i){
            assertTrue(equalsDouble(expResult.get(i), result.get(i)));
        }
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
        for(int i = 0; i < 3; ++i){
            assertTrue(equalsDouble(expResult.get(i), result.get(i))); 
        }
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
        for(int i = 0; i < 4; ++i){
            assertTrue(equalsDouble(expResult.get(i), result.get(i)));
        }
    }

    /**
     * Test of move method, of class Point.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Vec v = null;
        Point instance = new PointImpl();
        Point expResult = null;
        Point result = instance.move(v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of divide method, of class Point.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        Double t = null;
        Point p = null;
        Point instance = new PointImpl();
        Point expResult = null;
        Point result = instance.divide(t, p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDimention method, of class Point.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Point instance = new PointImpl();
        Integer expResult = null;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class Point.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Integer i = null;
        Point instance = new PointImpl();
        Double expResult = null;
        Double result = instance.get(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of differenceFrom method, of class Point.
     */
    @Test
    public void testDifferenceFrom() {
        System.out.println("differenceFrom");
        Point p = null;
        Point instance = new PointImpl();
        Vec expResult = null;
        Vec result = instance.differenceFrom(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of distance method, of class Point.
     */
    @Test
    public void testDistance() {
        System.out.println("distance");
        Point p = null;
        Point instance = new PointImpl();
        Double expResult = null;
        Double result = instance.distance(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of distanceSquare method, of class Point.
     */
    @Test
    public void testDistanceSquare() {
        System.out.println("distanceSquare");
        Point p = null;
        Point instance = new PointImpl();
        Double expResult = null;
        Double result = instance.distanceSquare(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals_Point_Double() {
        System.out.println("equals");
        Point p = null;
        Double eps = null;
        Point instance = new PointImpl();
        Boolean expResult = null;
        Boolean result = instance.equals(p, eps);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals_Point_Integer() {
        System.out.println("equals");
        Point p = null;
        Integer ulp = null;
        Point instance = new PointImpl();
        Boolean expResult = null;
        Boolean result = instance.equals(p, ulp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
