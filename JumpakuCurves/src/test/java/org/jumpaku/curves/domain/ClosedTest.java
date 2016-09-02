/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class ClosedTest {
    
    public ClosedTest() {
    }

    /**
     * Test of contains method, of class Closed.
     */
    @Test
    public void testIsIn() {
        System.out.println("isIn");
        Double t = 1.3;
        Closed instance = new Closed(0.3, 1.3);
        Boolean expResult = true;
        Boolean result = instance.contains(t);
        assertEquals(expResult, result);
    }
    
}
