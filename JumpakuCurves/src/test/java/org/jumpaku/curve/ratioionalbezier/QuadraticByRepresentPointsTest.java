/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;

import javaslang.collection.Stream;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.WeightedPoint;
import org.jumpaku.curve.Derivative;
import org.jumpaku.curve.Interval;

import static org.jumpaku.affine.VectorMatcher.vectorOf;
import static org.jumpaku.curve.ratioionalbezier.QuadraticByRepresentPointsMatcher.quadraticByRepresentPointOf;
import static org.jumpaku.curve.ratioionalbezier.RationalBezierMatcher.rationalBezierOf;
import static org.jumpaku.affine.PointMatcher.pointOf;
import static org.jumpaku.affine.WeightedPointMatcher.weightedPointOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class QuadraticByRepresentPointsTest {
    
    private static final Double R2 = Math.sqrt(2);
    /**
     * Test of evaluate method, of class ConicSection.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        QuadraticByRepresentPoints instance = new QuadraticByRepresentPoints(
                R2/2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0));

        assertThat(instance.evaluate(0.0),  is(pointOf(0.0,                1.0, 0.0,                1.0)));
        assertThat(instance.evaluate(0.25), is(pointOf((3*R2+1)/(3*R2+10), (3*R2+9)/(3*R2+10), 0.0, (24+6*R2)/(10+3*R2))));
        assertThat(instance.evaluate(0.5),  is(pointOf(R2/2,               R2/2, 0.0,               2.0)));
        assertThat(instance.evaluate(0.75), is(pointOf((3*R2+9)/(3*R2+10), (3*R2+1)/(3*R2+10), 0.0, (32+6*R2)/(10+3*R2))));
        assertThat(instance.evaluate(1.0),  is(pointOf(1.0,                0.0, 0.0,                3.0)));
    }

    /**
     * Test of getDomain method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Interval result = new QuadraticByRepresentPoints(
                -R2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0))
                .getDomain();
        assertEquals(0.0, result.getBegin(), 1.0e-10);
        assertEquals(1.0, result.getEnd(), 1.0e-10);
    }

    /**
     * Test of differentiate method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        Point[] ps = new Point[]{
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0)
        };

        Derivative derivative = new QuadraticByRepresentPoints(R2/2, ps[0], ps[1], ps[2])
                .differentiate();

        assertThat(derivative.evaluate(0.0),  is(vectorOf(R2,                                0.0,                           0.0, 0.0)));
        assertThat(derivative.evaluate(0.25), is(vectorOf((40-12*R2)*(6+72*R2)/(41*41),   (40-12*R2)*(-54+8*R2)/(41*41), 0.0, 0.0)));
        assertThat(derivative.evaluate(0.5),  is(vectorOf(4-2*R2,                         -4+2*R2,                       0.0, 0.0)));
        assertThat(derivative.evaluate(0.75), is(vectorOf(-(40-12*R2)*(-54+8*R2)/(41*41), -(40-12*R2)*(6+72*R2)/(41*41), 0.0, 0.0)));
        assertThat(derivative.evaluate(1.0),  is(vectorOf(0.0,                            -R2,                              0.0, 0.0)));
    }

    /**
     * Test of reverse method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        QuadraticByRepresentPoints instance = new QuadraticByRepresentPoints(
                R2/2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0));
        QuadraticByRepresentPoints expResult = new QuadraticByRepresentPoints(
                R2/2,
                Point.fuzzy(1.0, 0.0, 3.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        QuadraticByRepresentPoints result = instance.reverse();
        assertThat(result, is(quadraticByRepresentPointOf(expResult)));
    }

    /**
     * Test of getDegree method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        int result = new QuadraticByRepresentPoints(
                R2/2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0)).getDegree();
        assertEquals(2, result);
    }

    /**
     * Test of toString method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        QuadraticByRepresentPoints expected = new QuadraticByRepresentPoints(
                Math.sqrt(2.0)/2,
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        QuadraticByRepresentPoints actual = new JsonQuadraticByRepresentPoints().fromJson(expected.toString()).get();
        assertThat(actual, is(quadraticByRepresentPointOf(expected)));
    }
    
    /**
     * Test of getRepresentPoints method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testGetRepresentPoints() {
        System.out.println("getRepresentPoints");
        Point[] ps = new Point[]{
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0)
        };

        Array<Point> rp = new QuadraticByRepresentPoints(R2/2, ps[0], ps[1], ps[2])
                .getRepresentPoints();

        assertThat(rp.get(0), is(pointOf(ps[0])));
        assertThat(rp.get(1), is(pointOf(ps[1])));
        assertThat(rp.get(2), is(pointOf(ps[2])));
        assertEquals(3, rp.size());
    }

    /**
     * Test of toCrispRationalBezier method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testToCrispRationalBezier() {
        System.out.println("toCrispRationalBezier");
        RationalBezier result = new QuadraticByRepresentPoints(
                R2/2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0))
                .toCrispRationalBezier();
        RationalBezier expected = RationalBezier.create(
                new WeightedPoint(Point.fuzzy(0.0, 1.0, 0.0), 1.0),
                new WeightedPoint(Point.fuzzy(1.0, 1.0, 0.0), R2/2),
                new WeightedPoint(Point.fuzzy(1.0, 0.0, 0.0), 1.0));

        assertThat(result, is(rationalBezierOf(expected)));
    }

    /**
     * Test of createCrispRationalBezier method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testCreateCrispControlPoints() {
        System.out.println("createCrispControlPoints");
        Array<WeightedPoint> result = QuadraticByRepresentPoints.createCrispControlPoints(
                R2/2,
                Stream.of(
                        Point.fuzzy(0.0, 1.0, 1.0),
                        Point.fuzzy(R2/2, R2/2, 2.0),
                        Point.fuzzy(1.0, 0.0, 3.0)));

        assertThat(result.get(0), is(weightedPointOf(Point.crisp(0.0, 1.0), 1.0)));
        assertThat(result.get(1), is(weightedPointOf(Point.crisp(1.0, 1.0), R2/2)));
        assertThat(result.get(2), is(weightedPointOf(Point.crisp(1.0, 0.0), 1.0)));
        assertEquals(3, result.size());
    }

    /**
     * Test of getWeight method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        double result = new QuadraticByRepresentPoints(
                R2/2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0)).getWeight();
        assertEquals(R2/2, result, 1.0e-10);
    }

    /**
     * Test of complement method, of class QuadraticByRepresentPoints.
     */
    @Test
    public void testComplement() {
        System.out.println("complement");
        QuadraticByRepresentPoints instance = new QuadraticByRepresentPoints(
                R2/2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0));
        QuadraticByRepresentPoints expResult = new QuadraticByRepresentPoints(
                -R2/2,
                Point.fuzzy(0.0, 1.0, 1.0),
                Point.fuzzy(R2/2, R2/2, 2.0),
                Point.fuzzy(1.0, 0.0, 3.0));
        QuadraticByRepresentPoints result = instance.complement();
        assertThat(result, is(quadraticByRepresentPointOf(expResult)));
    }
}
