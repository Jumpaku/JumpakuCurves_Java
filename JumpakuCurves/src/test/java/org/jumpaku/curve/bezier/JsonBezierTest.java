/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import org.jumpaku.affine.Point;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Jumpaku
 */
public class JsonBezierTest {
    
    @Test
    public void testToJson(){
        System.out.println("toJson");
        Bezier expected = Bezier.create(Point.fuzzy(0.0, 0.0), Point.fuzzy(0.0, 1.0), Point.fuzzy(1.0, 0.0), Point.fuzzy(1.0, 1.0));
        Bezier actual = new JsonBezier().fromJson(new JsonBezier().toJson(expected)).get();
        assertThat(actual, is(bezierOf(expected)));
    }
    
    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        Bezier actual = new JsonBezier().fromJson(
                "{interval:{begin:0.0,end:1.0}, controlPoints:[{x:0.0,y:0.0,z:0.0,r:1.0},{x:0.0,y:1.0,z:0.0,r:0.5},{x:1.0,y:0.0,z:0.0,r:2.0},{x:1.0,y:1.0,z:0.0,r:0.0}]}").get();
        Bezier expected = Bezier.create(Point.fuzzy(0.0, 0.0, 1.0), Point.fuzzy(0.0, 1.0, 0.5), Point.fuzzy(1.0, 0.0, 2.0), Point.crisp(1.0, 1.0));
        assertThat(actual, is(bezierOf(expected)));
    }

    /**
     * Test closed getTemporaryType method, closed class JsonBezier.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonBezier.Data.class, new JsonBezier().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonBezier.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        Bezier instance = Bezier.create(Point.fuzzy(0.0, 0.0, 5.0), Point.fuzzy(0.0, 1.0, 3.0), Point.fuzzy(1.0, 0.0, 0.0), Point.crisp(1.0));
        assertThat(new JsonBezier().toTemporary(instance).newInstance(), is(bezierOf(instance)));
    }
    
}
