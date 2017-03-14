/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.affine.FuzzyPointMatcher;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import static org.jumpaku.affine.FuzzyPointMatcher.fuzzyPointOf;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.jumpaku.affine.FuzzyPointMatcher.fuzzyPointOf;

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
        Array<FuzzyPoint> controlPoints = Array.of(FuzzyPoint.of(0.0, 0.0), FuzzyPoint.of(0.0, 1.0), FuzzyPoint.of(1.0, 0.0), FuzzyPoint.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.9);
        Bezier result = Bezier.create(domain, controlPoints);
        assertThat(result.getControlPoints().get(0), is(FuzzyPointMatcher.fuzzyPointOf(controlPoints.get(0))));
        assertThat(result.getControlPoints().get(1), is(FuzzyPointMatcher.fuzzyPointOf(controlPoints.get(1))));
        assertThat(result.getControlPoints().get(2), is(FuzzyPointMatcher.fuzzyPointOf(controlPoints.get(2))));
        assertThat(result.getControlPoints().get(3), is(FuzzyPointMatcher.fuzzyPointOf(controlPoints.get(3))));
        assertEquals(result.getControlPoints().size(), controlPoints.size());
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of create method, of class Bezier.
     */
    @Test
    public void testCreate_PointArr() {
        System.out.println("create");
        Bezier result = Bezier.create(FuzzyPoint.of(0.0, 0.0), FuzzyPoint.of(0.0, 1.0), Point.of(1.0, 0.0), FuzzyPoint.of(1.0, 1.0, 2.0, 3.0));
        assertThat(result.getControlPoints().get(0), is(FuzzyPointMatcher.fuzzyPointOf(0.0, 0.0, 0.0, 0.0)));
        assertThat(result.getControlPoints().get(1), is(FuzzyPointMatcher.fuzzyPointOf(0.0, 0.0, 0.0, 1.0)));
        assertThat(result.getControlPoints().get(2), is(FuzzyPointMatcher.fuzzyPointOf(1.0, 0.0, 0.0, 0.0)));
        assertThat(result.getControlPoints().get(3), is(FuzzyPointMatcher.fuzzyPointOf(1.0, 1.0, 2.0, 3.0)));
        assertEquals(4, result.getControlPoints().size());
        assertEquals(0.0, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(1.0, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of toJson method, of class Bezier.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Bezier expected = Bezier.create(FuzzyPoint.of(0.0, 0.0, 1.0), FuzzyPoint.of(0.0, 1.0, 0.5), FuzzyPoint.of(1.0, 0.0, 2.0), FuzzyPoint.crisp(1.0, 1.0));
        Bezier actual = new JsonBezier().fromJson(new JsonBezier().toJson(expected)).get();
        assertThat(actual, is(bezierOf(expected)));
    }

    /**
     * Test of toJson method, of class Bezier.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Bezier actual = Bezier.fromJson(
                "{interval:{begin:0.0,end:1.0}, controlPoints:[{x:0.0,y:0.0,z:0.0,r:1.0},{x:0.0,y:1.0,z:0.0,r:0.5},{x:1.0,y:0.0,z:0.0,r:2.0},{x:1.0,y:1.0,z:0.0,r:0.0}]}").get();
        Bezier expected = Bezier.create(FuzzyPoint.of(0.0, 0.0, 1.0), FuzzyPoint.of(0.0, 1.0, 0.5), FuzzyPoint.of(1.0, 0.0, 2.0), FuzzyPoint.crisp(1.0, 1.0));
        assertThat(actual, is(bezierOf(expected)));
    }

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");    
        Bezier b4 = Bezier.create(FuzzyPoint.of(-2.0, 0.0, 1.0), FuzzyPoint.of(-1.0, 0.0, 2.0), Point.of(0.0, 2.0), FuzzyPoint.of(1.0, 0.0, 2.0), FuzzyPoint.of(2.0, 0.0, 1.0));
        assertThat(b4.evaluate(0.0),  is(fuzzyPointOf(-2.0,     0.0, 0.0, 1.0)));
        assertThat(b4.evaluate(0.25), is(fuzzyPointOf(-1.0, 27/64.0, 0.0, 161.0/128)));
        assertThat(b4.evaluate(0.5),  is(fuzzyPointOf( 0.0,    0.75, 0.0, 9.0/8)));
        assertThat(b4.evaluate(0.75), is(fuzzyPointOf( 1.0, 27/64.0, 0.0, 161.0/128)));
        assertThat(b4.evaluate(1.0),  is(fuzzyPointOf( 2.0,     0.0, 0.0, 1.0)));
    }
    

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException() {
        System.out.println("evaluate");    
        Bezier.create(Interval.of(0.2, 0.9), FuzzyPoint.of(-2.0, 0.0, 1.0), FuzzyPoint.of(-1.0, 0.0, 2.0), Point.of(0.0, 2.0), FuzzyPoint.of(1.0, 0.0, 2.0), FuzzyPoint.of(2.0, 0.0, 1.0))
                .evaluate(0.1);
    }
}
