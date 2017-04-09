/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.PointMatcher;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.bezier.Bezier;
import static org.jumpaku.curve.ratioionalbezier.RationalBezierMatcher.rationalBezierOf;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.jumpaku.curve.ratioionalbezier.WeightedPointMatcher.weightedPointOf;

/**
 *
 * @author jumpaku
 */
public class RationalBezierTest {
    
    public RationalBezierTest() {
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Iterable() {
        System.out.println("create");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier result = RationalBezier.create(points);
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
        assertEquals(0.0, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(1.0, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Interval_Iterable() {
        System.out.println("create");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier result = RationalBezier.create(Interval.of(0.2, 0.9), points);
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
        assertEquals(0.2, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(0.9, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_WeightedPointArr() {
        System.out.println("create");
        RationalBezier result = RationalBezier.create(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0))));
        assertEquals(4, result.getWeightedControlPoints().size());
        assertEquals(0.0, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(1.0, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Interval_WeightedPointArr() {
        System.out.println("create");
        RationalBezier result = RationalBezier.create(
                Interval.of(0.2, 0.9), 
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0))));
        assertEquals(4, result.getWeightedControlPoints().size());
        assertEquals(0.2, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(0.9, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_Iterable_Iterable() {
        System.out.println("create");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier result = RationalBezier.create(points.map(WeightedPoint::getPoint), points.map(WeightedPoint::getWeight));
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
        assertEquals(0.0, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(1.0, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of create method, of class RationalBezier.
     */
    @Test
    public void testCreate_3args() {
        System.out.println("create");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier result = RationalBezier.create(
                Interval.of(0.2, 0.9),
                points.map(WeightedPoint::getPoint), points.map(WeightedPoint::getWeight));
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
        assertEquals(0.2, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(0.9, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of byRepresentPoints method, of class RationalBezier.
     */
    @Test
    public void testByRepresentPoints_4args() {
        System.out.println("byRepresentPoints");
        ConicSection result = RationalBezier.byRepresentPoints(
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(result.getRepresentPoints().get(0), is(PointMatcher.pointOf(1.0, 0.0, 0.0, 1.0)));
        assertThat(result.getRepresentPoints().get(1), is(PointMatcher.pointOf(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 0.0, 2.0)));
        assertThat(result.getRepresentPoints().get(2), is(PointMatcher.pointOf(Point.fuzzy(0.0, 1.0, 0.0, 1.0))));
        assertEquals(3, result.getRepresentPoints().size());
        assertEquals(Math.sqrt(2.0)/2, result.getWeight(), 1.0e-10);
        assertEquals(0.0, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(1.0, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of byRepresentPoints method, of class RationalBezier.
     */
    @Test
    public void testByRepresentPoints_5args() {
        System.out.println("byRepresentPoints");
        ConicSection result = RationalBezier.byRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(result.getRepresentPoints().get(0), is(PointMatcher.pointOf(1.0, 0.0, 0.0, 1.0)));
        assertThat(result.getRepresentPoints().get(1), is(PointMatcher.pointOf(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 0.0, 2.0)));
        assertThat(result.getRepresentPoints().get(2), is(PointMatcher.pointOf(Point.fuzzy(0.0, 1.0, 0.0, 1.0))));
        assertEquals(3, result.getRepresentPoints().size());
        assertEquals(Math.sqrt(2.0)/2, result.getWeight(), 1.0e-10);
        assertEquals(0.2, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(0.9, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of fromBezier method, of class RationalBezier.
     */
    @Test
    public void testFromBezier() {
        System.out.println("fromBezier");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 1.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 1.0));
        RationalBezier result = RationalBezier.fromBezier(Bezier.create(Interval.of(0.2, 0.9), points.map(WeightedPoint::getPoint)));
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
        assertEquals(0.2, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(0.9, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of toJson method, of class RationalBezier.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        RationalBezier expected = RationalBezier.create(
                Interval.of(0.2, 0.9),
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier actual = RationalBezier.fromJson(RationalBezier.toJson(expected)).get();
        assertThat(actual, is(rationalBezierOf(expected)));
    }

    /**
     * Test of fromJson method, of class RationalBezier.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        RationalBezier actual = RationalBezier.fromJson(
                "{interval:{begin:0.2,end:0.9},"
                        + "controlPoints:[{x:0.0,y:0.0,z:0.0,r:1.0},{x:0.0,y:1.0,z:0.0,r:2.0},{x:1.0,y:0.0,z:0.0,r:3.0},{x:1.0,y:1.0,z:0.0,r:4.0}],"
                        + "weights:[1.0,2.0,3.0,4.0]}").get();
        RationalBezier expected = RationalBezier.create(
                Interval.of(0.2, 0.9),
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        assertThat(actual, is(rationalBezierOf(expected)));
    }
}
