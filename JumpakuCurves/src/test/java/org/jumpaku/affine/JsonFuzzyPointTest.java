/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.FuzzyPointMatcher.fuzzyPointOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class JsonFuzzyPointTest {
    
    public JsonFuzzyPointTest() {
    }

    /**
     * Test of toJson method, of class JsonPoint.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        JsonFuzzyPoint instance = new JsonFuzzyPoint();
        FuzzyPoint v = FuzzyPoint.of(1.23, 4.56, -7.89, 0.3);
        assertThat(instance.fromJson(instance.toJson(v)).get(), is(fuzzyPointOf(v)));
    }

    /**
     * Test of fromJson method, of class JsonPoint.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonFuzzyPoint instance = new JsonFuzzyPoint();
        assertThat(instance.fromJson("{x:1.23, y:4.56, z:-7.89, r:0.3}").get(), is(fuzzyPointOf(FuzzyPoint.of(1.23, 4.56, -7.89, 0.3))));
        assertTrue(instance.fromJson("{x:1.23, y:4.56}").isEmpty());
    }

    /**
     * Test of getTemporaryType method, of class JsonFuzzyPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonFuzzyPoint.Data.class, new JsonFuzzyPoint().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonFuzzyPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        assertThat(new JsonFuzzyPoint().toTemporary(FuzzyPoint.of(5.4, -4.2, 1.5e-35, 0.3)).newInstance(),
                is(fuzzyPointOf(FuzzyPoint.of(5.4, -4.2, 1.5e-35, 0.3))));

    }
}
