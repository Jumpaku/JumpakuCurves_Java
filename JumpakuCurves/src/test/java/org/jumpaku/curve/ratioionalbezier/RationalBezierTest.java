/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.WeightedPoint;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;
import org.jumpaku.curve.bezier.Bezier;

import static org.jumpaku.affine.PointMatcher.pointOf;
import static org.jumpaku.affine.VectorMatcher.vectorOf;
import static org.jumpaku.curve.ratioionalbezier.RationalBezierMatcher.rationalBezierOf;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.jumpaku.affine.WeightedPointMatcher.weightedPointOf;

/**
 *
 * @author jumpaku
 */
public class RationalBezierTest {
    
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
        RationalBezier result = RationalBezier.create(points);
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
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

                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0))));
        assertEquals(4, result.getWeightedControlPoints().size());
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
        RationalBezier result = RationalBezier.fromBezier(Bezier.create(points.map(WeightedPoint::getPoint)));
        assertThat(result.getWeightedControlPoints().get(0), is(weightedPointOf(points.get(0))));
        assertThat(result.getWeightedControlPoints().get(1), is(weightedPointOf(points.get(1))));
        assertThat(result.getWeightedControlPoints().get(2), is(weightedPointOf(points.get(2))));
        assertThat(result.getWeightedControlPoints().get(3), is(weightedPointOf(points.get(3))));
        assertEquals(4, result.getWeightedControlPoints().size());
    }

    /**
     * Test of toJson method, of class RationalBezier.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        RationalBezier expected = RationalBezier.create(

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

                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        assertThat(actual, is(rationalBezierOf(expected)));
    }


    static final double R2 = Math.sqrt(2);

    /**
     * Test of evaluate method, of class RationalBezier.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        RationalBezier instance = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(1.0, 1.0, 2.0), 1/R2),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)));

        assertThat(instance.evaluate(0.0),  is(pointOf(0.0,                1.0, 0.0,                1.0)));
        assertThat(instance.evaluate(0.25), is(pointOf((3*R2+1)/(3*R2+10), (3*R2+9)/(3*R2+10), 0.0, (12+6*R2)/(10+3*R2))));
        assertThat(instance.evaluate(0.5),  is(pointOf(1/R2,               1/R2, 0.0,               2.0)));
        assertThat(instance.evaluate(0.75), is(pointOf((3*R2+9)/(3*R2+10), (3*R2+1)/(3*R2+10), 0.0, (28+6*R2)/(10+3*R2))));
        assertThat(instance.evaluate(1.0),  is(pointOf(1.0,                0.0, 0.0,                3.0)));
    }

    /**
     * Test of getDomain method, of class RationalBezier.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Interval result = RationalBezier.create(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0)
        ).getDomain();

        assertEquals(0.0, result.getBegin(), 1.0e-10);
        assertEquals(1.0, result.getEnd(), 1.0e-10);
    }

    /**
     * Test of decasteljau method, of class RationalBezier.
     */
    @Test
    public void testDecasteljau() {
        System.out.println("decasteljau");
        Array<WeightedPoint> wcps = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));

        Array<WeightedPoint> result = RationalBezier.decasteljau(1/3.0, wcps);
        assertThat(result.get(0), is(weightedPointOf(Point.fuzzy(0.0,   0.5,   1.5),    4/3.0)));
        assertThat(result.get(1), is(weightedPointOf(Point.fuzzy(3/7.0, 4/7.0, 17/7.0), 7/3.0)));
        assertThat(result.get(2), is(weightedPointOf(Point.fuzzy(1.0,   0.4,   3.4),    10/3.0)));
        assertEquals(3, result.size());
    }

    /**
     * Test of weightBezier method, of class RationalBezier.
     */
    @Test
    public void testWeightBezier() {
        System.out.println("weightBezier");
        assertEquals(1.44, RationalBezier.weightBezier(0.2, Array.of(1.0, 2.0, 4.0)), 1.0e-10);
    }

    /**
     * Test of differentiate method, of class RationalBezier.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 2.0), 1/R2),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0));

        Derivative derivative = new RationalBezier(points)
                .differentiate();

        assertThat(derivative.evaluate(0.0),  is(vectorOf(R2,                                0.0,                           0.0, 0.0)));
        assertThat(derivative.evaluate(0.25), is(vectorOf((40-12*R2)*(6+72*R2)/(41*41),   (40-12*R2)*(-54+8*R2)/(41*41), 0.0, 0.0)));
        assertThat(derivative.evaluate(0.5),  is(vectorOf(4-2*R2,                         -4+2*R2,                       0.0, 0.0)));
        assertThat(derivative.evaluate(0.75), is(vectorOf(-(40-12*R2)*(-54+8*R2)/(41*41), -(40-12*R2)*(6+72*R2)/(41*41), 0.0, 0.0)));
        assertThat(derivative.evaluate(1.0),  is(vectorOf(0.0,                            -R2,                              0.0, 0.0)));
    }

    /**
     * Test of restrict method, of class RationalBezier.
     */
    @Test
    public void testRestrict_Interval() {
        System.out.println("restrict");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier result = RationalBezier.create(points).restrict(Interval.of(0.3, 0.8));
        assertThat(result, is(rationalBezierOf(
                RationalBezier.create(points))));
    }

    /**
     * Test of restrict method, of class RationalBezier.
     */
    @Test
    public void testRestrict_Double_Double() {
        System.out.println("restrict");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier result = RationalBezier.create(points).restrict(0.3, 0.8);
        assertThat(result, is(rationalBezierOf(
                RationalBezier.create(points))));
    }

    /**
     * Test of reverse method, of class RationalBezier.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier result = RationalBezier.create(points).reverse();
        assertThat(result, is(rationalBezierOf(
                RationalBezier.create(points.reverse()))));
    }

    /**
     * Test of getWeightedControlPoints method, of class RationalBezier.
     */
    @Test
    public void testGetWeightedControlPoints() {
        System.out.println("getWeightedControlPoints");
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
    }

    /**
     * Test of elevate method, of class RationalBezier.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        RationalBezier expResult = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(2-R2, 1.0, (1+2*R2)/(1+R2)), (1+R2)/3),
                        new WeightedPoint(Point.fuzzy(1.0, 2-R2, (3+2*R2)/(1+R2)), (1+R2)/3),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)));

        RationalBezier result = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(1.0, 1.0, 2.0), 1/R2),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)))
                .elevate();

        assertThat(result, is(rationalBezierOf(expResult)));
    }

    /**
     * Test of reduce method, of class RationalBezier.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");

        RationalBezier r3 = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(2-R2, 1.0, (1+2*R2)/(1+R2)), (1+R2)/3),
                        new WeightedPoint(Point.fuzzy(1.0, 2-R2, (3+2*R2)/(1+R2)), (1+R2)/3),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)));
        RationalBezier e3 = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(1.0, 1.0, 2+2*R2), 1/R2),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)));

        RationalBezier r2 = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(1.0, 1.0, 2.0), 1/R2),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)));
        RationalBezier e2 = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)));

        RationalBezier r1 = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 1.0)));
        RationalBezier e1 = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.5, 0.5, 2.0), 1.0)));

        assertThat(r3.reduce(), is(rationalBezierOf(e3)));
        assertThat(r2.reduce(), is(rationalBezierOf(e2)));
        assertThat(r1.reduce(), is(rationalBezierOf(e1)));
    }

    /**
     * Test of subdivide method, of class RationalBezier.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        RationalBezier instance = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(Math.sqrt(3), 1.0, 1.0), 0.5),
                        new WeightedPoint(Point.fuzzy(Math.sqrt(3)/2, -0.5, 1.0), 1.0)));

        Tuple2<RationalBezier, RationalBezier> result = instance.subdivide(0.5);

        RationalBezier first = new RationalBezier(
                Array.of(
                        new WeightedPoint(Point.fuzzy(0.0, 1.0, 1.0), 1.0),
                        new WeightedPoint(Point.fuzzy(Math.sqrt(3)/3, 1.0, 1.0), 0.75),
                        new WeightedPoint(instance.evaluate(0.5), 0.75)));

        RationalBezier second = new RationalBezier(
                Array.of(
                        new WeightedPoint(instance.evaluate(0.5), 0.75),
                        new WeightedPoint(Point.fuzzy(2*Math.sqrt(3)/3, 0.0, 1.0), 0.75),
                        new WeightedPoint(Point.fuzzy(Math.sqrt(3)/2, -0.5, 1.0), 1.0)));

        assertThat(result._1(), is(rationalBezierOf(first)));
        assertThat(result._2(), is(rationalBezierOf(second)));
    }
    /**
     * Test of getDegree method, of class RationalBezier.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Array<WeightedPoint> points = Array.of(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        int result = RationalBezier.create(points).getDegree();
        assertEquals(3, result);
    }


    /**
     * Test of toString method, of class RationalBezier.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        RationalBezier expected = RationalBezier.create(
                new WeightedPoint(Point.fuzzy(0.0, 0.0, 1.0), 1.0),
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 2.0), 2.0),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 3.0), 3.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 4.0), 4.0));
        RationalBezier actual = RationalBezier.fromJson(expected.toString()).get();
        assertThat(actual, is(rationalBezierOf(expected)));
    }
}
