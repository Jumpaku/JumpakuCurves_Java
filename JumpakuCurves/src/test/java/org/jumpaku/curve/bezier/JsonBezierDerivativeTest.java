package org.jumpaku.curve.bezier;

import org.jumpaku.affine.Point;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.curve.bezier.BezierMatcher.bezierOf;
import static org.junit.Assert.*;

/**
 * Created by Jumpaku on 2017/04/09.
 */
public class JsonBezierDerivativeTest {

    @Test
    public void testToJson(){
        System.out.println("toJson");
        BezierDerivative expected = BezierDerivative.create(Bezier.create(
                Point.crisp(0.0, 0.0), Point.crisp(0.0, 1.0), Point.crisp(1.0, 0.0), Point.crisp(1.0, 1.0)));
        BezierDerivative actual = new JsonBezierDerivative().fromJson(new JsonBezierDerivative().toJson(expected)).get();
        assertThat(actual.toBezier(), is(bezierOf(expected.toBezier())));
    }

    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        BezierDerivative actual = new JsonBezierDerivative().fromJson(
                "{interval:{begin:0.0,end:1.0}, controlVectors:[{x:0.0,y:0.0,z:1.0,r:0.0},{x:0.0,y:1.0,z:0.5,r:0.0},{x:1.0,y:0.0,z:2.0,r:0.0},{x:1.0,y:1.0,z:0.0,r:0.0}]}").get();
        BezierDerivative expected = BezierDerivative.create(Bezier.create(
                Point.crisp(0.0, 0.0, 1.0), Point.crisp(0.0, 1.0, 0.5), Point.crisp(1.0, 0.0, 2.0), Point.crisp(1.0, 1.0)));
        assertThat(actual.toBezier(), is(bezierOf(expected.toBezier())));
    }

    /**
     * Test closed getTemporaryType method, closed class JsonBezier.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        assertEquals(JsonBezierDerivative.Data.class, new JsonBezierDerivative().getTemporaryType());
    }

    /**
     * Test closed toTemporary method, closed class JsonBezier.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        BezierDerivative instance = BezierDerivative.create(Bezier.create(
                Point.crisp(0.0, 0.0, 5.0), Point.crisp(0.0, 1.0, 3.0), Point.crisp(1.0, 0.0, 0.0), Point.crisp(1.0)));
        assertThat(new JsonBezierDerivative().toTemporary(instance).newInstance().toBezier(), is(bezierOf(instance.toBezier())));
    }

}