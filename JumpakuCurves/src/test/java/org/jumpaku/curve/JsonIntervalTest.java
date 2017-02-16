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
public class JsonIntervalTest {
    
    public JsonIntervalTest() {
    }

    /**
     * Test of toJson method, of class JsonInterval.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Interval instance = new JsonInterval().fromJson(Interval.closed(-2.3, 3.4).toString());
        assertEquals(-2.3, instance.getbegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }

    /**
     * Test of fromJson method, of class JsonInterval.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Interval instance = new JsonInterval().fromJson("{start: -2.3, end: 3.4}");
        assertEquals(-2.3, instance.getbegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }
    
}
