/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class VectorTest {
    
    public VectorTest() {
    }
    
    /**
     * Test of equals method, of class Vector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Vector a = Vector.of(1.0, -0.5, 100.4523);
        Vector b = Vector.of(1.0, -0.5, 100.4523);
        assertEquals(true, Vector.equals(a, b, 1.0e-10));
        Vector c = Vector.of(1.0, -0.5, 100.4523);
        Vector d = Vector.of(1.0, -0.5, 100.4524);
        assertEquals(false, Vector.equals(c, d, 1.0e-10));
        Vector e = Vector.of(0.0, 0.0, 0.0);
        Vector f = Vector.zero();
        assertEquals(true, Vector.equals(e, f, 1.0e-10));
    }
    
    @Test
    public void testAxiom(){
        System.out.println("axiom");
        Vector zero = Vector.zero();
        Vector a = Vector.of(1.2, 1.3, -2000.4567);
        Vector b = Vector.of(0.34, -0.222294, 100043.004);
        Vector c = Vector.of(3425.2, 315526.1, -3542.12435);
        Double u = 2.0632;
        Double v = -0.3642;
        
        assertTrue(Vector.equals(a.add(b), b.add(a), 1.0e-10));
        assertTrue(Vector.equals(a.add(b).add(c), a.add(b.add(c)), 1.0e-10));
        assertTrue(Vector.equals(a.scale(u).add(b.scale(u)), a.add(b).scale(u), 1.0e-10));
        assertTrue(Vector.equals(a.scale(u).add(a.scale(v)), a.scale(u+v), 1.0e-10));

        assertTrue(Vector.equals(Vector.of(0.0, 0.0, 0.0), zero, 1.0e-10));
        assertTrue(Vector.equals(a.scale(0.0), zero, 1.0e-10));
        assertTrue(Vector.equals(zero.add(a), a, 1.0e-10));
        assertTrue(Vector.equals(a.sub(a), zero, 1.0e-10));
    }

    /**
     * Test of zero method, of class Vector.
     */
    @Test
    public void testZero() {
        System.out.println("zero");
        assertTrue(Vector.equals(Vector.of(0.0, 0.0, 0.0), Vector.zero(), 1.0e-10));
    }   

    /**
     * Test of of method, of class Vector.
     */
    @Test
    public void testOf_Vector3D() {
        System.out.println("of");
        assertTrue(Vector.equals(Vector.of(1.0, 2.0, 3.0), Vector.of(new Vector3D(1, 2, 3)), 1.0e-10));
    }

    /**
     * Test of of method, of class Vector.
     */
    @Test
    public void testOf_3args() {
        System.out.println("of");
        Vector v = Vector.of(1.0, 3.0, -0.43);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(3.0, v.getY(), 1.0e-10);
        assertEquals(-0.43, v.getZ(), 1.0e-10);
    }

    /**
     * Test of of method, of class Vector.
     */
    @Test
    public void testOf_2args() {
        System.out.println("of");
        assertTrue(Vector.equals(Vector.of(-0.4345, 120095.13531, 0.0), Vector.of(-0.4345, 120095.13531), 1.0e-10));
    }

    /**
     * Test of of method, of class Vector.
     */
    @Test
    public void testOf() {
        System.out.println("of");
        assertTrue(Vector.equals(Vector.of(-0.4345, 0.0, 0.0), Vector.of(-0.4345), 1.0e-10));

    }

    /**
     * Test of add method, of class Vector.
     */
    @Test
    public void testAdd_Vector() {
        System.out.println("add");
        assertTrue(Vector.equals(
                Vector.of(-0.4345, 4264.246, 35220.035).add(Vector.of(0.4645, 0.62523, -3425.4)),
                Vector.of(0.03, 4264.87123, 31794.635), 1.0e-10));
    }

    /**
     * Test of scale method, of class Vector.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        assertTrue(Vector.equals(
                Vector.of(-0.4345, 42.6, 352.5).scale(-3.0),
                Vector.of(1.3035, -127.8, -1057.5), 1.0e-10));
    }

    /**
     * Test of dot method, of class Vector.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        assertEquals(
                7.53, Vector.of(-3.0, 4.5, -0.3).dot(Vector.of(2.0, 3.0, -0.1)), 1.0e-10);
    }

    /**
     * Test of getX method, of class Vector.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        assertEquals(
                -3.0, Vector.of(-3.0, 4.5, -0.3).getX(), 1.0e-10);
    }

    /**
     * Test of getY method, of class Vector.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        assertEquals(
                4.5, Vector.of(-3.0, 4.5, -0.3).getY(), 1.0e-10);
     }

    /**
     * Test of getZ method, of class Vector.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        assertEquals(
                -0.3, Vector.of(-3.0, 4.5, -0.3).getZ(), 1.0e-10);
     }

    /**
     * Test of add method, of class Vector.
     */
    @Test
    public void testAdd_4args() {
        System.out.println("add");
        assertTrue(Vector.equals(Vector.of(1.3, 1.6, -2.6),
                Vector.add(0.7, Vector.of(1.0, 1.0, -2.0), 0.3, Vector.of(2.0, 3.0, -4.0)), 1.0e-10));
    }

    /**
     * Test of sub method, of class Vector.
     */
    @Test
    public void testSub_Vector() {
        System.out.println("sub");
        assertTrue(Vector.equals(Vector.of(0.99, 4.6, -5.604),
                Vector.of(1.3, 0.3, -0.004).sub(Vector.of(0.31, -4.3, 5.6)), 1.0e-10));
    }

    /**
     * Test of sub method, of class Vector.
     */
    @Test
    public void testSub_Double_Vector() {
        System.out.println("sub");
        assertTrue(Vector.equals(Vector.of(0.7, 8.9, -11.6),
                Vector.of(1.3, 0.3, -0.4).sub(2.0, Vector.of(0.3, -4.3, 5.6)), 1.0e-10));
    }

    /**
     * Test of add method, of class Vector.
     */
    @Test
    public void testAdd_Double_Vector() {
        System.out.println("add");
        assertTrue(Vector.equals(Vector.of(0.7, 8.9, -11.6),
                Vector.of(1.3, 0.3, -0.4).add(-2.0, Vector.of(0.3, -4.3, 5.6)), 1.0e-10));
    }

    /**
     * Test of square method, of class Vector.
     */
    @Test
    public void testSquare() {
        System.out.println("square");
        assertEquals(
                9.34, Vector.of(-3.0, 0.5, -0.3).square(), 1.0e-10);
    }

    /**
     * Test of length method, of class Vector.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        assertEquals(
                3.05614135799, Vector.of(-3.0, 0.5, -0.3).length(), 1.0e-10);
    }

    /**
     * Test of negate method, of class Vector.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        assertTrue(Vector.equals(Vector.of(-1.3, -0.3, 0.4),
                Vector.of(1.3, 0.3, -0.4).negate(), 1.0e-10));
    }

    /**
     * Test of normalize method, of class Vector.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        assertTrue(Vector.equals(Vector.of(-1.0/3.0, 2.0/3.0, -2.0/3.0), Vector.of(-1.0, 2.0, -2.0).normalize(), 1.0e-10));
    }

    /**
     * Test of normalize method, of class Vector.
     */
    @Test
    public void testResize() {
        System.out.println("resize");
        assertTrue(Vector.equals(Vector.of(-2.0/3.0, 4.0/3.0, -4.0/3.0), Vector.of(-1.0, 2.0, -2.0).resize(2.0), 1.0e-10));
    }
    
    /**
     * Test of cross method, of class Vector.
     */
    @Test
    public void testCross() {
        System.out.println("cross");
        assertTrue(Vector.equals(Vector.of(-5.0, 4.0, 1.0), Vector.of(1.0, 1.0, 1.0).cross(Vector.of(1.0, 2.0, -3.0)), 1.0e-10));
    }

    /**
     * Test of angle method, of class Vector.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        assertEquals(
                Math.PI/2.0, Vector.of(1.0, 1.0, 0.0).angle(Vector.of(1.0, -1.0, 0.0)), 1.0e-10);
    }
    
    @Test
    public void testToString(){
        System.out.println("toString");
        JsonVector instance = new JsonVector();
        assertTrue(Vector.equals(Vector.of(1.23, 4.56, -7.89), instance.fromJson(Vector.toString(Vector.of(1.23, 4.56, -7.89))).get(), 1.0e-10));
    }
}
