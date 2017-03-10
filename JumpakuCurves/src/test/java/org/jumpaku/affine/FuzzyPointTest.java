/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.jumpaku.affine.FuzzyPointMatcher.fuzzyPointOf;
import static org.jumpaku.affine.FuzzyVectorMatcher.fuzzyVectorOf;
import static org.hamcrest.core.Is.is;
import org.jumpaku.fuzzy.Grade;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class FuzzyPointTest {
    
    public FuzzyPointTest() {
    }


    /**
     * Test of of method, of class FuzzyPoint.
     */
    @Test
    public void testOf_4args() {
        System.out.println("of");
        FuzzyPoint v = FuzzyPoint.of(1.0, -2.0, 3.0, 4.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(-2.0, v.getY(), 1.0e-10);
        assertEquals(3.0, v.getZ(), 1.0e-10);
        assertEquals(4.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of of method, of class FuzzyPoint.
     */
    @Test
    public void testOf_3args() {
        System.out.println("of");
        FuzzyPoint v = FuzzyPoint.of(1.0, -2.0, 3.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(-2.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(3.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of of method, of class FuzzyPoint.
     */
    @Test
    public void testOf_Double_Double() {
        System.out.println("of");
        FuzzyPoint v = FuzzyPoint.of(1.0, 2.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(2.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of of method, of class FuzzyPoint.
     */
    @Test
    public void testOf_Point_Double() {
        System.out.println("of");
        FuzzyPoint v = FuzzyPoint.of(Point.of(1.0, 2.0, -3.0), 2.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(2.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyPoint.
     */
    @Test
    public void testCrisp_3args() {
        System.out.println("crisp");
        FuzzyPoint v = FuzzyPoint.crisp(1.0, 2.0, -3.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyPoint.
     */
    @Test
    public void testCrisp_Double_Double() {
        System.out.println("crisp");
        FuzzyPoint v = FuzzyPoint.crisp(1.0, 2.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyPoint.
     */
    @Test
    public void testCrisp_Double() {
        System.out.println("crisp");
        FuzzyPoint v = FuzzyPoint.crisp(1.0);
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(0.0, v.getY(), 1.0e-10);
        assertEquals(0.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of crisp method, of class FuzzyPoint.
     */
    @Test
    public void testCrisp_Point() {
        System.out.println("crisp");
        FuzzyPoint v = FuzzyPoint.crisp(Point.of(1.0, 2.0, -3.0));
        assertEquals(1.0, v.getX(), 1.0e-10);
        assertEquals(2.0, v.getY(), 1.0e-10);
        assertEquals(-3.0, v.getZ(), 1.0e-10);
        assertEquals(0.0, v.getR(), 1.0e-10);
    }

    /**
     * Test of toJson method, of class FuzzyPoint.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        FuzzyPoint v = FuzzyPoint.of(1.0, 2.0, -1.0, 2.0);
        assertThat(FuzzyPoint.fromJson(FuzzyPoint.toJson(v)).get(), fuzzyPointOf(v));
    }

    /**
     * Test of fromJson method, of class FuzzyPoint.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        assertThat(FuzzyPoint.fromJson("{x:1.0, y:2.0, z:-1.0, r:2.0}").get(), fuzzyPointOf(1.0, 2.0, -1.0, 2.0));
    }

    /**
     * Test of getR method, of class FuzzyPoint.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        FuzzyPoint v = FuzzyPoint.of(Point.of(1.0, 2.0, -3.0), 2.0);
        assertEquals(2.0, v.getR(), 1.0e-10);
    }


    /**
     * Test of getVector method, of class FuzzyPoint.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        FuzzyPoint instance = FuzzyPoint.of(1.0, 2.0, -3.0, 4.0);
        assertThat(instance.getVector(), is(fuzzyVectorOf(1.0, 2.0, -3.0, 4.0)));
    }

    /**
     * Test of move method, of class FuzzyPoint.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        FuzzyPoint instance = FuzzyPoint.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.move(Vector.of(3.0, 4.0, 5.0)), is(fuzzyPointOf(4.0, 6.0, 2.0, 1.0)));
        assertThat(instance.move(FuzzyVector.crisp(3.0, 4.0, 5.0)), is(fuzzyPointOf(4.0, 6.0, 2.0, 1.0)));
        assertThat(instance.move(FuzzyVector.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyPointOf(4.0, 6.0, 2.0, 3.0)));
    }

    /**
     * Test of diff method, of class FuzzyPoint.
     */
    @Test
    public void testDiff() {
        System.out.println("diff");
        FuzzyPoint instance = FuzzyPoint.of(1.0, 2.0, -3.0, 1.0);
        assertThat(instance.diff(Point.of(3.0, 4.0, 5.0)), is(fuzzyVectorOf(-2.0, -2.0, -8.0, 1.0)));
        assertThat(instance.diff(FuzzyPoint.crisp(3.0, 4.0, 5.0)), is(fuzzyVectorOf(-2.0, -2.0, -8.0, 1.0)));
        assertThat(instance.diff(FuzzyPoint.of(3.0, 4.0, 5.0, 2.0)), is(fuzzyVectorOf(-2.0, -2.0, -8.0, 3.0)));
    }

    /**
     * Test of membership method, of class FuzzyPoint.
     */
    @Test
    public void testMembership() {
        System.out.println("membership");
        Point p1 = Point.of(1.0, 1.0);
        Point p2 = Point.of(1.0, 0.0);
        Point p3 = Point.of(1.0, -1.0);
        Point p4 = Point.of(0.0, 0.0);
        FuzzyPoint instance = FuzzyPoint.of(1.0, 1.0, 0.0, 2.0);
        
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
        
        
        assertEquals(1.0, FuzzyPoint.crisp(0.0, 0.0).membership(Point.of(0.0, 0.0)).getValue(), 1.0e-10);
        assertEquals(0.0, FuzzyPoint.crisp(0.0, 0.0).membership(Point.of(1.0, 0.0)).getValue(), 1.0e-10);
    }

    /**
     * Test of possibility method, of class FuzzyPoint.
     */
    @Test
    public void testPossibility() {
        System.out.println("possibility");
        FuzzyPoint p1 = FuzzyPoint.of(0.0, 1.0, 0.0, 2.0);
        FuzzyPoint p2 = FuzzyPoint.of(0.0, 2.0, 0.0, 2.0);
        FuzzyPoint p3 = FuzzyPoint.of(0.0, 3.0, 0.0, 2.0);
        FuzzyPoint p4 = FuzzyPoint.of(2.0, 2.0, 0.0, 2.0);
        FuzzyPoint instance = FuzzyPoint.of(0.0, 0.0, 0.0, 2.0);
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
        FuzzyPoint p1 = FuzzyPoint.of(0.0, 0.0, 0.0, 2.0);
        FuzzyPoint p2 = FuzzyPoint.of(1.0, 0.0, 0.0, 2.0);
        FuzzyPoint p3 = FuzzyPoint.of(2.0, 0.0, 0.0, 2.0);
        FuzzyPoint p4 = FuzzyPoint.of(3.0, 0.0, 0.0, 5.0);
        FuzzyPoint instance = FuzzyPoint.of(0.0, 0.0, 0.0, 2.0);
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
     * Test of divide method, of class FuzzyPoint.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        assertThat(FuzzyPoint.crisp(1.0, -2.0, 2.0).divide(0.3, Point.of(2.0, -4.0, 0.0)), is(fuzzyPointOf(1.3, -2.6, 1.4, 0.0)));
        assertThat(FuzzyPoint.crisp(1.0, -2.0, 2.0).divide(-1.0, Point.of(2.0, -4.0, 0.0)), is(fuzzyPointOf(0.0, 0.0, 4.0, 0.0)));
        assertThat(FuzzyPoint.crisp(1.0, -2.0, 2.0).divide(2.0, Point.of(2.0, -4.0, 0.0)), is(fuzzyPointOf(3.0, -6.0, -2.0, 0.0)));
        assertThat(FuzzyPoint.crisp(1.0, -2.0, 2.0).divide(0.0, Point.of(2.0, -4.0, 0.0)), is(fuzzyPointOf(1.0, -2.0, 2.0, 0.0)));
        assertThat(FuzzyPoint.crisp(1.0, -2.0, 2.0).divide(1.0, Point.of(2.0, -4.0, 0.0)), is(fuzzyPointOf(2.0, -4.0, 0.0, 0.0)));

        assertThat(FuzzyPoint.of(1.0, -2.0, 2.0, 2.0).divide(0.3, FuzzyPoint.of(2.0, -4.0, 0.0, 0.0)), is(fuzzyPointOf(1.3, -2.6, 1.4, 1.4)));
        assertThat(FuzzyPoint.of(1.0, -2.0, 2.0, 2.0).divide(-1.0, FuzzyPoint.of(2.0, -4.0, 0.0, 3.0)), is(fuzzyPointOf(0.0, 0.0, 4.0, 7.0)));
        assertThat(FuzzyPoint.of(1.0, -2.0, 2.0, 2.0).divide(2.0, FuzzyPoint.of(2.0, -4.0, 0.0, 1.0)), is(fuzzyPointOf(3.0, -6.0, -2.0, 4.0)));
        assertThat(FuzzyPoint.of(1.0, -2.0, 2.0, 2.0).divide(0.0, FuzzyPoint.of(2.0, -4.0, 0.0, 3.0)), is(fuzzyPointOf(1.0, -2.0, 2.0, 2.0)));
        assertThat(FuzzyPoint.of(1.0, -2.0, 2.0, 2.0).divide(1.0, FuzzyPoint.of(2.0, -4.0, 0.0, 4.0)), is(fuzzyPointOf(2.0, -4.0, 0.0, 4.0)));
    }
}
