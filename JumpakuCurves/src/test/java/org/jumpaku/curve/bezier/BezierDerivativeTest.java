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
import static org.jumpaku.curve.bezier.BezierDerivativeMatcher.bezierDerivativeOf;
import static org.jumpaku.affine.VectorMatcher.vectorOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class BezierDerivativeTest {

    /**
     * Test of create method, of class BezierDerivative.
     */
    @Test
    public void testCreate_Bezier() {
        System.out.println("create");
        Array<Vector> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.9);
        BezierDerivative result = BezierDerivative.create(Bezier.create(domain, controlVectors.map(v -> Point.crisp(v.getX(), v.getY()))));
        assertThat(result.getControlVectors().get(0), is(vectorOf(controlVectors.get(0))));
        assertThat(result.getControlVectors().get(1), is(vectorOf(controlVectors.get(1))));
        assertThat(result.getControlVectors().get(2), is(vectorOf(controlVectors.get(2))));
        assertThat(result.getControlVectors().get(3), is(vectorOf(controlVectors.get(3))));
        assertEquals(controlVectors.size(), result.getControlVectors().size());
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);  
    }

    /**
     * Test of create method, of class BezierDerivative.
     */
    @Test
    public void testCreate_Array_Interval() {
        System.out.println("create");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.9);
        BezierDerivative result = BezierDerivative.create(domain, controlVectors);
        assertThat(result.getControlVectors().get(0), is(vectorOf(controlVectors.get(0))));
        assertThat(result.getControlVectors().get(1), is(vectorOf(controlVectors.get(1))));
        assertThat(result.getControlVectors().get(2), is(vectorOf(controlVectors.get(2))));
        assertThat(result.getControlVectors().get(3), is(vectorOf(controlVectors.get(3))));
        assertEquals(controlVectors.size(), result.getControlVectors().size());
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);  
    }

    /**
     * Test of getDomain method, of class BezierDerivative.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.9);
        BezierDerivative result = BezierDerivative.create(domain, controlVectors);
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        BezierDerivative b4 = BezierDerivative.create(Interval.ZERO_ONE, Array.of(Vector.crisp(-2.0, 0.0), Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 2.0), Vector.crisp(1.0, 0.0), Vector.crisp(2.0, 0.0)));
        assertThat(b4.evaluate(0.0),  is(vectorOf(Vector.crisp(-2.0,     0.0))));
        assertThat(b4.evaluate(0.25), is(vectorOf(Vector.crisp(-1.0, 27/64.0))));
        assertThat(b4.evaluate(0.5),  is(vectorOf(Vector.crisp( 0.0,    0.75))));
        assertThat(b4.evaluate(0.75), is(vectorOf(Vector.crisp( 1.0, 27/64.0))));
        assertThat(b4.evaluate(1.0),  is(vectorOf(Vector.crisp( 2.0,     0.0))));
    }
    

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException0() {
        System.out.println("evaluate");    
        BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(1.0, 1.0)))
               .evaluate(0.0);
    }
    
    /**
     * Test of differentiate method, of class BezierDerivative.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        BezierDerivative instance = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative expResult = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(0.0, 3.0), Vector.crisp(3.0, -3.0), Vector.crisp(0.0, 3.0)));
        assertThat(instance.differentiate(), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of restrict method, of class BezierDerivative.
     */
    @Test
    public void testRestrict() {
        System.out.println("restrict");
        BezierDerivative instance = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative expResult = BezierDerivative.create(Interval.of(0.2, 0.5), Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        assertThat(instance.restrict(0.2, 0.5), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of reverse method, of class BezierDerivative.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        BezierDerivative instance = BezierDerivative.create(Interval.of(0.2, 0.5), Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative expResult = BezierDerivative.create(Interval.of(0.5, 0.8), Array.of(Vector.crisp(1.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(0.0, 0.0)));
        assertThat(instance.reverse(), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of getControlVectors method, of class BezierDerivative.
     */
    @Test
    public void testGetControlVectors() {
        System.out.println("getControlVectors");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.9);
        BezierDerivative result = BezierDerivative.create(domain, controlVectors);
        assertThat(result.getControlVectors().get(0), is(vectorOf(controlVectors.get(0))));
        assertThat(result.getControlVectors().get(1), is(vectorOf(controlVectors.get(1))));
        assertThat(result.getControlVectors().get(2), is(vectorOf(controlVectors.get(2))));
        assertThat(result.getControlVectors().get(3), is(vectorOf(controlVectors.get(3))));
        assertEquals(controlVectors.size(), result.getControlVectors().size());
    }

    /**
     * Test of getDegree method, of class BezierDerivative.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Array<Vector.Crisp> controlVectors = Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        BezierDerivative result = BezierDerivative.create(domain, controlVectors);
        assertEquals(3, result.getDegree().intValue());
    }

    /**
     * Test of elevate method, of class BezierDerivative.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        BezierDerivative instance = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 2.0), Vector.crisp(1.0, 0.0)));
        BezierDerivative expected = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1/3.0, 4/3.0), Vector.crisp(1/3.0, 4/3.0), Vector.crisp(1.0, 0.0)));
        assertThat(instance.elevate(), is(bezierDerivativeOf(expected)));
    }

    /**
     * Test of reduce method, of class BezierDerivative.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");
        BezierDerivative b1 = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(-1.0, -1.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative e1 = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(0.0, 0.0)));
        assertThat(b1.reduce(), is(bezierDerivativeOf(e1)));

        BezierDerivative b2 = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 0.0), Vector.crisp(1.0, 0.0)));
        BezierDerivative e2 = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(1.0, 0.0)));
        assertThat(b2.reduce(), is(bezierDerivativeOf(e2)));

        BezierDerivative b3 = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(-1/3.0, 4/3.0), Vector.crisp(1/3.0, 4/3.0), Vector.crisp(1.0, 0.0)));
        BezierDerivative e3 = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(-1.0, 0.0), Vector.crisp(0.0, 2.0), Vector.crisp(1.0, 0.0)));
        assertThat(b3.reduce(), is(bezierDerivativeOf(e3)));
    }

    /**
     * Test of subdivide method, of class BezierDerivative.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        Double t = 0.25;
        BezierDerivative instance = BezierDerivative.create(Interval.of(0.2, 0.9), Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 1.0), Vector.crisp(1.0, 0.0), Vector.crisp(1.0, 1.0)));
        BezierDerivative first = instance.subdivide(t)._1();
        BezierDerivative second = instance.subdivide(t)._2();
        assertThat(first, is(bezierDerivativeOf(BezierDerivative.create(Interval.of(1.0/20, 1.0), Array.of(Vector.crisp(0.0, 0.0), Vector.crisp(0.0, 0.25), Vector.crisp(1/16.0, 3/8.0), Vector.crisp(5/32.0, 7/16.0))))));
        assertThat(second, is(bezierDerivativeOf(BezierDerivative.create(Interval.of(0.0, 9.0/40), Array.of(Vector.crisp(5/32.0, 7/16.0), Vector.crisp(7/16.0, 5/8.0), Vector.crisp(1.0, 0.250), Vector.crisp(1.0, 1.0))))));
    }
}
