/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import org.jumpaku.affine.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class JsonBezierTest {
    
    @Test
    public void testToJson(){
        System.out.println("toJson");
        Bezier expected = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        Bezier actual = new JsonBezier().fromJson(new JsonBezier().toJson(expected)).get();
        assertThat(actual, is(bezierOf(expected)));
    }
    
    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        Bezier actual = new JsonBezier().fromJson("{\"controlPoints\":[{\"x\":0.0,\"y\":0.0,\"z\":0.0},{\"x\":0.0,\"y\":1.0,\"z\":0.0},{\"x\":1.0,\"y\":0.0,\"z\":0.0},{\"x\":1.0,\"y\":1.0,\"z\":0.0}],\"interval\":{\"begin\":0.0,\"end\":1.0}}").get();
        Bezier expected = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        assertThat(actual, is(bezierOf(expected)));
    }

    /**
     * Test of getTemporaryType method, of class JsonBezier.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonBezier.Data.class, new JsonBezier().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonBezier.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        Bezier expected = Bezier.create(Point.of(0.0, 0.0), Point.of(0.0, 1.0), Point.of(1.0, 0.0), Point.of(1.0, 1.0));
        assertThat(new JsonBezier().toTemporary(expected).newInstance(), is(bezierOf(expected)));
    }
    
}
