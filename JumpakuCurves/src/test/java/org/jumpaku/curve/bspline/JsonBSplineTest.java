package org.jumpaku.curve.bspline;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.curve.bspline.BSplineMatcher.bSplineOf;

import javaslang.collection.Stream;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by jumpaku on 2017/05/03.
 */
public class JsonBSplineTest {
    
    @Test
    public void testToJson(){
        System.out.println("toJson");
        BSpline expected = BSpline.create(
                Interval.of(2.5, 3.0), 3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Stream.of(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0));
        BSpline actual = new JsonBSpline().fromJson(new JsonBSpline().toJson(expected)).get();
        assertThat(actual, is(bSplineOf(expected)));
    }

    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        BSpline actual = new JsonBSpline().fromJson(
                "{interval:{begin:2.5,end:3.0}, degree:3, controlPoints:[{x:-2.0,y:0.0,z:0.0,r:1.0},{x:-1.0,y:1.0,z:0.0,r:2.0},{x:0.0,y:0.0,z:0.0,r:3.0},{x:1.0,y:-1.0,z:0.0,r:4.0},{x:2.0,y:0.0,z:0.0,r:5.0}], knots:[-1.0,0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0]}").get();
        BSpline expected = BSpline.create(
                Interval.of(2.5, 3.0), 3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Stream.of(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0));
        assertThat(actual, is(bSplineOf(expected)));
    }

    /**
     * Test of getTemporaryType method, of class JsonBSpline.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonBSpline.Data.class, new JsonBSpline().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonBSpline.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        BSpline instance = BSpline.create(
                Interval.of(2.5, 3.0), 3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Stream.of(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0));
        assertThat(new JsonBSpline().toTemporary(instance).newInstance(), is(bSplineOf(instance)));
    }

}