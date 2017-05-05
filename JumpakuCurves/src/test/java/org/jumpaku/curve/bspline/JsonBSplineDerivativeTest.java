package org.jumpaku.curve.bspline;

import javaslang.collection.Stream;
import org.jumpaku.affine.Point;
import org.jumpaku.curve.Knot;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.curve.bspline.BSplineMatcher.bSplineOf;
import static org.junit.Assert.*;

/**
 * Created by jumpaku on 2017/05/03.
 */
public class JsonBSplineDerivativeTest {

    @Test
    public void testToJson(){
        System.out.println("toJson");
        BSplineDerivative expected = BSplineDerivative.create(BSpline.create(
                3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Knot.clampedUniformKnots(3, 5)));
        BSplineDerivative actual = new JsonBSplineDerivative().fromJson(new JsonBSplineDerivative().toJson(expected)).get();
        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        BSplineDerivative actual = new JsonBSplineDerivative().fromJson(
                "{degree:3, controlVectors:[{x:-2.0,y:0.0,z:0.0,r:1.0},{x:-1.0,y:1.0,z:0.0,r:2.0},{x:0.0,y:0.0,z:0.0,r:3.0},{x:1.0,y:-1.0,z:0.0,r:4.0},{x:2.0,y:0.0,z:0.0,r:5.0}], knots:[{value:0.0, multiplicity:4}, {value:1.0, multiplicity:1}, {value:2.0, multiplicity:4}]}").get();
        BSplineDerivative expected = BSplineDerivative.create(BSpline.create(
                3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Knot.clampedUniformKnots(3, 5)));
        assertThat(actual.toBSpline(), is(bSplineOf(expected.toBSpline())));
    }

    /**
     * Test closed getTemporaryType method, closed class JsonBSpline.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonBSplineDerivative.Data.class, new JsonBSplineDerivative().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonBSpline.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        BSplineDerivative instance = BSplineDerivative.create(BSpline.create(
                3,
                Stream.of(Point.fuzzy(-2.0, 0.0, 1.0), Point.fuzzy(-1.0, 1.0, 2.0), Point.fuzzy(0.0, 0.0, 3.0), Point.fuzzy(1.0, -1.0, 4.0), Point.fuzzy(2.0, 0.0, 5.0)),
                Knot.clampedUniformKnots(3, 5)));
        assertThat(new JsonBSplineDerivative().toTemporary(instance).newInstance().toBSpline(), is(bSplineOf(instance.toBSpline())));
    }
}