package org.jumpaku.affine;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.WeightedPointMatcher.weightedPointOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Jmpaku on 2017/04/09.
 */
public class JsonWeightedPointTest {

    @Test
    public void testToJson(){
        System.out.println("toJson");
        WeightedPoint weightedPoint = new WeightedPoint(Point.fuzzy(1.0, 2.0, 3.0, 4.0), 1.0);
        WeightedPoint actual = new JsonWeightedPoint().fromJson(new JsonWeightedPoint().toJson(weightedPoint)).get();
        assertThat(actual, is(weightedPointOf(weightedPoint)));
    }

    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        WeightedPoint actual = new JsonWeightedPoint().fromJson(
                "{weight:1.0, point: {x:1.0,y:2.0,z:3.0,r:4.0}}").get();
        WeightedPoint expected = new WeightedPoint(Point.fuzzy(1.0, 2.0, 3.0, 4.0), 1.0);
        assertThat(actual, is(weightedPointOf(expected)));
    }

    /**
     * Test closed getTemporaryType method, closed class JsonWeightedPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonWeightedPoint.Data.class, new JsonWeightedPoint().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonWeightedPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        WeightedPoint weightedPoint = new WeightedPoint(Point.fuzzy(1.0, 2.0, 3.0, 4.0), 1.0);
        assertThat(new JsonWeightedPoint().toTemporary(weightedPoint).newInstance(), is(weightedPointOf(weightedPoint)));
    }
}
