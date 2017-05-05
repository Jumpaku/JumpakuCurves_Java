/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.VectorMatcher.vectorOf;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class VectorTest {
    
    /**
     * Test closed equals method, closed class Vector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Vector.Crisp a = Vector.crisp(1.0, -0.5, 100.4523);
        Vector.Crisp b = Vector.crisp(1.0, -0.5, 100.4523);
        assertTrue(Vector.equals(a, b, 1.0e-10));
        Vector.Crisp c = Vector.crisp(1.0, -0.5, 100.4523);
        Vector.Crisp d = Vector.crisp(1.0, -0.5, 100.4524);
        assertFalse(Vector.equals(c, d, 1.0e-10));
        Vector.Crisp e = Vector.crisp(0.0, 0.0, 0.0);
        Vector.Crisp f = Vector.ZERO;
        assertTrue(Vector.equals(e, f, 1.0e-10));
    }
    /**
     * Test closed fuzzy method, closed class Vector.
     */
    @Test
    public void testFuzzy_VectorCrisp_Double() {
        System.out.println("fuzzy");
        Vector.Fuzzy v = Vector.fuzzy(Vector.crisp(1.0, 2.0, -3.0), 10.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(10.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed fuzzy method, closed class Vector.
     */
    @Test
    public void testFuzzy_4args() {
        System.out.println("fuzzy");
        Vector.Fuzzy v = Vector.fuzzy(1.0, 2.0, -3.0, 10.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(10.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed fuzzy method, closed class Vector.
     */
    @Test
    public void testFuzzy_3args() {
        System.out.println("fuzzy");
        Vector.Fuzzy v = Vector.fuzzy(1.0, 2.0, 10.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(10.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed fuzzy method, closed class Vector.
     */
    @Test
    public void testFuzzy_Double_Double() {
        System.out.println("fuzzy");
        Vector.Fuzzy v = Vector.fuzzy(1.0, 10.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(10.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed zero method, closed class Vector.
     */
    @Test
    public void testZero_Double() {
        System.out.println("zero");
        Vector.Fuzzy v = Vector.zero(10.0);
        assertEquals(0.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(10.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed crisp method, closed class Vector.
     */
    @Test
    public void testCrisp_3args() {
        System.out.println("crisp");
        Vector.Crisp v = Vector.crisp(1.0, 2.0, -3.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed crisp method, closed class Vector.
     */
    @Test
    public void testCrisp_Double_Double() {
        System.out.println("crisp");
        Vector.Crisp v = Vector.crisp(1.0, 2.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed crisp method, closed class Vector.
     */
    @Test
    public void testCrisp_Double() {
        System.out.println("crisp");
        Vector.Crisp v = Vector.crisp(1.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed zero method, closed class Vector.
     */
    @Test
    public void testZero_0args() {
        System.out.println("zero");
        Vector.Crisp v = Vector.ZERO;
        assertEquals(0.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test closed toJson method, closed class Vector.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        assertThat(Vector.fromJson(Vector.toJson(Vector.fuzzy(1.23, 4.56, -7.89, 10.0))).get(),
                is(vectorOf(1.23, 4.56, -7.89, 10.0)));
    }

    /**
     * Test closed fromJson method, closed class Vector.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        assertThat(Vector.fromJson("{x:1.23, y:4.56, z:-7.89,r:10.0}").get(), 
                is(vectorOf(1.23, 4.56, -7.89, 10.0)));
        assertTrue(Vector.fromJson("{x:1.23, y:4.56}").isEmpty());
    }

    /**
     * Test closed add method, closed class Vector.
     */
    @Test
    public void testAdd_Vector() {
        System.out.println("add");
        assertThat(Vector.crisp(-0.4345, 4264.246, 35220.035).add(Vector.crisp(0.4645, 0.62523, -3425.4)),
                is(vectorOf(Vector.crisp(0.03, 4264.87123, 31794.635))));
        assertThat(Vector.fuzzy(-0.4345, 4264.246, 35220.035, 100.0).add(Vector.crisp(0.4645, 0.62523, -3425.4)),
                is(vectorOf(0.03, 4264.87123, 31794.635, 100.0)));
        assertThat(Vector.crisp(-0.4345, 4264.246, 35220.035).add(Vector.fuzzy(0.4645, 0.62523, -3425.4, 100.0)),
                is(vectorOf(0.03, 4264.87123, 31794.635, 100.0)));
        assertThat(Vector.fuzzy(-0.4345, 4264.246, 35220.035, 200.0).add(Vector.fuzzy(0.4645, 0.62523, -3425.4, 100.0)),
                is(vectorOf(0.03, 4264.87123, 31794.635, 300.0)));
    }

    /**
     * Test closed scale method, closed class Vector.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        assertThat(Vector.crisp(-0.4345, 42.6, 352.5).scale(-3.0),
                is(vectorOf(Vector.crisp(1.3035, -127.8, -1057.5))));
        assertThat(Vector.fuzzy(-0.4345, 42.6, 352.5, 3.0).scale(-3.0),
                is(vectorOf(1.3035, -127.8, -1057.5, 9.0)));
        assertThat(Vector.fuzzy(-0.4345, 42.6, 352.5, 3.0).scale(3.0),
                is(vectorOf(-1.3035, 127.8, 1057.5, 9.0)));
    }

    /**
     * Test closed getX method, closed class Vector.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        assertEquals(-3.0, Vector.crisp(-3.0, 4.5, -0.3).getX(), 1.0e-10);
        assertEquals(-3.0, Vector.fuzzy(-3.0, 4.5, -0.3, 4.0).getX(), 1.0e-10);
    }

    /**
     * Test closed getY method, closed class Vector.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        assertEquals(4.5, Vector.crisp(-3.0, 4.5, -0.3).getY(), 1.0e-10);
        assertEquals(4.5, Vector.fuzzy(-3.0, 4.5, -0.3, 10.0).getY(), 1.0e-10);
     }

    /**
     * Test closed getZ method, closed class Vector.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        assertEquals(-0.3, Vector.crisp(-3.0, 4.5, -0.3).getZ(), 1.0e-10);
        assertEquals(-0.3, Vector.fuzzy(-3.0, 4.5, -0.3, 100.0).getZ(), 1.0e-10);
     }

    /**
     * Test closed getR method, closed class Vector.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        assertEquals(0.0, Vector.crisp(-3.0, 4.5, -0.3).getR(), 1.0e-10);
        assertEquals(100.0, Vector.fuzzy(-3.0, 4.5, -0.3, 100.0).getR(), 1.0e-10);
    }

    /**
     * Test closed add method, closed class Vector.
     */
    @Test
    public void testCrispAdd_4args() {
        System.out.println("Crisp.add");
        assertThat(Vector.Crisp.add(0.7, Vector.crisp(1.0, 1.0, -2.0), 0.3, Vector.crisp(2.0, 3.0, -4.0)), 
                is(vectorOf(Vector.crisp(1.3, 1.6, -2.6))));
    }

    /**
     * Test closed sub method, closed class Vector.
     */
    @Test
    public void testSub_Vector() {
        System.out.println("sub");
        assertThat(Vector.crisp(1.3, 0.3, -0.004).sub(Vector.crisp(0.31, -4.3, 5.6)), 
                is(vectorOf(Vector.crisp(0.99, 4.6, -5.604))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.004, 10.0).sub(Vector.crisp(0.31, -4.3, 5.6)), 
                is(vectorOf(Vector.fuzzy(0.99, 4.6, -5.604, 10.0))));
        assertThat(Vector.crisp(1.3, 0.3, -0.004).sub(Vector.fuzzy(0.31, -4.3, 5.6, 10.0)), 
                is(vectorOf(Vector.fuzzy(0.99, 4.6, -5.604, 10.0))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.004, 10.0).sub(Vector.fuzzy(0.31, -4.3, 5.6, 20.0)), 
                is(vectorOf(Vector.fuzzy(0.99, 4.6, -5.604, 30.0))));
    }

    /**
     * Test closed sub method, closed class Vector.
     */
    @Test
    public void testSub_Double_Vector() {
        System.out.println("sub");
        assertThat(Vector.crisp(1.3, 0.3, -0.4).sub(2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.crisp(0.7, 8.9, -11.6))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).sub(2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.fuzzy(0.7, 8.9, -11.6, 10.0))));
        assertThat(Vector.crisp(1.3, 0.3, -0.4).sub(2.0, Vector.fuzzy(0.3, -4.3, 5.6, 10.0)),
                is(vectorOf(Vector.fuzzy(0.7, 8.9, -11.6, 20.0))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).sub(2.0, Vector.fuzzy(0.3, -4.3, 5.6, 20.0)),
                is(vectorOf(Vector.fuzzy(0.7, 8.9, -11.6, 50.0))));
        
        assertThat(Vector.crisp(1.3, 0.3, -0.4).sub(-2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.crisp(1.9, -8.3, 10.8))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).sub(-2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.fuzzy(1.9, -8.3, 10.8, 10.0))));
        assertThat(Vector.crisp(1.3, 0.3, -0.4).sub(-2.0, Vector.fuzzy(0.3, -4.3, 5.6, 10.0)),
                is(vectorOf(Vector.fuzzy(1.9, -8.3, 10.8, 20.0))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).sub(-2.0, Vector.fuzzy(0.3, -4.3, 5.6, 20.0)),
                is(vectorOf(Vector.fuzzy(1.9, -8.3, 10.8, 50.0))));
    }
    
    /**
     * Test closed add method, closed class Vector.
     */
    @Test
    public void testAdd_Double_Vector() {
        System.out.println("add");
        assertThat(Vector.crisp(1.3, 0.3, -0.4).add(-2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.crisp(0.7, 8.9, -11.6))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).add(-2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.fuzzy(0.7, 8.9, -11.6, 10.0))));
        assertThat(Vector.crisp(1.3, 0.3, -0.4).add(-2.0, Vector.fuzzy(0.3, -4.3, 5.6, 10.0)),
                is(vectorOf(Vector.fuzzy(0.7, 8.9, -11.6, 20.0))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).add(-2.0, Vector.fuzzy(0.3, -4.3, 5.6, 20.0)),
                is(vectorOf(Vector.fuzzy(0.7, 8.9, -11.6, 50.0))));
        
        assertThat(Vector.crisp(1.3, 0.3, -0.4).add(2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.crisp(1.9, -8.3, 10.8))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).add(2.0, Vector.crisp(0.3, -4.3, 5.6)),
                is(vectorOf(Vector.fuzzy(1.9, -8.3, 10.8, 10.0))));
        assertThat(Vector.crisp(1.3, 0.3, -0.4).add(2.0, Vector.fuzzy(0.3, -4.3, 5.6, 10.0)),
                is(vectorOf(Vector.fuzzy(1.9, -8.3, 10.8, 20.0))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 10.0).add(2.0, Vector.fuzzy(0.3, -4.3, 5.6, 20.0)),
                is(vectorOf(Vector.fuzzy(1.9, -8.3, 10.8, 50.0))));
    }

    /**
     * Test closed negate method, closed class Vector.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        assertThat(Vector.crisp(1.3, 0.3, -0.4).negate(), 
                is(vectorOf(Vector.crisp(-1.3, -0.3, 0.4))));
        assertThat(Vector.fuzzy(1.3, 0.3, -0.4, 5.0).negate(), 
                is(vectorOf(Vector.fuzzy(-1.3, -0.3, 0.4, 5.0))));
    }

    /**
     * Test closed toCrisp method, closed class Vector.
     */
    @Test
    public void testToCrisp() {
        System.out.println("toCrisp");
        assertThat(Vector.fuzzy(-1.0, 2.0, -3.0, 4.0).toCrisp(),
                is(vectorOf(-1.0, 2.0, -3.0, 0.0)));
        assertThat(Vector.crisp(-1.0, 2.0, -3.0).toCrisp(),
                is(vectorOf(-1.0, 2.0, -3.0, 0.0)));
    }

    /**
     * Test closed membership method, closed class Vector.
     */
    @Test
    public void testMembership() {
        System.out.println("membership");
        Vector.Crisp p0 = Vector.ZERO;
        Vector.Crisp p1 = Vector.crisp(1.0);
        Vector.Crisp p2 = Vector.crisp(2.0);
        Vector.Crisp p3 = Vector.crisp(3.0);
        
        Vector instance1 = Vector.zero(2.0);
        assertEquals(1.0, instance1.membership(p0).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.membership(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.membership(p2).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.membership(p3).getValue(), 1.0e-10);
        
        Vector instance2 = Vector.ZERO;
        assertEquals(1.0, instance2.membership(p0).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.membership(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.membership(p2).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.membership(p3).getValue(), 1.0e-10);
    }

    /**
     * Test closed possibility method, closed class Vector.
     */
    @Test
    public void testPossibility() {
        System.out.println("possibility");
        Vector p0 = Vector.fuzzy(0.0, 1.0);
        Vector p1 = Vector.fuzzy(0.0, 2.0);
        Vector p2 = Vector.fuzzy(1.0, 0.5);
        Vector p3 = Vector.fuzzy(1.0, 2.0);
        Vector p4 = Vector.fuzzy(2.0, 1.0);
        Vector p5 = Vector.fuzzy(2.0, 4.0);
        Vector p6 = Vector.fuzzy(3.0, 1.0);
        Vector p7 = Vector.fuzzy(3.0, 8.0);
        Vector p8 = Vector.ZERO;
        Vector p9 = Vector.crisp(1.0);
        Vector p10 = Vector.crisp(2.0);
        Vector p11 = Vector.crisp(3.0);
        
        Vector instance1 = Vector.zero(2.0);
        assertEquals(1.0, instance1.possibility(p0).getValue(), 1.0e-10);
        assertEquals(1.0, instance1.possibility(p1).getValue(), 1.0e-10);
        assertEquals(3.0/5, instance1.possibility(p2).getValue(), 1.0e-10);
        assertEquals(0.75, instance1.possibility(p3).getValue(), 1.0e-10);
        assertEquals(1.0/3, instance1.possibility(p4).getValue(), 1.0e-10);
        assertEquals(2.0/3, instance1.possibility(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.possibility(p6).getValue(), 1.0e-10);
        assertEquals(0.7, instance1.possibility(p7).getValue(), 1.0e-10);
        assertEquals(1.0, instance1.possibility(p8).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.possibility(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.possibility(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.possibility(p11).getValue(), 1.0e-10);
        
        Vector instance2 = Vector.ZERO;
        assertEquals(1.0, instance2.possibility(p0).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.possibility(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p2).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.possibility(p3).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p4).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.possibility(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p6).getValue(), 1.0e-10);
        assertEquals(5.0/8, instance2.possibility(p7).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.possibility(p8).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p11).getValue(), 1.0e-10);
    }

    /**
     * Test closed necessity method, closed class Vector.
     */
    @Test
    public void testNecessity() {
        System.out.println("necessity");
        Vector p0 = Vector.fuzzy(0.0, 1.0);
        Vector p1 = Vector.fuzzy(0.0, 2.0);
        Vector p2 = Vector.fuzzy(1.0, 0.5);
        Vector p3 = Vector.fuzzy(1.0, 2.0);
        Vector p4 = Vector.fuzzy(2.0, 1.0);
        Vector p5 = Vector.fuzzy(2.0, 4.0);
        Vector p6 = Vector.fuzzy(3.0, 1.0);
        Vector p7 = Vector.fuzzy(3.0, 8.0);
        Vector p8 = Vector.ZERO;
        Vector p9 = Vector.crisp(1.0);
        Vector p10 = Vector.crisp(2.0);
        Vector p11 = Vector.crisp(3.0);
        
        Vector instance1 = Vector.zero(2.0);
        assertEquals(1.0/3, instance1.necessity(p0).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.necessity(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p2).getValue(), 1.0e-10);
        assertEquals(0.25, instance1.necessity(p3).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p4).getValue(), 1.0e-10);
        assertEquals(1.0/3, instance1.necessity(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p6).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.necessity(p7).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p8).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p11).getValue(), 1.0e-10);
        
        Vector instance2 = Vector.ZERO;
        assertEquals(1.0, instance2.necessity(p0).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.necessity(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p2).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.necessity(p3).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p4).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.necessity(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p6).getValue(), 1.0e-10);
        assertEquals(5.0/8, instance2.necessity(p7).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.necessity(p8).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p11).getValue(), 1.0e-10);
    }

    /**
     * Test closed normalize method, closed class Vector.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        assertThat(Vector.crisp(-1.0, 2.0, -2.0).normalize(), 
                is(vectorOf(Vector.crisp(-1.0/3.0, 2.0/3.0, -2.0/3.0))));
    }

    /**
     * Test closed normalize method, closed class Vector.
     */
    @Test
    public void testResize() {
        System.out.println("resize");
        assertThat(Vector.crisp(-1.0, 2.0, -2.0).resize(2.0), 
                is(vectorOf(Vector.crisp(-2.0/3.0, 4.0/3.0, -4.0/3.0))));
    }
    
    /**
     * Test closed dot method, closed class Vector.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        assertEquals(7.53,
                Vector.crisp(-3.0, 4.5, -0.3).dot(Vector.crisp(2.0, 3.0, -0.1)), 1.0e-10);
    }

    /**
     * Test closed square method, closed class Vector.
     */
    @Test
    public void testSquare() {
        System.out.println("square");
        assertEquals(9.34,
                Vector.crisp(-3.0, 0.5, -0.3).square(), 1.0e-10);
    }

    /**
     * Test closed length method, closed class Vector.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        assertEquals(3.05614135799,
                Vector.crisp(-3.0, 0.5, -0.3).length(), 1.0e-10);
    }
    
    /**
     * Test closed cross method, closed class Vector.
     */
    @Test
    public void testCross() {
        System.out.println("cross");
        assertThat(Vector.crisp(1.0, 1.0, 1.0).cross(Vector.crisp(1.0, 2.0, -3.0)),
                is(vectorOf(Vector.crisp(-5.0, 4.0, 1.0))));
    }

    /**
     * Test closed angle method, closed class Vector.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        assertEquals(Math.PI/2.0,
                Vector.crisp(1.0, 1.0, 0.0).angle(Vector.crisp(1.0, -1.0, 0.0)), 1.0e-10);
    }
    
    /**
     * Test closed toString method, closed class Vector.
     */
    @Test
    public void testToString(){
        System.out.println("toString");
        assertThat(Vector.fromJson(Vector.fuzzy(1.23, 4.56, -7.89, 1.0).toString()).get(),
                is(vectorOf(1.23, 4.56, -7.89, 1.0)));
        assertThat(Vector.fromJson(Vector.crisp(1.23, 4.56, -7.89).toString()).get(),
                is(vectorOf(1.23, 4.56, -7.89, 0.0)));
    }
}
