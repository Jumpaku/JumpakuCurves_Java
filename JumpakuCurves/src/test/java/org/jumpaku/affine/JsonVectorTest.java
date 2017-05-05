/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.VectorMatcher.vectorOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class JsonVectorTest {

    /**
     * Test closed toJson method, closed class JsonVector.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        JsonVector instance = new JsonVector();
        assertThat(instance.fromJson(instance.toJson(Vector.fuzzy(1.23, 4.56, -7.89, 10.0))).get(),
                is(vectorOf(1.23, 4.56, -7.89, 10.0)));
    }

    /**
     * Test closed fromJson method, closed class JsonVector.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        JsonVector instance = new JsonVector();
        assertThat(instance.fromJson("{x:1.23, y:4.56, z:-7.89,r:10.0}").get(), 
                is(vectorOf(1.23, 4.56, -7.89, 10.0)));
        assertTrue(instance.fromJson("{x:1.23, y:4.56}").isEmpty());
    }
    
    /**
     * Test closed getTemporaryType method, closed class JsonPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonVector.Data.class, new JsonVector().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        assertThat(new JsonVector().toTemporary(Vector.fuzzy(5.4, -4.2, 1.5e-35, 1.0)).newInstance(),
                is(vectorOf(5.4, -4.2, 1.5e-35, 1.0)));
    }
}
