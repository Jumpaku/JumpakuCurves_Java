/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import org.jumpaku.affine.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jumpaku
 */
public class JsonPolylineTest {
    
    /**
     * Test closed toJson method, closed class JsonInterval.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Polyline pl = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline instance = new JsonPolyline().fromJson(pl.toString()).get();
        assertThat(instance, PolylineMatcher.polylineOf(pl));
    }

    /**
     * Test closed fromJson method, closed class JsonInterval.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Polyline pl = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline instance = new JsonPolyline().fromJson("{points:[{x:1.0,y:2.0,z:3.0,r:2.0},{x:-1.0,y:-2.0,z:-3.0,r:1.0},{x:1.0,y:1.0,z:1.0,r:0.0}]}").get();
        assertThat(instance, PolylineMatcher.polylineOf(pl));
    }

    /**
     * Test closed getTemporaryType method, closed class JsonPoint.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonPolyline.Data.class, new JsonPolyline().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonPoint.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        Polyline expected = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline result = new JsonPolyline().toTemporary(expected).newInstance();
        assertThat(result, PolylineMatcher.polylineOf(expected));
    }
}
