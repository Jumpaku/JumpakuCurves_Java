
package org.jumpaku.affine;

import org.jumpaku.affine.JsonWeightedPoint;
import org.jumpaku.affine.Point;
import org.jumpaku.affine.WeightedPoint;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.PointMatcher.pointOf;
import static org.jumpaku.affine.WeightedPointMatcher.weightedPointOf;

/**
 *
 * @author Jumpaku
 */
public class WeightedPointTest {
    
    public WeightedPointTest() {
    }

    /**
     * Test of getW method, of class WeightedPoint.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        assertEquals(-0.4, new WeightedPoint(Point.fuzzy(1.0, 2.0, 3.0, 4.0), -0.4).getWeight(), 1.0e-10);
    }

    /**
     * Test of getPoint method, of class WeightedPoint.
     */
    @Test
    public void testGetPoint() {
        System.out.println("getPoint");
        assertThat(new WeightedPoint(Point.fuzzy(1.0, 2.0, 3.0, 4.0), -0.4).getPoint(), is(pointOf(1.0, 2.0, 3.0, 4.0)));
    }

    /**
     * Test of divide method, of class WeightedPoint.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        WeightedPoint p1 = new WeightedPoint(Point.fuzzy(2.0, 10.0), 3.0);
        WeightedPoint p2 = new WeightedPoint(Point.fuzzy(-2.0, 20.0), 2.0);
        assertThat(p1.divide(-1.0, p2), is(weightedPointOf( Point.fuzzy( 16.0/4.0, 100.0/4.0), 4.0)));
        assertThat(p1.divide( 0.0, p2), is(weightedPointOf( Point.fuzzy(  6.0/3.0, 30.0/3.0) , 3.0)));
        assertThat(p1.divide( 0.4, p2), is(weightedPointOf( Point.fuzzy(  2.0/2.6, 34.0/2.6) , 2.6)));
        assertThat(p1.divide( 1.0, p2), is(weightedPointOf( Point.fuzzy( -4.0/2.0, 40.0/2.0) , 2.0)));
        assertThat(p1.divide( 2.0, p2), is(weightedPointOf( Point.fuzzy(-14.0/1.0, 110.0/1.0), 1.0)));
    }

    /**
     * Test of toString method, of class WeightedPoint.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        WeightedPoint weightedPoint = new WeightedPoint(Point.fuzzy(1.0, 2.0, 3.0, 4.0), 2.0);
        assertThat(JsonWeightedPoint.CONVERTER.fromJson(weightedPoint.toString()).get(),
                is(weightedPointOf(weightedPoint)));
    }
}
