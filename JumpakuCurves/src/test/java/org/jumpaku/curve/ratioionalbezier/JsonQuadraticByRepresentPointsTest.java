/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import static org.hamcrest.core.Is.is;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import static org.jumpaku.curve.ratioionalbezier.QuadraticByRepresentPointsMatcher.quadraticByRepresentPointOf;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class JsonQuadraticByRepresentPointsTest {
    
    public JsonQuadraticByRepresentPointsTest() {
    }

    @Test
    public void testToJson(){
        System.out.println("toJson");
        QuadraticByRepresentPoints expected = new QuadraticByRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        QuadraticByRepresentPoints actual = new JsonQuadraticByRepresentPoints().fromJson(new JsonQuadraticByRepresentPoints().toJson(expected)).get();
        assertThat(actual, is(quadraticByRepresentPointOf(expected)));
    }
    
    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        QuadraticByRepresentPoints actual = new JsonQuadraticByRepresentPoints().fromJson(
                "{interval:{begin:0.2,end:0.9},"
                        + "representPoints:[{x:1.0,y:0.0,z:0.0,r:1.0},{x:0.2928932188134525,y:0.2928932188134525,z:0.0,r:2.0},{x:0.0,y:1.0,z:0.0,r:1.0}],"
                        + "weight:0.7071067811865476}").get();
        QuadraticByRepresentPoints expected = new QuadraticByRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(actual, is(quadraticByRepresentPointOf(expected)));
    }

    /**
     * Test of getTemporaryType method, of class JsonQuadraticByRepresentPoints.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonQuadraticByRepresentPoints.Data.class, new JsonQuadraticByRepresentPoints().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonQuadraticByRepresentPoints.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        QuadraticByRepresentPoints instance = new QuadraticByRepresentPoints(
                Interval.of(0.2, 0.9),
                Math.sqrt(2.0)/2, 
                Point.fuzzy(1.0, 0.0, 1.0),
                Point.fuzzy(0.5/(1+Math.sqrt(2.0)/2), 0.5/(1+Math.sqrt(2.0)/2), 2.0),
                Point.fuzzy(0.0, 1.0, 1.0));
        assertThat(new JsonQuadraticByRepresentPoints().toTemporary(instance).newInstance(), is(quadraticByRepresentPointOf(instance)));
    }
}
