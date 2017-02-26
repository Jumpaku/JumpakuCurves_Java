/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import java.lang.reflect.Type;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
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
        assertTrue(Point.equals(Point.of(1.23, 4.56, -7.89),
                instance.fromJson(instance.toJson(Point.of(1.23, 4.56, -7.89))).get(), 1.0e-10));
    }

    /**
     * Test of fromJson method, of class JsonPoint.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonPoint instance = new JsonPoint();
        assertTrue(Point.equals(Point.of(1.23, 4.56, -7.89), instance.fromJson("{x:1.23, y:4.56, z:-7.89}").get(), 1.0e-10));
        assertTrue(instance.fromJson("{x:1.23, y:4.56}").isEmpty());
    }   

    /**
     * Test of getTemporaryType method, of class JsonPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonPoint.Data.class, new JsonPoint().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        assertTrue(Point.equals(Point.of(5.4, -4.2, 1.5e-35),
                new JsonPoint().toTemporary(Point.of(5.4, -4.2, 1.5e-35)).newInstance(), 1.0e-10));
    }
}
