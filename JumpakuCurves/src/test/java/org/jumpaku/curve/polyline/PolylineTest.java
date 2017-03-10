/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import java.util.Arrays;
import java.util.LinkedList;
import javaslang.collection.Array;
import org.apache.commons.math3.util.Precision;
import org.jumpaku.affine.FuzzyPoint;
import org.jumpaku.affine.FuzzyPointMatcher;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.FuzzyCurve;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class PolylineTest {
    
    public PolylineTest() {
    }

    /**
     * Test create create method, create class Polyline.
     */
    @Test
    public void testCreate_FuzzyPointArr() {
        System.out.println("create");
        Array<? extends FuzzyPoint> points = Array.of(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.create(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        assertTrue(instance.getPoints().zipWith(points, (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && instance.getPoints().size() == points.size()
                && Precision.equals(instance.getDomain().getBegin(), 0.0, 1.0e-10)
                && Precision.equals(instance.getDomain().getEnd(), Math.pow(56, 0.5)+Math.pow(29, 0.5), 1.0e-10));
    }

    /**
     * Test create create method, create class Polyline.
     */
    @Test
    public void testCreate_Iterable() {
        System.out.println("create");
        Array<? extends FuzzyPoint> points = Array.of(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.create(points);
        assertTrue(instance.getPoints().zipWith(points, (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && instance.getPoints().size() == points.size()
                && Precision.equals(instance.getDomain().getBegin(), 0.0, 1.0e-10)
                && Precision.equals(instance.getDomain().getEnd(), Math.pow(56, 0.5)+Math.pow(29, 0.5), 1.0e-10));
    }

    /**
     * Test create toLines method, create class Polyline.
     */
    @Test
    public void testToLines() {
        System.out.println("toLines");
        FuzzyCurve curve = Polyline.create(FuzzyPoint.crisp(-1.0), FuzzyPoint.crisp(-1.0, 1.0), FuzzyPoint.crisp(1.0, 1.0), FuzzyPoint.crisp(1.0));
        Double a = 0.0;
        Array<FuzzyPoint> expected = Array.of(FuzzyPoint.crisp(-1.0), FuzzyPoint.crisp(-1.0, 1.0), FuzzyPoint.crisp(0.0, 1.0), FuzzyPoint.crisp(1.0, 1.0), FuzzyPoint.crisp(1.0));
        LinkedList<FuzzyPoint> result = Polyline.toLines(curve, FuzzyPoint.crisp(-1.0), 0.0, FuzzyPoint.crisp(1.0), 4.0, 1.0e-10);
        assertTrue(expected.zipWith(result, (e, r)->Point.equals(e, r, 1.0e-10)).forAll(b->b)
                && expected.size() == result.size());
    }

    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        Polyline instance = Polyline.create(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        Array<? extends FuzzyPoint> expected = Array.of(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        Array<? extends FuzzyPoint> result = instance.getPoints();
        assertTrue(expected.zipWith(result, (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && expected.size() == result.size());
    }
    
    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        Polyline instance = Polyline.create(FuzzyPoint.of(1.0, 1.0, 2.0), FuzzyPoint.of(-1.0, -1.0, 1.0), FuzzyPoint.of(-1.0, -1.0, 1.0, 0.0));
        assertThat(instance.evaluate(0.0), FuzzyPointMatcher.fuzzyPointOf(1.0, 1.0, 0.0, 2.0));
        assertThat(instance.evaluate(Math.pow(2, 0.5)), FuzzyPointMatcher.fuzzyPointOf(0.0, 0.0, 0.0, 1.5));
        assertThat(instance.evaluate(2*Math.pow(2, 0.5)), FuzzyPointMatcher.fuzzyPointOf(-1.0, -1.0, 0.0, 1.0));
        assertThat(instance.evaluate(2*Math.pow(2, 0.5)+0.5), FuzzyPointMatcher.fuzzyPointOf(-1.0, -1.0, 0.5, 0.5));
        assertThat(instance.evaluate(2*Math.pow(2, 0.5)+1), FuzzyPointMatcher.fuzzyPointOf(-1.0, -1.0, 1.0, 0.0));
    }
    
    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testEvaluateAllByArcLengthParams() {
        System.out.println("evaluateAllByArcLengthParams");
        Polyline instance = Polyline.create(FuzzyPoint.of(-1.0, 1.0, 2.0), FuzzyPoint.of(1.0, 1.0, 1.0), FuzzyPoint.of(1.0, -3.0, 3.0), FuzzyPoint.of(1.0, -3.0, 1.5, 2.0));
        Array<? extends FuzzyPoint> result = instance.evaluateAllByArcLengthParams(6);
        assertThat(result.get(0), FuzzyPointMatcher.fuzzyPointOf(-1.0, 1.0, 0.0, 2.0));
        assertThat(result.get(1), FuzzyPointMatcher.fuzzyPointOf(0.5, 1.0, 0.0, 1.25));
        assertThat(result.get(2), FuzzyPointMatcher.fuzzyPointOf(1.0, 0.0, 0.0, 1.5));
        assertThat(result.get(3), FuzzyPointMatcher.fuzzyPointOf(1.0, -1.5, 0.0, 2.25));
        assertThat(result.get(4), FuzzyPointMatcher.fuzzyPointOf(1.0, -3.0, 0.0, 3.0));
        assertThat(result.get(5), FuzzyPointMatcher.fuzzyPointOf(1.0, -3.0, 1.5, 2.0));
    }

    /**
     * Test of toJson method, of class Polyline.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Polyline pl = Polyline.create(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.fromJson(Polyline.toJson(pl)).get();
        assertThat(instance, PolylineMatcher.polylineOf(pl));
    }

    /**
     * Test of toJson method, of class Polyline.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Polyline pl = Polyline.create(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.fromJson(pl.toString()).get();
        assertThat(instance, PolylineMatcher.polylineOf(pl));
    }

    /**
     * Test of fromJson method, of class Polyline.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Polyline pl = Polyline.create(FuzzyPoint.of(1.0, 2.0, 3.0, 2.0), FuzzyPoint.of(-1.0, -2.0, -3.0, 1.0), FuzzyPoint.of(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.fromJson("{points:[{x:1.0,y:2.0,z:3.0,r:2.0},{x:-1.0,y:-2.0,z:-3.0,r:1.0},{x:1.0,y:1.0,z:1.0,r:0.0}]}").get();
        assertThat(instance, PolylineMatcher.polylineOf(pl));
    }
}
