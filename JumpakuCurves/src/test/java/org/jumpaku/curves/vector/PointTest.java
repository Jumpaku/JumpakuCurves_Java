/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.util.Arrays;
import org.apache.commons.math3.util.Precision;
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
     * Test of getDimention method, of class Point.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Point instance = new PointImpl(1,2,3,4);
        Integer expResult = 4;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of create method, of class Point.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Vec v = new VecImpl(1,2,3,4);
        Point expResult = new PointImpl(1,2,3,4);
        Point result = Point.create(v);
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of affineCombination method, of class Point.
     */
    @Test
    public void testAffineCombination() {
        System.out.println("affineCombination");
        Iterable<Double> cofficients = Arrays.asList(0.25,0.25,0.5);
        Iterable<Point> points = Arrays.asList(new PointImpl(1,2,3,4), new PointImpl(-3,-4,-5,-6), new PointImpl(10, 5, 0, -5));
        Point expResult = new PointImpl(4.5, 2, -0.5, -3);
        Point result = Point.affineCombination(cofficients, points);
        assertTrue(checkEquals(expResult, result));
    }
    
    /**
     * Test of origin method, of class Point.
     */
    @Test
    public void testOrigin() {
        System.out.println("origin");
        Integer dimention = 4;
        Point expResult = new PointImpl(0, 0, 0, 0);
        Point result = Point.origin(dimention);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of getVector method, of class Point.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        Point instance = new PointImpl(1,2,3,-4);
        Vec expResult = new VecImpl(1,2,3,-4);
        Vec result = instance.getVector();
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of move method, of class Point.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Vec v = new VecImpl(2,3,0.5,-0.01);
        Point instance = new PointImpl(1,2,3,4);
        Point expResult = new PointImpl(3, 5, 3.5, 3.99);
        Point result = instance.move(v);
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of divide method, of class Point.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        Double t = 0.2;
        Point p = new PointImpl(1,2,3,4);
        Point instance = new PointImpl(0,-3,-2,-6);
        Point expResult = new PointImpl(0.2, -2, -1, -4);
        Point result = instance.divide(t, p);
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of getDimention method, of class Point.
     */
    @Test
    public void testDimention() {
        System.out.println("dimention");
        Point instance = new PointImpl(3,4,5,-0);
        Integer expResult = 4;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Point.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Point instance = new PointImpl(1,2,3,-100);
        Double expResult0 = 1.0;
        Double expResult1 = 2.0;
        Double expResult2 = 3.0;
        Double expResult3 = -100.0;
        Double result0 = instance.get(0);
        Double result1 = instance.get(1);
        Double result2 = instance.get(2);
        Double result3 = instance.get(3);
        assertTrue(checkEqualsDouble(expResult0, result0));
        assertTrue(checkEqualsDouble(expResult1, result1));
        assertTrue(checkEqualsDouble(expResult2, result2));
        assertTrue(checkEqualsDouble(expResult3, result3));
    }

    /**
     * Test of difference method, of class Point.
     */
    @Test
    public void testDifference() {
        System.out.println("difference");
        Point p = new PointImpl(1,2,-3,-4);
        Point instance = new PointImpl(-1,-2,-3,-0.5);
        Vec expResult = new VecImpl(-2, -4, 0, 3.5);
        Vec result = instance.difference(p);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of distance method, of class Point.
     */
    @Test
    public void testDistance() {
        System.out.println("distance");
        Point p = new PointImpl(5,4,-2,-1);
        Point instance = new PointImpl(4,5,-3,0);
        Double expResult = 2.0;
        Double result = instance.distance(p);
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of deistanceSquare method, of class Point.
     */
    @Test
    public void testDeistanceSquare() {
        System.out.println("deistanceSquare");
        Point p = new PointImpl(0.1,0.2,0.3,-0.4);
        Point instance = new PointImpl(-0.2,-0.4,-0.6,0.1);
        Double expResult = 0.09+0.36+0.81+0.25;
        Double result = instance.deistanceSquare(p);
        assertTrue(checkEqualsDouble(result, expResult));
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals_Point_Double() {
        System.out.println("equals");
        Point p = new PointImpl(9,8,100,2.700000000011);
        Double eps = 1.0e-10;
        Point instance = new PointImpl(9,8,100,2.70000000001);
        Boolean expResult = true;
        Boolean result = instance.equals(p, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals_Point_Integer() {
        System.out.println("equales");
        Point p = new PointImpl(9,8,100,0.000000000000101);
        Integer ulp = 40000;
        Point instance = new PointImpl(9,8,100,0.0000000000001);
        Boolean expResult = false;
        Boolean result = instance.equals(p, ulp);
        assertEquals(expResult, result);
    }

    private class VecImpl implements Vec {
        double x;
        double y;
        double z;
        double w;
        public VecImpl(double x, double y, double z, double w) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vec add(Vec v) {
            return new VecImpl(x + v.get(0), y + v.get(1), z + v.get(2), w + v.get(3));
        }

        public Vec scale(Double a) {
            return new VecImpl(a*x, a*y, a*z, a*w);
        }

        public Integer getDimention() {
            return 4;
        }

        public Double get(Integer i) {
            assertTrue(0 <= i && i <= 3);
            return i == 0 ? x : i == 1 ? y : i == 2 ? z : w;
        }

        public Double dot(Vec v) {
            return x*v.get(0)+y*v.get(1)+z*v.get(2)+w*v.get(3);
        }

        public Boolean equals(Vec v, Double eps) {
            if(4 == v.getDimention()){
                for(int i = 0; i < 4; ++i){
                    if(!Precision.equals(get(i), v.get(i), eps))
                        return false;
                }
                return true;
            }
            return false;
        }

        public Boolean equals(Vec v, Integer ulp) {
            if(4 == v.getDimention()){
                for(int i = 0; i < 4; ++i){
                    if(!Precision.equals(get(i), v.get(i), ulp))
                        return false;
                }
                return true;
            }
            return false;
        }
    }
    
    private static boolean checkEquals(Point p1, Point p2){
        return checkEqualsDouble(p1.get(0), p2.get(0)) && checkEqualsDouble(p1.get(1), p2.get(1)) && checkEqualsDouble(p1.get(2), p2.get(2)) && checkEqualsDouble(p1.get(3), p2.get(3));
    }
    
    private static boolean checkEquals(Vec v1, Vec v2){
        return checkEqualsDouble(v1.get(0), v2.get(0)) && checkEqualsDouble(v1.get(1), v2.get(1)) && checkEqualsDouble(v1.get(2), v2.get(2)) && checkEqualsDouble(v1.get(3), v2.get(3));
    }
        
    private static boolean checkEqualsDouble(Double a, Double b){
        return Precision.equals(a, b, 1.0e-10);
    }
    
    private class PointImpl implements Point {

        VecImpl v;

        public PointImpl(double x, double y, double z, double w) {
            v = new VecImpl(x, y, z, w);
        }
        
        public Vec getVector() {
            return v;
        }
    }
}
