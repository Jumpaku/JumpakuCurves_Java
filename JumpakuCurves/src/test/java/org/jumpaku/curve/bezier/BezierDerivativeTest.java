/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import javaslang.Tuple2;
import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.bezier.BezierDerivativeMatcher.bezierDerivativeOf;
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
        Array<? extends Vector> controlVectors = Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        BezierDerivative result = BezierDerivative.create(Bezier.create(controlVectors.map(Point::of), domain));
        assertTrue(controlVectors.zipWith(result.getControlVectors(), (e, a)->Vector.equals(e, a, 1.0e-10)).forAll(b->b)
                && controlVectors.size() == result.getControlVectors().size());
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);  
    }

    /**
     * Test of create method, of class BezierDerivative.
     */
    @Test
    public void testCreate_Array_Interval() {
        System.out.println("create");
        Array<? extends Vector> controlVectors = Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        BezierDerivative result = BezierDerivative.create(controlVectors, domain);
        assertTrue(controlVectors.zipWith(result.getControlVectors(), (e, a)->Vector.equals(e, a, 1.0e-10)).forAll(b->b)
                && controlVectors.size() == result.getControlVectors().size());
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);  
    }

    /**
     * Test of getDomain method, of class BezierDerivative.
     */
    @Test
    public void testGetDomain() {
        System.out.println("getDomain");
        Array<? extends Vector> controlVectors = Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        BezierDerivative result = BezierDerivative.create(controlVectors, domain);
        assertEquals(domain.getBegin(), result.getDomain().getBegin(), 1.0e-10);
        assertEquals(domain.getEnd(), result.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");    

        BezierDerivative b0 = BezierDerivative.create(Array.of(Vector.of(1.0, 1.0)), Interval.ZERO_ONE);
        assertTrue(Vector.equals(Vector.of(1.0, 1.0), b0.evaluate(0.0), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(1.0, 1.0), b0.evaluate(0.25), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(1.0, 1.0), b0.evaluate(0.5), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(1.0, 1.0), b0.evaluate(0.75), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(1.0, 1.0), b0.evaluate(1.0), 1.0e-10));

        BezierDerivative b1 = BezierDerivative.create(Array.of(Vector.of(-1.0, -1.0), Vector.of(1.0, 1.0)), Interval.ZERO_ONE);
        assertTrue(Vector.equals(Vector.of(-1.0, -1.0), b1.evaluate(0.0), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(-0.5, -0.5), b1.evaluate(0.25), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 0.0,  0.0), b1.evaluate(0.5), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 0.5,  0.5), b1.evaluate(0.75), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 1.0,  1.0), b1.evaluate(1.0), 1.0e-10));

        BezierDerivative b2 = BezierDerivative.create(Array.of(Vector.of(-1.0, 0.0), Vector.of(0.0, 2.0), Vector.of(1.0, 0.0)), Interval.ZERO_ONE);
        assertTrue(Vector.equals(Vector.of(-1.0,   0.0), b2.evaluate(0.0), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(-0.5,  0.75), b2.evaluate(0.25), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 0.0,   1.0), b2.evaluate(0.5), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 0.5,  0.75), b2.evaluate(0.75), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 1.0,   0.0), b2.evaluate(1.0), 1.0e-10));

        BezierDerivative b3 = BezierDerivative.create(Array.of(Vector.of(-1.0, 1.0), Vector.of(-1.0, -1.0), Vector.of(1.0, 1.0), Vector.of(1.0, -1.0)), Interval.ZERO_ONE);
        assertTrue(Vector.equals(Vector.of(      -1.0,    1.0), b3.evaluate(0.0), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(-11.0/16.0,  0.125), b3.evaluate(0.25), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(       0.0,    0.0), b3.evaluate(0.5), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 11.0/16.0, -0.125), b3.evaluate(0.75), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(       1.0,   -1.0), b3.evaluate(1.0), 1.0e-10));

        BezierDerivative b4 = BezierDerivative.create(Array.of(Vector.of(-2.0, 0.0), Vector.of(-1.0, 0.0), Vector.of(0.0, 2.0), Vector.of(1.0, 0.0), Vector.of(2.0, 0.0)), Interval.ZERO_ONE);
        assertTrue(Vector.equals(Vector.of(-2.0,     0.0), b4.evaluate(0.0), 1.0e-10));
        assertTrue(Vector.equals(Vector.of(-1.0, 27/64.0), b4.evaluate(0.25), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 0.0,    0.75), b4.evaluate(0.5), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 1.0, 27/64.0), b4.evaluate(0.75), 1.0e-10));
        assertTrue(Vector.equals(Vector.of( 2.0,     0.0), b4.evaluate(1.0), 1.0e-10));
    }
    

    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException0() {
        System.out.println("evaluate");    
        BezierDerivative.create(
                Array.of(Vector.of(1.0, 1.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException1() {
        System.out.println("evaluate");    
        BezierDerivative.create(
                Array.of(Vector.of(-1.0, -1.0), Vector.of(1.0, 1.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException2() {
        System.out.println("evaluate");    
        BezierDerivative.create(
                Array.of(Vector.of(-1.0, 0.0), Vector.of(0.0, 2.0), Vector.of(1.0, 0.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException3() {
        System.out.println("evaluate");    
        BezierDerivative.create(
                Array.of(Vector.of(-1.0, 1.0), Vector.of(-1.0, -1.0), Vector.of(1.0, 1.0), Vector.of(1.0, -1.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    /**
     * Test of evaluate method, of class Bezier.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateException4() {
        System.out.println("evaluate");    
        BezierDerivative.create(
                Array.of(Vector.of(-2.0, 0.0), Vector.of(-1.0, 0.0), Vector.of(0.0, 2.0), Vector.of(1.0, 0.0), Vector.of(2.0, 0.0)), Interval.of(0.2, 0.8))
               .evaluate(0.0);
    }
    /**
     * Test of differentiate method, of class BezierDerivative.
     */
    @Test
    public void testDifferentiate() {
        System.out.println("differentiate");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0)), Interval.of(0.2, 0.8));
        BezierDerivative expResult = BezierDerivative.create(Array.of(Vector.of(0.0, 3.0), Vector.of(3.0, -3.0), Vector.of(0.0, 3.0)), Interval.of(0.2, 0.8));
        assertThat(instance.differentiate(), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of restrict method, of class BezierDerivative.
     */
    @Test
    public void testRestrict() {
        System.out.println("restrict");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0)), Interval.of(0.2, 0.8));
        BezierDerivative expResult = BezierDerivative.create(Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0)), Interval.of(0.2, 0.5));
        assertThat(instance.restrict(0.2, 0.5), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of reverse method, of class BezierDerivative.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0)), Interval.ZERO_ONE);
        BezierDerivative expResult = BezierDerivative.create(Array.of(Vector.of(1.0, 1.0), Vector.of(1.0, 0.0), Vector.of(0.0, 1.0), Vector.of(0.0, 0.0)), Interval.ZERO_ONE);
        assertThat(instance.reverse(), is(bezierDerivativeOf(expResult)));
    }

    /**
     * Test of getControlVectors method, of class BezierDerivative.
     */
    @Test
    public void testGetControlVectors() {
        System.out.println("getControlVectors");
        Array<? extends Vector> controlVectors = Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        BezierDerivative result = BezierDerivative.create(controlVectors, domain);
        assertTrue(controlVectors.zipWith(result.getControlVectors(), (e, a)->Vector.equals(e, a, 1.0e-10)).forAll(b->b)
                && controlVectors.size() == result.getControlVectors().size());
    }

    /**
     * Test of getDegree method, of class BezierDerivative.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Array<Vector> controlVectors = Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0));
        Interval domain = Interval.of(0.2, 0.8);
        BezierDerivative result = BezierDerivative.create(controlVectors, domain);
        assertEquals(3, result.getDegree().intValue());
    }

    /**
     * Test of elevate method, of class BezierDerivative.
     */
    @Test
    public void testElevate() {
        System.out.println("elevate");
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.of(-1.0, 0.0), Vector.of(0.0, 2.0), Vector.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        BezierDerivative expected = BezierDerivative.create(Array.of(Vector.of(-1.0, 0.0), Vector.of(-1/3.0, 4/3.0), Vector.of(1/3.0, 4/3.0), Vector.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(instance.elevate(), is(bezierDerivativeOf(expected)));
    }

    /**
     * Test of reduce method, of class BezierDerivative.
     */
    @Test
    public void testReduce() {
        System.out.println("reduce");
         BezierDerivative b1 = BezierDerivative.create(Array.of(Vector.of(-1.0, -1.0), Vector.of(1.0, 1.0)), Interval.of(0.2, 0.8));
         BezierDerivative e1 = BezierDerivative.create(Array.of(Vector.of(0.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(b1.reduce(), is(bezierDerivativeOf(e1)));

         BezierDerivative b2 = BezierDerivative.create(Array.of(Vector.of(-1.0, 0.0), Vector.of(0.0, 0.0), Vector.of(1.0, 0.0)), Interval.of(0.2, 0.8));
         BezierDerivative e2 = BezierDerivative.create(Array.of(Vector.of(-1.0, 0.0), Vector.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(b2.reduce(), is(bezierDerivativeOf(e2)));

         BezierDerivative b3 = BezierDerivative.create(Array.of(Vector.of(-1.0, 0.0), Vector.of(-1/3.0, 4/3.0), Vector.of(1/3.0, 4/3.0), Vector.of(1.0, 0.0)), Interval.of(0.2, 0.8));
         BezierDerivative e3 = BezierDerivative.create(Array.of(Vector.of(-1.0, 0.0), Vector.of(0.0, 2.0), Vector.of(1.0, 0.0)), Interval.of(0.2, 0.8));
        assertThat(b3.reduce(), is(bezierDerivativeOf(e3)));
    }

    /**
     * Test of subdivide method, of class BezierDerivative.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        Double t = 0.25;
        BezierDerivative instance = BezierDerivative.create(Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 1.0), Vector.of(1.0, 0.0), Vector.of(1.0, 1.0)), Interval.ZERO_ONE);
        BezierDerivative first = instance.subdivide(t)._1();
        BezierDerivative second = instance.subdivide(t)._2();
        assertThat(first, is(bezierDerivativeOf(BezierDerivative.create(Array.of(Vector.of(0.0, 0.0), Vector.of(0.0, 0.25), Vector.of(1/16.0, 3/8.0), Vector.of(5/32.0, 7/16.0)), Interval.ZERO_ONE))));
        assertThat(second, is(bezierDerivativeOf(BezierDerivative.create(Array.of(Vector.of(5/32.0, 7/16.0), Vector.of(7/16.0, 5/8.0), Vector.of(1.0, 0.250), Vector.of(1.0, 1.0)), Interval.ZERO_ONE))));
    }
}
