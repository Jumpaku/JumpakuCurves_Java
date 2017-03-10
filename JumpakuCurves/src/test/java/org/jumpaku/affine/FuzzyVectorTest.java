/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.jumpaku.affine.FuzzyVectorMatcher.fuzzyVectorOf;
import static org.hamcrest.core.Is.is;
import org.jumpaku.fuzzy.Grade;
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
     * Test of membership method, of class FuzzyPoint.
     */
    @Test
    public void testMembership() {
        System.out.println("membership");
        Vector p1 = Vector.of(1.0, 1.0);
        Vector p2 = Vector.of(1.0, 0.0);
        Vector p3 = Vector.of(1.0, -1.0);
        Vector p4 = Vector.of(0.0, 0.0);
        FuzzyVector instance = FuzzyVector.of(1.0, 1.0, 0.0, 2.0);
        
        Grade expResult1 = Grade.trueValue();
        Grade result1 = instance.membership(p1);
        assertEquals(expResult1.getValue(), result1.getValue(), 1.0e-10);
        
        Grade expResult2 = Grade.of(0.5);
        Grade result2 = instance.membership(p2);
        assertEquals(expResult2.getValue(), result2.getValue(), 1.0e-10);
        
        Grade expResult3 = Grade.falseValue();
        Grade result3 = instance.membership(p3);
        assertEquals(expResult3.getValue(), result3.getValue(), 1.0e-10);
        
        Grade expResult4 = Grade.of(1.0 - 1.0 / Math.sqrt(2));
        Grade result4 = instance.membership(p4);
        assertEquals(expResult4.getValue(), result4.getValue(), 1.0e-10);
        
        
        assertEquals(1.0, FuzzyVector.crisp(0.0, 0.0).membership(Vector.of(0.0, 0.0)).getValue(), 1.0e-10);
        assertEquals(0.0, FuzzyVector.crisp(0.0, 0.0).membership(Vector.of(1.0, 0.0)).getValue(), 1.0e-10);
    }

    /**
     * Test of possibility method, of class FuzzyPoint.
     */
    @Test
    public void testPossibility() {
        System.out.println("possibility");
        FuzzyVector p1 = FuzzyVector.of(0.0, 1.0, 0.0, 2.0);
        FuzzyVector p2 = FuzzyVector.of(0.0, 2.0, 0.0, 2.0);
        FuzzyVector p3 = FuzzyVector.of(0.0, 3.0, 0.0, 2.0);
        FuzzyVector p4 = FuzzyVector.of(2.0, 2.0, 0.0, 2.0);
        FuzzyVector instance = FuzzyVector.of(0.0, 0.0, 0.0, 2.0);
        Grade expResult1 = Grade.of(0.75);
        Grade expResult2 = Grade.of(0.5);
        Grade expResult3 = Grade.of(0.25);
        Grade expResult4 = Grade.of(1.0 - 1.0/Math.sqrt(2.0));
        Grade result1 = instance.possibility(p1);
        Grade result2 = instance.possibility(p2);
        Grade result3 = instance.possibility(p3);
        Grade result4 = instance.possibility(p4);
        assertEquals(expResult1.getValue(), result1.getValue(), 1.0e-10);
        assertEquals(expResult2.getValue(), result2.getValue(), 1.0e-10);
        assertEquals(expResult3.getValue(), result3.getValue(), 1.0e-10);
        assertEquals(expResult4.getValue(), result4.getValue(), 1.0e-10);
    }

    /**
     * Test of necessity method, of class FuzzyPoint.
     */
    @Test
    public void testNecessity() {
        System.out.println("necessity");
        FuzzyVector p1 = FuzzyVector.of(0.0, 0.0, 0.0, 2.0);
        FuzzyVector p2 = FuzzyVector.of(1.0, 0.0, 0.0, 2.0);
        FuzzyVector p3 = FuzzyVector.of(2.0, 0.0, 0.0, 2.0);
        FuzzyVector p4 = FuzzyVector.of(3.0, 0.0, 0.0, 5.0);
        FuzzyVector instance = FuzzyVector.of(0.0, 0.0, 0.0, 2.0);
        Grade expResult1 = Grade.of(0.5);
        Grade expResult2 = Grade.of(0.25);
        Grade expResult3 = Grade.of(0.0);
        Grade expResult4 = Grade.of(0.0);
        Grade result1 = instance.necessity(p1);
        Grade result2 = instance.necessity(p2);
        Grade result3 = instance.necessity(p3);
        Grade result4 = instance.necessity(p4);
        assertEquals(expResult1.getValue(), result1.getValue(), 1.0e-10);
        assertEquals(expResult2.getValue(), result2.getValue(), 1.0e-10);
        assertEquals(expResult3.getValue(), result3.getValue(), 1.0e-10);
        assertEquals(expResult4.getValue(), result4.getValue(), 1.0e-10);
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
