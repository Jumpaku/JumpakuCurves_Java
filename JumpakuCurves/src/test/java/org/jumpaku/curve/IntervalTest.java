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
    
    public IntervalTest() {
    }

    /**
     * Test of closed method, of class Interval.
     */
    @Test
    public void testOf() {
        System.out.println("of");
        Interval instance = Interval.of(-2.3, 3.4);
        assertEquals(-2.3, instance.getbegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }

    /**
     * Test of sample method, of class Interval.
     */
    @Test
    public void testSample_Integer() {
        System.out.println("sample");
        assertArrayEquals(new double[]{-0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5},
                Interval.of(-0.1, 0.5).sample(7).toJavaStream().mapToDouble(d->d).toArray(), 1.0e-10);
    }

    /**
     * Test of sample method, of class Interval.
     */
    @Test
    public void testSample_Double() {
        System.out.println("sample");
        assertArrayEquals(new double[]{-0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5},
                Interval.of(-0.1, 0.5).sample(0.09).toJavaStream().mapToDouble(d->d).toArray(), 1.0e-10);
    }

    /**
     * Test of getbegin method, of class Interval.
     */
    @Test
    public void testGetbegin() {
        System.out.println("getbegin");
        Interval instance = Interval.of(-2.3, 3.4);
        assertEquals(-2.3, instance.getbegin(), 1.0e-10);
    }

    /**
     * Test of getEnd method, of class Interval.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        Interval instance = Interval.of(-2.3, 3.4);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }

    /**
     * Test of getEnd method, of class Interval.
     */
    @Test
    public void testIncludes() {
        System.out.println("getEnd");
        Interval instance = Interval.of(-2.3, 3.4);
        assertEquals(true, instance.includes(-2.3));
        assertEquals(true, instance.includes( 3.4));
        assertEquals(true, instance.includes( 1.0));
        assertEquals(false,instance.includes(-3.0));
        assertEquals(false,instance.includes( 4.0));
    }
    
    /**
     * Test of getEnd method, of class Interval.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Interval instance = new JsonInterval().fromJson(Interval.of(-2.3, 3.4).toString()).get();
        assertEquals(-2.3, instance.getbegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }  
}
