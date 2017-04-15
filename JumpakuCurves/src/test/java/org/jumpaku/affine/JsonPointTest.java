/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.PointMatcher.pointOf;
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
        assertThat(instance.fromJson(instance.toJson(Point.fuzzy(1.23, 4.56, -7.89, 1.0))).get(),
                is(pointOf(1.23, 4.56, -7.89, 1.0)));
    }

    /**
     * Test of fromJson method, of class JsonPoint.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonPoint instance = new JsonPoint();
        assertThat(instance.fromJson("{x:1.23, y:4.56, z:-7.89, r:1.0}").get(),
                is(pointOf(1.23, 4.56, -7.89, 1.0)));
        assertTrue(instance.fromJson("{}").isEmpty());
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
        assertThat(new JsonPoint().toTemporary(Point.fuzzy(5.4, -4.2, 1.5e-35, 1.0)).newInstance(),
                is(pointOf(5.4, -4.2, 1.5e-35, 1.0)));
    }
}
