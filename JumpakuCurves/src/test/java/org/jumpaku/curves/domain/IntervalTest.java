/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.jumpaku.curves.test.TestUtils.*;
/**
 *
 * @author ito tomohiko
 */
public class IntervalTest {
    
    public IntervalTest() {
    }

    /**
     * Test of getFrom method, of class Interval.
     */
    @Test
    public void testGetFrom() {
        System.out.println("getFrom");
        Interval instance = new Closed(0.0, 1.0);
        Double expResult = 0.0;
        Double result = instance.getFrom();
        assertTrue(equalsDouble(expResult, result));
    }

    /**
     * Test of getTo method, of class Interval.
     */
    @Test
    public void testGetTo() {
        System.out.println("getTo");
        Interval instance = new Closed(0.0, 1.0);
        Double expResult = 1.0;
        Double result = instance.getTo();
        assertTrue(equalsDouble(expResult, result));
    }
}
