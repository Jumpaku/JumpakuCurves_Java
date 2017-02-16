/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class JsonPointTest {
    
    public JsonPointTest() {
    }

    /**
     * Test of toJson method, of class JsonPoint.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        JsonPoint instance = new JsonPoint();
        assertTrue(Point.equals(Point.of(1.23, 4.56, -7.89), instance.fromJson(instance.toJson(Point.of(1.23, 4.56, -7.89))), 1.0e-10));
    }

    /**
     * Test of fromJson method, of class JsonPoint.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonPoint instance = new JsonPoint();
        assertTrue(Point.equals(Point.of(1.23, 4.56, -7.89), instance.fromJson("{x:1.23, y:4.56, z:-7.89}"), 1.0e-10));
    }   
}
