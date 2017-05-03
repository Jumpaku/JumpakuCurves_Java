package org.jumpaku.curve.bspline;

import javaslang.collection.Stream;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Interval;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.curve.bspline.BSplineDerivativeMatcher.bSplineDerivativeOf;
import static org.junit.Assert.*;

/**
 * Created by jumpaku on 2017/05/03.
 */
public class JsonBSplineDerivativeTest {

    @Test
    public void testToJson(){
        System.out.println("toJson");
        BSplineDerivative expected = BSplineDerivative.create(BSpline.create(
                Interval.of(2.5, 3.0), 3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Stream.of(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)));
        BSplineDerivative actual = new JsonBSplineDerivative().fromJson(new JsonBSplineDerivative().toJson(expected)).get();
        assertThat(actual, is(bSplineDerivativeOf(expected)));
    }

    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        BSplineDerivative actual = new JsonBSplineDerivative().fromJson(
                "{interval:{begin:2.5,end:3.0}, degree:3, controlVectors:[{x:-2.0,y:0.0,z:0.0,r:1.0},{x:-1.0,y:1.0,z:0.0,r:2.0},{x:0.0,y:0.0,z:0.0,r:3.0},{x:1.0,y:-1.0,z:0.0,r:4.0},{x:2.0,y:0.0,z:0.0,r:5.0}], knots:[-1.0,0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0]}").get();
        BSplineDerivative expected = BSplineDerivative.create(BSpline.create(
                Interval.of(2.5, 3.0), 3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Stream.of(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)));
        assertThat(actual, is(bSplineDerivativeOf(expected)));
    }

    /**
     * Test of getTemporaryType method, of class JsonBSpline.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonBSplineDerivative.Data.class, new JsonBSplineDerivative().getTemporaryType());
    }

    /**
     * Test of toTemporary method, of class JsonBSpline.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        BSplineDerivative instance = BSplineDerivative.create(BSpline.create(
                Interval.of(2.5, 3.0), 3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Stream.of(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)));
        assertThat(new JsonBSplineDerivative().toTemporary(instance).newInstance(), is(bSplineDerivativeOf(instance)));
    }
}