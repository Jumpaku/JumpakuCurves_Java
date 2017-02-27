/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class BezierTest {
    
    public BezierTest() {
    }

    /**
     * Test of create method, of class Bezier.
     */
    @Test
    public void testCreate_Array_Interval() {
        System.out.println("create");
        Array<? extends Point> controlPoints = Array.of(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        Bezier result = Bezier.create(controlPoints, domain);
        assertTrue(controlPoints.zipWith(result.getControlPoints(), (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && controlPoints.size() == result.getControlPoints().size());
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of create method, of class Bezier.
     */
    @Test
    public void testCreate_PointArr() {
        System.out.println("create");
        Point[] controlPoints = {Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0)};
        Bezier result = Bezier.create(controlPoints);
        assertTrue(Array.of(controlPoints).zipWith(result.getControlPoints(), (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && controlPoints.length == result.getControlPoints().size());
        assertEquals(0.0, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(1.0, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of toJson method, of class Bezier.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Bezier expected = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Bezier actual = new JsonBezier().fromJson(new JsonBezier().toJson(expected)).get();
        assertThat(actual, is(bezierOf(expected)));
    }

    /**
     * Test of toJson method, of class Bezier.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Bezier actual = Bezier.fromJson("{\"controlPoints\":[{\"x\":0.0,\"y\":0.0,\"z\":0.0},{\"x\":0.0,\"y\":1.0,\"z\":0.0},{\"x\":1.0,\"y\":0.0,\"z\":0.0},{\"x\":1.0,\"y\":1.0,\"z\":0.0}],\"interval\":{\"begin\":0.0,\"end\":1.0}}").get();
        Bezier expected = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        assertThat(actual, is(bezierOf(expected)));
    }

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");    

        Bezier b0 = Bezier.create(Point.of(1.0, 1.0));
        assertTrue(Point.equals(Point.of(1.0, 1.0), b0.evaluate(0.0), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.0, 1.0), b0.evaluate(0.25), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.0, 1.0), b0.evaluate(0.5), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.0, 1.0), b0.evaluate(0.75), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.0, 1.0), b0.evaluate(1.0), 1.0e-10));

        Bezier b1 = Bezier.create(Point.of(-1.0, -1.0), Point.of(1.0, 1.0));
        assertTrue(Point.equals(Point.of(-1.0, -1.0), b1.evaluate(0.0), 1.0e-10));
        assertTrue(Point.equals(Point.of(-0.5, -0.5), b1.evaluate(0.25), 1.0e-10));
        assertTrue(Point.equals(Point.of( 0.0,  0.0), b1.evaluate(0.5), 1.0e-10));
        assertTrue(Point.equals(Point.of( 0.5,  0.5), b1.evaluate(0.75), 1.0e-10));
        assertTrue(Point.equals(Point.of( 1.0,  1.0), b1.evaluate(1.0), 1.0e-10));

        Bezier b2 = Bezier.create(Point.of(-1.0, 0.0), Point.of(0.0, 2.0), Point.of(1.0, 0.0));
        assertTrue(Point.equals(Point.of(-1.0,   0.0), b2.evaluate(0.0), 1.0e-10));
        assertTrue(Point.equals(Point.of(-0.5,  0.75), b2.evaluate(0.25), 1.0e-10));
        assertTrue(Point.equals(Point.of( 0.0,   1.0), b2.evaluate(0.5), 1.0e-10));
        assertTrue(Point.equals(Point.of( 0.5,  0.75), b2.evaluate(0.75), 1.0e-10));
        assertTrue(Point.equals(Point.of( 1.0,   0.0), b2.evaluate(1.0), 1.0e-10));

        Bezier b3 = Bezier.create(Point.of(-1.0, 1.0), Point.of(-1.0, -1.0), Point.of(1.0, 1.0), Point.of(1.0, -1.0));
        assertTrue(Point.equals(Point.of(      -1.0,    1.0), b3.evaluate(0.0), 1.0e-10));
        assertTrue(Point.equals(Point.of(-11.0/16.0,  0.125), b3.evaluate(0.25), 1.0e-10));
        assertTrue(Point.equals(Point.of(       0.0,    0.0), b3.evaluate(0.5), 1.0e-10));
        assertTrue(Point.equals(Point.of( 11.0/16.0, -0.125), b3.evaluate(0.75), 1.0e-10));
        assertTrue(Point.equals(Point.of(       1.0,   -1.0), b3.evaluate(1.0), 1.0e-10));

        Bezier b4 = Bezier.create(Point.of(-2.0, 0.0), Point.of(-1.0, 0.0), Point.of(0.0, 2.0), Point.of(1.0, 0.0), Point.of(2.0, 0.0));
        assertTrue(Point.equals(Point.of(-2.0,     0.0), b4.evaluate(0.0), 1.0e-10));
        assertTrue(Point.equals(Point.of(-1.0, 27/64.0), b4.evaluate(0.25), 1.0e-10));
        assertTrue(Point.equals(Point.of( 0.0,    0.75), b4.evaluate(0.5), 1.0e-10));
        assertTrue(Point.equals(Point.of( 1.0, 27/64.0), b4.evaluate(0.75), 1.0e-10));
        assertTrue(Point.equals(Point.of( 2.0,     0.0), b4.evaluate(1.0), 1.0e-10));
    }
    

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException0() {
        System.out.println("evaluate");    
        Bezier.create(
                Array.of(Point.of(1.0, 1.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException1() {
        System.out.println("evaluate");    
        Bezier.create(
                Array.of(Point.of(-1.0, -1.0), Point.of(1.0, 1.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException2() {
        System.out.println("evaluate");    
        Bezier.create(
                Array.of(Point.of(-1.0, 0.0), Point.of(0.0, 2.0), Point.of(1.0, 0.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException3() {
        System.out.println("evaluate");    
        Bezier.create(
                Array.of(Point.of(-1.0, 1.0), Point.of(-1.0, -1.0), Point.of(1.0, 1.0), Point.of(1.0, -1.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException4() {
        System.out.println("evaluate");    
        Bezier.create(
                Array.of(Point.of(-2.0, 0.0), Point.of(-1.0, 0.0), Point.of(0.0, 2.0), Point.of(1.0, 0.0), Point.of(2.0, 0.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
}
