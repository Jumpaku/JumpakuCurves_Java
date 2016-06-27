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
public class VecTest {
    
    public VecTest() {
    }

    /**
     * Test of zero method, of class Vec.
     */
    @Test
    public void testZero() {
        System.out.println("zero");
        Integer dimention = 4;
        Vec expResult = new VecImpl(0.0, 0.0, 0.0, 0.0);
        Vec result = Vec.zero(dimention);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of add method, of class Vec.
     */
    @Test
    public void testAdd_4args() {
        System.out.println("add");
        Double a = 2.0;
        Vec v1 = new VecImpl(1.0, 2.0, 3.0, 4.0);
        Double b = -0.5;
        Vec v2 = new VecImpl(2.0, 4.0, 6.0, 10.0);
        Vec expResult = new VecImpl(1.0, 2.0, 3.0, 3.0);
        Vec result = Vec.add(a, v1, b, v2);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of add method, of class Vec.
     */
    @Test
    public void testAdd_Vec() {
        System.out.println("add");
        Vec v = new VecImpl(4.0, 3.0, -2.0, -1.0);
        Vec instance = new VecImpl(10.0, 3.0, 4.0, -2.0);
        Vec expResult = new VecImpl(14.0, 6.0, 2.0, -3.0);
        Vec result = instance.add(v);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of scale method, of class Vec.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Double a = -0.3;
        Vec instance = new VecImpl(10.0, 20.0, -100.0, -0.1);
        Vec expResult = new VecImpl(-3.0, -6.0, 30.0, 0.03);
        Vec result = instance.scale(a);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of getDimention method, of class Vec.
     */
    @Test
    public void testGetDimention() {
        System.out.println("getDimention");
        Vec instance = new VecImpl(3.0, 4.0, 5.0, 6.0);
        Integer expResult = 4;
        Integer result = instance.getDimention();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Vec.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Vec instance = new VecImpl(3.0, 5.0, -5.5, 3.33333);
        Double expResult0 = 3.0;
        Double expResult1 = 5.0;
        Double expResult2 = -5.5;
        Double expResult3 = 3.33333;
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
     * Test of dot method, of class Vec.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vec v = new VecImpl(4.0, -3.0, 10000.0, -200000.0);
        Vec instance = new VecImpl(1.0, 0.0, -0.0, 4.0);
        Double expResult = -799996.0;
        Double result = instance.dot(v);
        assertTrue(checkEqualsDouble(expResult, result));
    }

    /**
     * Test of equals method, of class Vec.
     */
    @Test
    public void testEquals_Vec_Double() {
        System.out.println("equals");
        Vec v = new VecImpl(-0.0, 2.0, 1.000000000000001, 3.4);
        Double eps = 1.0e-10;
        Vec instance = new VecImpl(0.0, 2.0, 1.0, 3.40000000001);
        Boolean expResult = true;
        Boolean result = instance.equals(v, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Vec.
     */
    @Test
    public void testEquals_Vec_Integer() {
        System.out.println("equals");
        Vec v = new VecImpl(-0.0, 2.0, 1.0000000001, 3.4);
        Double eps = 1.0e-10;
        Vec instance = new VecImpl(0.0, 2.0, 1.0, 3.40000000001);
        Boolean expResult = true;
        Boolean result = instance.equals(v, 450370);
        assertEquals(expResult, result);
    }

    /**
     * Test of sub method, of class Vec.
     */
    @Test
    public void testSub() {
        System.out.println("sub");
        Vec v = new VecImpl(4.0, 3.0, -2.0, -1.0);
        Vec instance = new VecImpl(10.0, 3.0, 4.0, -2.0);
        Vec expResult = new VecImpl(6.0, 0.0, 6.0, -1.0);
        Vec result = instance.sub(v);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of square method, of class Vec.
     */
    @Test
    public void testSquare() {
        System.out.println("square");
        Vec instance = new VecImpl(0.0, 2.0, 4.0, -6.0);
        Double expResult = 56.0;
        Double result = instance.square();
        assertTrue(checkEqualsDouble(expResult, result));
    }

    /**
     * Test of length method, of class Vec.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        Vec instance = new VecImpl(1.0, 3.0, -1.0, -5.0);
        Double expResult = 6.0;
        Double result = instance.length();
        assertTrue(checkEqualsDouble(expResult, result));
    }

    /**
     * Test of add method, of class Vec.
     */
    @Test
    public void testAdd_Double_Vec() {
        System.out.println("add");
        Double a = -0.5;
        Vec v = new VecImpl(2.0, 0.0, -3.0, 100.0);
        Vec instance = new VecImpl(100.0, 4.0, -0.0, 3.0);
        Vec expResult = new VecImpl(99.0, 4.0, 1.5, -47.0);
        Vec result = instance.add(a, v);
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of negate method, of class Vec.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Vec instance = new VecImpl(0.0, -0.0, 2.9, -0.000000001);
        Vec expResult = new VecImpl(-0.0, 0.0, -2.9, 0.000000001);
        Vec result = instance.negate();
        assertTrue(checkEquals(result, expResult));
    }

    /**
     * Test of normalize method, of class Vec.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        Vec instance = new VecImpl(1.0, 3.0, -1.0, -5.0);
        Vec expResult = new VecImpl(1.0/6.0, 3.0/6.0, -1.0/6.0, -5.0/6.0);
        Vec result = instance.normalize();
        assertTrue(checkEquals(result, expResult));
    }

    public class VecImpl implements Vec {
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
    
    private static boolean checkEquals(Vec v1, Vec v2){
        return checkEqualsDouble(v1.get(0), v2.get(0)) && checkEqualsDouble(v1.get(1), v2.get(1)) && checkEqualsDouble(v1.get(2), v2.get(2)) && checkEqualsDouble(v1.get(3), v2.get(3));
    }

    private static boolean checkEqualsDouble(Double a, Double b){
        return Precision.equals(a, b, 1.0e-10);
    }
}
