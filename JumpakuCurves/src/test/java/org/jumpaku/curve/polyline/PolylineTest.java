/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import java.util.LinkedList;
import javaslang.collection.Array;
import static org.hamcrest.core.Is.is;
import static org.jumpaku.curve.polyline.PolylineMatcher.polylineOf;
import static org.jumpaku.affine.PointMatcher.pointOf;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Curve;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author jumpaku
 */
public class PolylineTest {
    
    /**
     * Test create create method, create class Polyline.
     */
    @Test
    public void testCreate_PointArr() {
        System.out.println("create");
        Array<? extends Point> points = Array.of(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        assertThat(points.get(0), is(pointOf(instance.getPoints().get(0))));
        assertThat(points.get(1), is(pointOf(instance.getPoints().get(1))));
        assertThat(points.get(2), is(pointOf(instance.getPoints().get(2))));
        assertEquals(3, instance.getPoints().size());
        assertEquals(0.0, instance.getDomain().getBegin(), 1.0e-10);
        assertEquals(Math.pow(56, 0.5)+Math.pow(29, 0.5), instance.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test create create method, create class Polyline.
     */
    @Test
    public void testCreate_Iterable() {
        System.out.println("create");
        Array<? extends Point> points = Array.of(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.create(points);
        assertThat(points.get(0), is(pointOf(instance.getPoints().get(0))));
        assertThat(points.get(1), is(pointOf(instance.getPoints().get(1))));
        assertThat(points.get(2), is(pointOf(instance.getPoints().get(2))));
        assertEquals(3, instance.getPoints().size());
        assertEquals(0.0, instance.getDomain().getBegin(), 1.0e-10);
        assertEquals(Math.pow(56, 0.5)+Math.pow(29, 0.5), instance.getDomain().getEnd(), 1.0e-10);
    }

    /**
     * Test create toLines method, create class Polyline.
     */
    @Test
    public void testToLines() {
        System.out.println("toLines");
        Curve curve = Polyline.create(Point.crisp(-1.0), Point.crisp(-1.0, 1.0), Point.crisp(1.0, 1.0), Point.crisp(1.0));
        Array<Point> expected = Array.of(Point.crisp(-1.0), Point.crisp(-1.0, 1.0), Point.crisp(0.0, 1.0), Point.crisp(1.0, 1.0), Point.crisp(1.0));
        LinkedList<Point> result = Polyline.toLines(curve, Point.crisp(-1.0), 0.0, Point.crisp(1.0), 4.0, 1.0e-10);
        assertThat(result.get(0), is(pointOf(expected.get(0))));
        assertThat(result.get(1), is(pointOf(expected.get(1))));
        assertThat(result.get(2), is(pointOf(expected.get(2))));
        assertThat(result.get(3), is(pointOf(expected.get(3))));
        assertThat(result.get(4), is(pointOf(expected.get(4))));
        assertEquals(result.size(), expected.size());
    }

    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        Polyline instance = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Array<? extends Point> expected = Array.of(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Array<? extends Point> result = instance.getPoints();
        assertThat(result.get(0), is(pointOf(expected.get(0))));
        assertThat(result.get(1), is(pointOf(expected.get(1))));
        assertThat(result.get(2), is(pointOf(expected.get(2))));
        assertEquals(3, instance.getPoints().size());
    }
    
    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        Polyline instance = Polyline.create(Point.fuzzy(1.0, 1.0, 2.0), Point.fuzzy(-1.0, -1.0, 1.0), Point.fuzzy(-1.0, -1.0, 1.0, 0.0));
        assertThat(instance.evaluate(0.0), is(pointOf(1.0, 1.0, 0.0, 2.0)));
        assertThat(instance.evaluate(Math.pow(2, 0.5)), is(pointOf(0.0, 0.0, 0.0, 1.5)));
        assertThat(instance.evaluate(2*Math.pow(2, 0.5)), is(pointOf(-1.0, -1.0, 0.0, 1.0)));
        assertThat(instance.evaluate(2*Math.pow(2, 0.5)+0.5), is(pointOf(-1.0, -1.0, 0.5, 0.5)));
        assertThat(instance.evaluate(2*Math.pow(2, 0.5)+1), is(pointOf(-1.0, -1.0, 1.0, 0.0)));
    }
    
    /**
     * Test create getPoints method, create class Polyline.
     */
    @Test
    public void testEvaluateAllByArcLengthParams() {
        System.out.println("evaluateAllArcLength");
        Polyline instance = Polyline.create(Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(1.0, 1.0, 1.0), Point.fuzzy(1.0, -3.0, 3.0), Point.fuzzy(1.0, -3.0, 1.5, 2.0));
        Array<? extends Point> result = instance.evaluateAllArcLength(6);
        assertThat(result.get(0), is(pointOf(-1.0, 1.0, 0.0, 2.0)));
        assertThat(result.get(1), is(pointOf(0.5, 1.0, 0.0, 1.25)));
        assertThat(result.get(2), is(pointOf(1.0, 0.0, 0.0, 1.5)));
        assertThat(result.get(3), is(pointOf(1.0, -1.5, 0.0, 2.25)));
        assertThat(result.get(4), is(pointOf(1.0, -3.0, 0.0, 3.0)));
        assertThat(result.get(5), is(pointOf(1.0, -3.0, 1.5, 2.0)));
    }

    /**
     * Test of toJson method, of class Polyline.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        Polyline pl = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.fromJson(Polyline.toJson(pl)).get();
        assertThat(instance, is(polylineOf(pl)));
    }

    /**
     * Test of toJson method, of class Polyline.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Polyline pl = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.fromJson(pl.toString()).get();
        assertThat(instance, is(polylineOf(pl)));
    }

    /**
     * Test of fromJson method, of class Polyline.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        Polyline pl = Polyline.create(Point.fuzzy(1.0, 2.0, 3.0, 2.0), Point.fuzzy(-1.0, -2.0, -3.0, 1.0), Point.fuzzy(1.0, 1.0, 1.0, 0.0));
        Polyline instance = Polyline.fromJson("{points:[{x:1.0,y:2.0,z:3.0,r:2.0},{x:-1.0,y:-2.0,z:-3.0,r:1.0},{x:1.0,y:1.0,z:1.0,r:0.0}]}").get();
        assertThat(instance, is(polylineOf(pl)));
    }
}
