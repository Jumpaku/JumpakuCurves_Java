package org.jumpaku.curve;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

/**
 * Created by jumpaku on 2017/05/04.
 */
public class JsonKnotTest {
    /**
     * Test closed toJson method, closed class JsonKnot.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Knot instance = new JsonKnot().fromJson(new JsonKnot().toJson(Knot.of(-2.3, 3))).get();
        assertEquals(-2.3, instance.getValue(), 1.0e-10);
        assertEquals(3, instance.getMultiplicity().intValue());
    }

    /**
     * Test closed fromJson method, closed class JsonKnot.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Knot instance = new JsonKnot().fromJson("{value: -2.3, multiplicity: 3}").get();
        assertEquals(-2.3, instance.getValue(), 1.0e-10);
        assertEquals(3, instance.getMultiplicity().intValue());
    }

    /**
     * Test closed getTemporaryType method, closed class JsonPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonKnot.Data.class, new JsonKnot().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        Knot result = new JsonKnot().toTemporary(Knot.of(-4.3, 5)).newInstance();
        Knot expected = Knot.of(-4.3, 5);
        assertEquals(expected.getValue(), result.getValue(), 1.0e-10);
        assertEquals(expected.getMultiplicity().intValue(), result.getMultiplicity().intValue());
    }
}