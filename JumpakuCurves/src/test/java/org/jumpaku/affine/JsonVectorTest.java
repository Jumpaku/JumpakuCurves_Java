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
 * @author Jumpaku
 */
public class JsonVectorTest {
    
    public JsonVectorTest() {
    }

    /**
     * Test of toJson method, of class JsonVector.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        JsonVector instance = new JsonVector();
        assertTrue(Vector.equals(Vector.of(1.23, 4.56, -7.89), instance.fromJson(instance.toJson(Vector.of(1.23, 4.56, -7.89))).get(), 1.0e-10));
    }

    /**
     * Test of fromJson method, of class JsonVector.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonVector instance = new JsonVector();
        assertTrue(Vector.equals(Vector.of(1.23, 4.56, -7.89), instance.fromJson("{x:1.23, y:4.56, z:-7.89}").get(), 1.0e-10));
        assertTrue(instance.fromJson("{x:1.23, y:4.56}").isEmpty());
    }
    
    /**
     * Test of getTemporaryType method, of class JsonPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonVector.Data.class, new JsonVector().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        assertTrue(Vector.equals(Vector.of(5.4, -4.2, 1.5e-35),
                new JsonVector().toTemporary(Vector.of(5.4, -4.2, 1.5e-35)).newInstance(), 1.0e-10));
    }
}
