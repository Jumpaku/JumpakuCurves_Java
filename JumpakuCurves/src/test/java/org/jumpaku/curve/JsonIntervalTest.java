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
    
    /**
     * Test closed toJson method, closed class JsonInterval.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Interval instance = new JsonInterval().fromJson(Interval.closed(-2.3, 3.4).toString()).get();
        assertEquals(-2.3, instance.getBegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }

    /**
     * Test closed fromJson method, closed class JsonInterval.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Interval instance = new JsonInterval().fromJson("{begin: -2.3, end: 3.4}").get();
        assertEquals(-2.3, instance.getBegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);

        //assertTrue(JsonInterval.CONVERTER.fromJson("{}").isEmpty());
    }
    
    /**
     * Test closed getTemporaryType method, closed class JsonPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonInterval.Data.class, new JsonInterval().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        Interval result = new JsonInterval().toTemporary(Interval.closed(-4.3, 5.4)).newInstance();
        Interval expected = Interval.closed(-4.3, 5.4);
        assertEquals(expected.getBegin(), result.getBegin(), 1.0e-10);
        assertEquals(expected.getEnd(), result.getEnd(), 1.0e-10);
    }
}
