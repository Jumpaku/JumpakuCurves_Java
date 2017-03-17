/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import java.lang.reflect.Type;
import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.ratioionalbezier.ConicSectionMatcher.conicSectionOf;
import org.jumpaku.json.Converter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class JsonConicSectionTest {
    
    public JsonConicSectionTest() {
    }

    @Test
    public void testToJson(){
        System.out.println("toJson");
        ConicSection expected = RationalBezier.byRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        ConicSection actual = new JsonConicSection().fromJson(new JsonConicSection().toJson(expected)).get();
        assertThat(actual, is(conicSectionOf(expected)));
    }
    
    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        ConicSection actual = new JsonConicSection().fromJson(
                "{interval:{begin:0.2,end:0.9},"
                        + "representPoints:[{x:1.0,y:0.0,z:0.0,r:1.0},{x:0.2928932188134525,y:0.2928932188134525,z:0.0,r:2.0},{x:0.0,y:1.0,z:0.0,r:1.0}],"
                        + "weight:0.7071067811865476}").get();
        ConicSection expected = RationalBezier.byRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(actual, is(conicSectionOf(expected)));
    }

    /**
     * Test of getTemporaryType method, of class JsonConicSection.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonConicSection.Data.class, new JsonConicSection().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonConicSection.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        ConicSection instance = RationalBezier.byRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(new JsonConicSection().toTemporary(instance).newInstance(), is(conicSectionOf(instance)));
    }
}
