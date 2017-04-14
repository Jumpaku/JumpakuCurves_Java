/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.ratioionalbezier.ByRepresentPointsMatcher.conicSectionOf;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class JsonByRepresentPointsTest {
    
    public JsonByRepresentPointsTest() {
    }

    @Test
    public void testToJson(){
        System.out.println("toJson");
        ByRepresentPoints expected = new ByRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        ByRepresentPoints actual = new JsonByRepresentPoints().fromJson(new JsonByRepresentPoints().toJson(expected)).get();
        assertThat(actual, is(conicSectionOf(expected)));
    }
    
    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        ByRepresentPoints actual = new JsonByRepresentPoints().fromJson(
                "{interval:{begin:0.2,end:0.9},"
                        + "representPoints:[{x:1.0,y:0.0,z:0.0,r:1.0},{x:0.2928932188134525,y:0.2928932188134525,z:0.0,r:2.0},{x:0.0,y:1.0,z:0.0,r:1.0}],"
                        + "weight:0.7071067811865476}").get();
        ByRepresentPoints expected = new ByRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(actual, is(conicSectionOf(expected)));
    }

    /**
     * Test of getTemporaryType method, of class JsonByRepresentPoints.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonByRepresentPoints.Data.class, new JsonByRepresentPoints().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonByRepresentPoints.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        ByRepresentPoints instance = new ByRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(new JsonByRepresentPoints().toTemporary(instance).newInstance(), is(conicSectionOf(instance)));
    }
}
