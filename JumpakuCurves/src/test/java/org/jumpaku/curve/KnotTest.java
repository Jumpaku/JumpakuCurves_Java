package org.jumpaku.curve;

import javaslang.collection.Array;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by jumpaku on 2017/05/04.
 */
public class KnotTest {
    @Test
    public void test_Of() {
        System.out.println("Of");
        Knot a = Knot.of(0.0, 2);
        assertEquals(0.0, a.getValue(), 1.0e-10);
        assertEquals(2, a.getMultiplicity().intValue());
    }

    @Test
    public void test_ClampedUniformKnots() {
        System.out.println("ClampedUniformKnots");
        Array<Knot> knots = Knot.clampedUniformKnots(3, 5);
        assertEquals(0.0, knots.get(0).getValue(), 1.0e-10);
        assertEquals(4, knots.get(0).getMultiplicity().intValue());
        assertEquals(1.0, knots.get(1).getValue(), 1.0e-10);
        assertEquals(1, knots.get(1).getMultiplicity().intValue());
        assertEquals(2.0, knots.get(2).getValue(), 1.0e-10);
        assertEquals(4, knots.get(2).getMultiplicity().intValue());

    }

    @Test
    public void test_GetValue() {
        System.out.println("GetValue");
        Knot a = Knot.of(0.0, 2);
        assertEquals(0.0, a.getValue(), 1.0e-10);
    }

    @Test
    public void test_ToArray() {
        System.out.println("ToArray");
        Array<Double> knots = Knot.of(3.0, 5).toArray();
        assertEquals(3.0, knots.get(0), 1.0e-10);
        assertEquals(3.0, knots.get(1), 1.0e-10);
        assertEquals(3.0, knots.get(2), 1.0e-10);
        assertEquals(3.0, knots.get(3), 1.0e-10);
        assertEquals(3.0, knots.get(4), 1.0e-10);
        assertEquals(5, knots.size());

    }

    @Test
    public void test_GetMultiplicity() {
        System.out.println("GetMultiplicity");
        Knot a = Knot.of(0.0, 2);
        assertEquals(2, a.getMultiplicity().intValue());
    }

    @Test
    public void test_ReduceMultiplicity() {
        System.out.println("ReduceMultiplicity");
        Knot a = Knot.of(0.0, 2).reduceMultiplicity();
        assertEquals(0.0, a.getValue(), 1.0e-10);
        assertEquals(1, a.getMultiplicity().intValue());
    }

    @Test
    public void test_ReduceMultiplicity1() {
        System.out.println("ReduceMultiplicity1");
        Knot a = Knot.of(0.0, 2).reduceMultiplicity(2);
        assertEquals(0.0, a.getValue(), 1.0e-10);
        assertEquals(0, a.getMultiplicity().intValue());
    }

    @Test
    public void test_ElevateMultiplicity() {
        System.out.println("ElevateMultiplicity");
        Knot a = Knot.of(0.0, 2).elevateMultiplicity();
        assertEquals(0.0, a.getValue(), 1.0e-10);
        assertEquals(3, a.getMultiplicity().intValue());
    }
    @Test
    public void test_ElevateMultiplicity1() {
        System.out.println("ElevateMultiplicity1");
        Knot a = Knot.of(0.0, 2).elevateMultiplicity(2);
        assertEquals(0.0, a.getValue(), 1.0e-10);
        assertEquals(4, a.getMultiplicity().intValue());
    }
    @Test
    public void test_CompareTo() {
        System.out.println("CompareTo");
        assertEquals(0, Knot.of(1.0, 5).compareTo(Knot.of(1.0, 3)));
        assertEquals(-1, Knot.of(1.0, 5).compareTo(Knot.of(2.0, 3)));
        assertEquals(1, Knot.of(1.0, 5).compareTo(Knot.of(0.0, 3)));
    }


    @Test
    public void testToJson(){
        System.out.println("toJson");
        Knot instance = new JsonKnot().fromJson(Knot.toJson(Knot.of(-2.3, 3))).get();
        assertEquals(-2.3, instance.getValue(), 1.0e-10);
        assertEquals(3, instance.getMultiplicity().intValue());
    }

    @Test
    public void testFromJson(){
        System.out.println("fromJson");
        Knot instance = Knot.fromJson("{value: -2.3, multiplicity: 3}").get();
        assertEquals(-2.3, instance.getValue(), 1.0e-10);
        assertEquals(3, instance.getMultiplicity().intValue());
    }

    @Test
    public void testToString(){
        System.out.println("toString");
        Knot instance = new JsonKnot().fromJson(Knot.of(-2.3, 3).toString()).get();
        assertEquals(-2.3, instance.getValue(), 1.0e-10);
        assertEquals(3, instance.getMultiplicity().intValue());
    }
}