/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.jumpaku.affine.FuzzyVectorMatcher.fuzzyVectorOf;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class FuzzyVectorTest {
    
    public FuzzyVectorTest() {
    }

    /**
     * Test of of method, of class FuzzyVector.
     */
    @Test
    public void testOf_4args() {
        System.out.println("of");
        FuzzyVector v = FuzzyVector.of(1.0, -2.0, 3.0, 4.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(-2.0, v.getY(), 1.0e-10);
        assertEquals(3.0, v.getZ(), 1.0e-10);
        assertEquals(4.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of of method, of class FuzzyVector.
     */
    @Test
    public void testOf_3args() {
        System.out.println("of");
        FuzzyVector v = FuzzyVector.of(1.0, -2.0, 3.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(-2.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(3.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of of method, of class FuzzyVector.
     */
    @Test
    public void testOf_Double_Double() {
        System.out.println("of");
        FuzzyVector v = FuzzyVector.of(1.0, 2.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(2.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of zero method, of class FuzzyVector.
     */
    @Test
    public void testZero_Double() {
        System.out.println("zero");
        FuzzyVector v = FuzzyVector.zero(1.0);
        assertEquals(0.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(1.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of of method, of class FuzzyVector.
     */
    @Test
    public void testOf_Vector_Double() {
        System.out.println("of");
        FuzzyVector v = FuzzyVector.of(Vector.of(1.0, 2.0, -3.0), 2.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(2.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyVector.
     */
    @Test
    public void testCrisp_3args() {
        System.out.println("crisp");
        FuzzyVector v = FuzzyVector.crisp(1.0, 2.0, -3.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyVector.
     */
    @Test
    public void testCrisp_Double_Double() {
        System.out.println("crisp");
        FuzzyVector v = FuzzyVector.crisp(1.0, 2.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyVector.
     */
    @Test
    public void testCrisp_Double() {
        System.out.println("crisp");
        FuzzyVector v = FuzzyVector.crisp(1.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyVector.
     */
    @Test
    public void testCrisp_Vector() {
        System.out.println("crisp");
        FuzzyVector v = FuzzyVector.crisp(Vector.of(1.0, 2.0, -3.0));
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of zero method, of class FuzzyVector.
     */
    @Test
    public void testZero_0args() {
        System.out.println("zero");
        FuzzyVector v = FuzzyVector.zero();
        assertEquals(0.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of toJson method, of class FuzzyVector.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        FuzzyVector v = FuzzyVector.of(1.0, 2.0, -1.0, 2.0);
        assertThat(FuzzyVector.fromJson(FuzzyVector.toJson(v)).get(), fuzzyVectorOf(v));
    }

    /**
     * Test of fromJson method, of class FuzzyVector.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        assertThat(FuzzyVector.fromJson("{x:1.0, y:2.0, z:-1.0, r:2.0}").get(), fuzzyVectorOf(1.0, 2.0, -1.0, 2.0));
    }

    /**
     * Test of getR method, of class FuzzyVector.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        FuzzyVector v = FuzzyVector.of(Vector.of(1.0, 2.0, -3.0), 2.0);
        assertEquals(2.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of add method, of class FuzzyVector.
     */
    @Test
    public void testAdd_Vector() {
        System.out.println("add");
        FuzzyVector instance = FuzzyVector.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.add(Vector.of(3.0, 4.0, 5.0)), is(fuzzyVectorOf(4.0, 6.0, 2.0, 1.0)));
        assertThat(instance.add(FuzzyVector.crisp(3.0, 4.0, 5.0)), is(fuzzyVectorOf(4.0, 6.0, 2.0, 1.0)));
        assertThat(instance.add(FuzzyVector.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyVectorOf(4.0, 6.0, 2.0, 3.0)));
    }

    /**
     * Test of scale method, of class FuzzyVector.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        FuzzyVector instance = FuzzyVector.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.scale(5.0), is(fuzzyVectorOf(5.0, 10.0, -15.0, 5.0)));
        assertThat(instance.scale(-5.0), is(fuzzyVectorOf(-5.0, -10.0, 15.0, 5.0)));
    }

    /**
     * Test of sub method, of class FuzzyVector.
     */
    @Test
    public void testSub_Vector() {
        System.out.println("sub");
        FuzzyVector instance = FuzzyVector.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.sub(Vector.of(3.0, 4.0, 5.0)), is(fuzzyVectorOf(-2.0, -2.0, -8.0, 1.0)));
        assertThat(instance.sub(FuzzyVector.crisp(3.0, 4.0, 5.0)), is(fuzzyVectorOf(-2.0, -2.0, -8.0, 1.0)));
        assertThat(instance.sub(FuzzyVector.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyVectorOf(-2.0, -2.0, -8.0, 3.0)));
    }

    /**
     * Test of sub method, of class FuzzyVector.
     */
    @Test
    public void testSub_Double_Vector() {
        System.out.println("sub");
        FuzzyVector instance = FuzzyVector.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.sub(-0.5, Vector.of(3.0, 4.0, 5.0)), is(fuzzyVectorOf(2.5, 4.0, -0.5, 1.0)));
        assertThat(instance.sub(-0.5, FuzzyVector.crisp(3.0, 4.0, 5.0)), is(fuzzyVectorOf(2.5, 4.0, -0.5, 1.0)));
        assertThat(instance.sub(-0.5, FuzzyVector.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyVectorOf(2.5, 4.0, -0.5, 2.0)));
        assertThat(instance.sub(0.5, FuzzyVector.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyVectorOf(-0.5, 0.0, -5.5, 2.0)));
    }

    /**
     * Test of add method, of class FuzzyVector.
     */
    @Test
    public void testAdd_Double_Vector() {
        System.out.println("add");
        FuzzyVector instance = FuzzyVector.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.add(-2.0, Vector.of(3.0, 4.0, 5.0)), is(fuzzyVectorOf(-5.0, -6.0, -13.0, 1.0)));
        assertThat(instance.add(-2.0, FuzzyVector.crisp(3.0, 4.0, 5.0)), is(fuzzyVectorOf(-5.0, -6.0, -13.0, 1.0)));
        assertThat(instance.add(-2.0, FuzzyVector.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyVectorOf(-5.0, -6.0, -13.0, 5.0)));
        assertThat(instance.add(2.0, FuzzyVector.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyVectorOf(7.0, 10.0, 7.0, 5.0)));
    }

    /**
     * Test of negate method, of class FuzzyVector.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        FuzzyVector instance = FuzzyVector.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.negate(), is(fuzzyVectorOf(-1.0, -2.0, 3.0, 1.0)));
    }

    /**
     * Test of normalize method, of class FuzzyVector.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        FuzzyVector instance = FuzzyVector.of(1.0, 2.0, -2.0, 1.0);
        assertThat(instance.normalize(), is(fuzzyVectorOf(1.0/3.0, 2.0/3.0, -2.0/3.0, 1.0/3.0)));
    }

    /**
     * Test of resize method, of class FuzzyVector.
     */
    @Test
    public void testResize() {
        System.out.println("resize");
        FuzzyVector instance = FuzzyVector.of(2.0, 4.0, -4.0, 1.0);
        assertThat(instance.resize(3.0), is(fuzzyVectorOf(1.0, 2.0, -2.0, 1.0/2.0)));
    }
}
