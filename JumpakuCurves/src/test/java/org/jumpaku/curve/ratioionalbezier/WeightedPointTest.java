/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import org.jumpaku.affine.Point;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.PointMatcher.pointOf;
import static org.jumpaku.curve.ratioionalbezier.WeightedPointMatcher.weightedPointOf;

/**
 *
 * @author tomohiko
 */
public class WeightedPointTest {
    
    public WeightedPointTest() {
    }

    /**
     * Test of getW method, of class WeightedPoint.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        assertEquals(-0.4, new WeightedPoint(-0.4, Point.fuzzy(1.0, 2.0, 3.0, 4.0)).getWeight(), 1.0e-10);
    }

    /**
     * Test of getPoint method, of class WeightedPoint.
     */
    @Test
    public void testGetPoint() {
        System.out.println("getPoint");
        assertThat(new WeightedPoint(-0.4, Point.fuzzy(1.0, 2.0, 3.0, 4.0)).getPoint(), is(pointOf(1.0, 2.0, 3.0, 4.0)));
    }

    /**
     * Test of divide method, of class WeightedPoint.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        WeightedPoint p1 = new WeightedPoint(3.0, Point.fuzzy(2.0, 10.0));
        WeightedPoint p2 = new WeightedPoint(2.0, Point.fuzzy(-2.0, 20.0));
        assertThat(p1.divide(-1.0, p2), is(weightedPointOf( 4.0, Point.fuzzy( 16.0/4.0, 100.0/4.0))));
        assertThat(p1.divide( 0.0, p2), is(weightedPointOf( 3.0, Point.fuzzy(  6.0/3.0, 30.0/3.0))));
        assertThat(p1.divide( 0.4, p2), is(weightedPointOf( 2.6, Point.fuzzy(  2.0/2.6, 34.0/2.6))));
        assertThat(p1.divide( 1.0, p2), is(weightedPointOf( 2.0, Point.fuzzy( -4.0/2.0, 40.0/2.0))));
        assertThat(p1.divide( 2.0, p2), is(weightedPointOf( 1.0, Point.fuzzy(-14.0/1.0, 110.0/1.0))));
    }
    
}
