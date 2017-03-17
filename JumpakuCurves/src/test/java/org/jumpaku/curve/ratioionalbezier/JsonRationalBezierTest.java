/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import java.lang.reflect.Type;
import org.jumpaku.affine.Point;
import org.jumpaku.json.Converter;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.jumpaku.curve.ratioionalbezier.RationalBezierMatcher.rationalBezierOf;
import static org.hamcrest.core.Is.is;
import org.jumpaku.curve.Interval;

/**
 *
 * @author tomohiko
 */
public class JsonRationalBezierTest {
    
    public JsonRationalBezierTest() {
    }

    
    @Test
    public void testToJson(){
        System.out.println("toJson");
        RationalBezier expected = RationalBezier.create(
                Interval.of(0.2, 0.9),
                new WeightedPoint(1.0, Point.fuzzy(0.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(0.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(1.0, 1.0)));
        RationalBezier actual = new JsonRationalBezier().fromJson(new JsonRationalBezier().toJson(expected)).get();
        assertThat(actual, is(rationalBezierOf(expected)));
    }
    
    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        RationalBezier actual = new JsonRationalBezier().fromJson(
                "{interval:{begin:0.2,end:0.9},"
                        + "controlPoints:[{x:0.0,y:0.0,z:0.0,r:1.0},{x:0.0,y:1.0,z:0.0,r:0.5},{x:1.0,y:0.0,z:0.0,r:2.0},{x:1.0,y:1.0,z:0.0,r:0.0}],"
                        + "weights:[1.0,2.0,3.0,4.0]}").get();
        RationalBezier expected = RationalBezier.create(
                Interval.of(0.2, 0.9),
                new WeightedPoint(1.0, Point.zero(1.0)),
                new WeightedPoint(2.0, Point.fuzzy(0.0, 1.0, 0.5)),
                new WeightedPoint(3.0, Point.fuzzy(1.0, 0.0, 2.0)),
                new WeightedPoint(4.0, Point.crisp(1.0, 1.0)));
        assertThat(actual, is(rationalBezierOf(expected)));
    }

    /**
     * Test of getTemporaryType method, of class JsonRationalBezier.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonRationalBezier.Data.class, new JsonRationalBezier().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonRationalBezier.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        RationalBezier instance = RationalBezier.create(
                Interval.of(0.2, 0.9),
                new WeightedPoint(1.0, Point.fuzzy(0.0, 0.0)),
                new WeightedPoint(2.0, Point.fuzzy(0.0, 1.0)),
                new WeightedPoint(3.0, Point.fuzzy(1.0, 0.0)),
                new WeightedPoint(4.0, Point.fuzzy(1.0, 1.0)));
        assertThat(new JsonRationalBezier().toTemporary(instance).newInstance(), is(rationalBezierOf(instance)));
    }
}
