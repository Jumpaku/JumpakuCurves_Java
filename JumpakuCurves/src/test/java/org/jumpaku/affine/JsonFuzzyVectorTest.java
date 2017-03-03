/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.FuzzyVectorMatcher.fuzzyVectorOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class JsonFuzzyVectorTest {
    
    public JsonFuzzyVectorTest() {
    }

    /**
     * Test of toJson method, of class JsonVector.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        JsonFuzzyVector instance = new JsonFuzzyVector();
        FuzzyVector v = FuzzyVector.of(1.23, 4.56, -7.89, 0.3);
        assertThat(instance.fromJson(instance.toJson(v)).get(), is(fuzzyVectorOf(v)));
    }

    /**
     * Test of fromJson method, of class JsonVector.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonFuzzyVector instance = new JsonFuzzyVector();
        assertThat(instance.fromJson("{x:1.23, y:4.56, z:-7.89, r:0.3}").get(), is(fuzzyVectorOf(FuzzyVector.of(1.23, 4.56, -7.89, 0.3))));
        assertTrue(instance.fromJson("{x:1.23, y:4.56}").isEmpty());
    }

    /**
     * Test of getTemporaryType method, of class JsonFuzzyVector.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonFuzzyVector.Data.class, new JsonFuzzyVector().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonFuzzyVector.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        assertThat(new JsonFuzzyVector().toTemporary(FuzzyVector.of(5.4, -4.2, 1.5e-35, 0.3)).newInstance(),
                is(fuzzyVectorOf(FuzzyVector.of(5.4, -4.2, 1.5e-35, 0.3))));

    }
}
