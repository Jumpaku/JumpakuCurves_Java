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
        Interval instance = new JsonInterval().fromJson(Interval.of(-2.3, 3.4).toString()).get();
        assertEquals(-2.3, instance.getBegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }

    /**
     * Test of fromJson method, of class JsonInterval.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Interval instance = new JsonInterval().fromJson("{begin: -2.3, end: 3.4}").get();
        assertEquals(-2.3, instance.getBegin(), 1.0e-10);
        assertEquals(3.4, instance.getEnd(), 1.0e-10);
    }
    
    /**
     * Test of getTemporaryType method, of class JsonPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonInterval.Data.class, new JsonInterval().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        Interval result = new JsonInterval().toTemporary(Interval.of(-4.3, 5.4)).newInstance();
        Interval expected = Interval.of(-4.3, 5.4);
        assertEquals(expected.getBegin(), result.getBegin(), 1.0e-10);
        assertEquals(expected.getEnd(), result.getEnd(), 1.0e-10);
    }
}
