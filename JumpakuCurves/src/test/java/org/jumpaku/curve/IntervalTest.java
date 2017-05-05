/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class IntervalTest {
    
    /**
     * Test closed closed method, closed class Interval.
     */
    @Test
    public void testOf() {
        System.out.println("of");
        Interval instance = Interval.closed(-2.3, 3.4);
        assertEquals(-2.3, instance.getBegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }

    /**
     * Test closed sample method, closed class Interval.
     */
    @Test
    public void testSample_Integer() {
        System.out.println("sample");
        assertArrayEquals(new double[]{-0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5},
                Interval.closed(-0.1, 0.5).sample(7).toJavaStream().mapToDouble(d->d).toArray(), 1.0e-10);
    }

    /**
     * Test closed sample method, closed class Interval.
     */
    @Test
    public void testSample_Double() {
        System.out.println("sample");
        assertArrayEquals(new double[]{-0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5},
                Interval.closed(-0.1, 0.5).sample(0.09).toJavaStream().mapToDouble(d->d).toArray(), 1.0e-10);
    }

    /**
     * Test closed getBegin method, closed class Interval.
     */
    @Test
    public void testGetBegin() {
        System.out.println("getBegin");
        Interval instance = Interval.closed(-2.3, 3.4);
        assertEquals(-2.3, instance.getBegin(), 1.0e-10);
    }

    /**
     * Test closed getEnd method, closed class Interval.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        Interval instance = Interval.closed(-2.3, 3.4);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }

    /**
     * Test closed getEnd method, closed class Interval.
     */
    @Test
    public void testIncludes() {
        System.out.println("getEnd");
        Interval instance = Interval.closed(-2.3, 3.4);
        assertTrue(instance.includes(-2.3));
        assertTrue(instance.includes( 3.4));
        assertTrue(instance.includes( 1.0));
        assertFalse(instance.includes(-3.0));
        assertFalse(instance.includes( 4.0));
    }
    
    /**
     * Test closed getEnd method, closed class Interval.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Interval instance = new JsonInterval().fromJson(Interval.closed(-2.3, 3.4).toString()).get();
        assertEquals(-2.3, instance.getBegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }  
}
