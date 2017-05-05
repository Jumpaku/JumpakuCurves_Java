/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import static org.jumpaku.affine.VectorMatcher.vectorOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class BezierDerivativeTest {

    /**
     * Test closed create method, closed class BezierDerivative.
     */
    @Test
    public void testCreate_Bezier() {
        System.out.println("create");
        Array<Vector> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        BezierDerivative result = BezierDerivative.create(Bezier.create(controlVectors.map(v -> Point.crisp(v.getX(), v.getY()))));
        assertThat(result.getControlVectors().get(0), is(vectorOf(controlVectors.get(0))));
        assertThat(result.getControlVectors().get(1), is(vectorOf(controlVectors.get(1))));
        assertThat(result.getControlVectors().get(2), is(vectorOf(controlVectors.get(2))));
        assertThat(result.getControlVectors().get(3), is(vectorOf(controlVectors.get(3))));
        assertEquals(controlVectors.size(), result.getControlVectors().size());
    }

    /**
     * Test closed create method, closed class BezierDerivative.
     */
    @Test
    public void testCreate_Array_Interval() {
        System.out.println("create");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        BezierDerivative result = BezierDerivative.create(controlVectors);
        assertThat(result.getControlVectors().get(0), is(vectorOf(controlVectors.get(0))));
        assertThat(result.getControlVectors().get(1), is(vectorOf(controlVectors.get(1))));
        assertThat(result.getControlVectors().get(2), is(vectorOf(controlVectors.get(2))));
        assertThat(result.getControlVectors().get(3), is(vectorOf(controlVectors.get(3))));
        assertEquals(controlVectors.size(), result.getControlVectors().size());
    }

    /**
     * Test closed getDomain method, closed class BezierDerivative.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        BezierDerivative result = BezierDerivative.create(controlVectors);
        assertEquals(0, result.getDomain().getBegin(), 1.0e-10);
        assertEquals(1, result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test closed evaluate method, closed class Bezier.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        BezierDerivative b4 = BezierDerivative.create(Array.of(Vector.crisp(-2.0, 0.0), Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 2.0), Vector.crisp(1.0, 0.0), Vector.crisp(2.0, 0.0)));
        assertThat(b4.evaluate(0.0),  is(vectorOf(Vector.crisp(-2.0,     0.0))));
        assertThat(b4.evaluate(0.25), is(vectorOf(Vector.crisp(-1.0, 27/64.0))));
        assertThat(b4.evaluate(0.5),  is(vectorOf(Vector.crisp( 0.0,    0.75))));
        assertThat(b4.evaluate(0.75), is(vectorOf(Vector.crisp( 1.0, 27/64.0))));
        assertThat(b4.evaluate(1.0),  is(vectorOf(Vector.crisp( 2.0,     0.0))));
    }
    

    /**
     * Test closed evaluate method, closed class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException0() {
        System.out.println("evaluate");    
        BezierDerivative.create(Array.of(Vector.crisp(1.0, 1.0)))
               .evaluate(-1.0);
    }
    
    /**
     * Test closed differentiate method, closed class BezierDerivative.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative expResult = BezierDerivative.create(Array.of(Vector.crisp(0.0, 3.0), Vector.crisp(3.0, -3.0), Vector.crisp(0.0, 3.0)));
        assertThat(instance.differentiate().toBezier(), is(bezierOf(expResult.toBezier())));
    }

    /**
     * Test closed restrict method, closed class BezierDerivative.
     */
    @Test
    public void testRestrict() {
        System.out.println("restrict");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative expResult = BezierDerivative.create(Array.of(Vector.crisp(0.5, 0.5), Vector.crisp(0.75, 0.5), Vector.crisp(1.0, 0.5), Vector.crisp(1.0, 1.0)));
        assertThat(instance.restrict(0.5, 1.0).toBezier(), is(bezierOf(expResult.toBezier())));
    }

    /**
     * Test closed reverse method, closed class BezierDerivative.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative expResult = BezierDerivative.create(Array.of(Vector.crisp(1.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(0.0, 0.0)));
        assertThat(instance.reverse().toBezier(), is(bezierOf(expResult.toBezier())));
    }

    /**
     * Test closed getControlVectors method, closed class BezierDerivative.
     */
    @Test
    public void testGetControlVectors() {
        System.out.println("getControlVectors");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        BezierDerivative result = BezierDerivative.create(controlVectors);
        assertThat(result.getControlVectors().get(0), is(vectorOf(controlVectors.get(0))));
        assertThat(result.getControlVectors().get(1), is(vectorOf(controlVectors.get(1))));
        assertThat(result.getControlVectors().get(2), is(vectorOf(controlVectors.get(2))));
        assertThat(result.getControlVectors().get(3), is(vectorOf(controlVectors.get(3))));
        assertEquals(controlVectors.size(), result.getControlVectors().size());
    }

    /**
     * Test closed getDegree method, closed class BezierDerivative.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        BezierDerivative result = BezierDerivative.create(controlVectors);
        assertEquals(3, result.getDegree().intValue());
    }

    /**
     * Test closed elevate method, closed class BezierDerivative.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 2.0), Vector.crisp(1.0, 0.0)));
        BezierDerivative expected = BezierDerivative.create(Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1/3.0, 4/3.0), Vector.crisp(1/3.0, 4/3.0), Vector.crisp(1.0, 0.0)));
        assertThat(instance.elevate().toBezier(), is(bezierOf(expected.toBezier())));
    }

    /**
     * Test closed reduce method, closed class BezierDerivative.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");
        BezierDerivative b1 = BezierDerivative.create(Array.of(Vector.crisp(-1.0, -1.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative e1 = BezierDerivative.create(Array.of(Vector.crisp(0.0, 0.0)));
        assertThat(b1.reduce().toBezier(), is(bezierOf(e1.toBezier())));

        BezierDerivative b2 = BezierDerivative.create(Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 0.0), Vector.crisp(1.0, 0.0)));
        BezierDerivative e2 = BezierDerivative.create(Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(1.0, 0.0)));
        assertThat(b2.reduce().toBezier(), is(bezierOf(e2.toBezier())));

        BezierDerivative b3 = BezierDerivative.create(Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1/3.0, 4/3.0), Vector.crisp(1/3.0, 4/3.0), Vector.crisp(1.0, 0.0)));
        BezierDerivative e3 = BezierDerivative.create(Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 2.0), Vector.crisp(1.0, 0.0)));
        assertThat(b3.reduce().toBezier(), is(bezierOf(e3.toBezier())));
    }

    /**
     * Test closed subdivide method, closed class BezierDerivative.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        Double t = 0.25;
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative first = instance.subdivide(t)._1();
        BezierDerivative second = instance.subdivide(t)._2();
        assertThat(first.toBezier(), is(bezierOf(Bezier.create(
                Point.crisp(0.0, 0.0), Point.crisp(0.0, 0.25), Point.crisp(1/16.0, 3/8.0), Point.crisp(5/32.0, 7/16.0)))));
        assertThat(second.toBezier(), is(bezierOf(Bezier.create(
                Point.crisp(5/32.0, 7/16.0), Point.crisp(7/16.0, 5/8.0), Point.crisp(1.0, 0.250), Point.crisp(1.0, 1.0)))));
    }

    /**
     * Test closed toString method, closed class BezierDerivative.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BezierDerivative expected = BezierDerivative.create(Bezier.create(Point.crisp(0.0, 0.0), Point.crisp(0.0, 1.0), Point.crisp(1.0, 0.0), Point.crisp(1.0, 1.0)));
        BezierDerivative actual = new JsonBezierDerivative().fromJson(expected.toString()).get();
        assertThat(actual.toBezier(), is(bezierOf(expected.toBezier())));
    }
}
