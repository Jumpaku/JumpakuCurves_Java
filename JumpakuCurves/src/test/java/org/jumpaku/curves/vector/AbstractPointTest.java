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
public class AbstractPointTest {
    
    public AbstractPointTest() {
    }

    /**
     * Test of toPoint method, of class AbstractPoint.
     */
    @Test
    public void testToPoint() {
        System.out.println("toPoint");
        Vec v = new VecImpl(1,2,3,4);
        AbstractPoint instance = new AbstractPointImpl(4,5,6,7);
        Point expResult = new AbstractPointImpl(1,2,3,4);
        Point result = instance.toPoint(v);
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of getVector method, of class AbstractPoint.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        AbstractPoint instance = new AbstractPointImpl(2,1,4,-3);
        Vec expResult = new VecImpl(2,1,4,-3);
        Vec result = instance.getVector();
        assert(checkEquals(expResult, result));
    }

    /**
     * Test of move method, of class AbstractPoint.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Vec v = new VecImpl(2,0.002,0.009,-0.0);
        AbstractPoint instance = new AbstractPointImpl(10,9,8,7);
        Point expResult = new AbstractPointImpl(12,9.002, 8.009, 7.0);
        Point result = instance.move(v);
        assertTrue(checkEquals(expResult, result));
    }

    /**
     * Test of divide method, of class AbstractPoint.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        Double t = 0.7;
        Point p = new AbstractPointImpl(2,4,5,3);
        AbstractPoint instance = new AbstractPointImpl(-3,9,-0.0,103);
        Point expResult = new AbstractPointImpl(0.5, 5.5, 3.5, 33);
        Point result = instance.divide(t, p);
        assertTrue(checkEquals(expResult, result));
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

    private class AbstractPointImpl extends AbstractPoint {

        VecImpl v;

        public AbstractPointImpl(double x, double y, double z, double w) {
            v = new VecImpl(x, y, z, w);
        }
        
        public Vec getVector() {
            return v;
        }

        public Point toPoint(Vec v) {
            return new AbstractPointImpl(v.get(0), v.get(1), v.get(2), v.get(3));
        }
    }
}
