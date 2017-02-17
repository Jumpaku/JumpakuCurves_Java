/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.fitting;

import org.jumpaku.old.curves.TimeSeriesData;
import org.apache.commons.math3.util.Precision;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Point3D;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class TimeSeriesDataTest {
    
    /**
     * Test of getData method, of class TimeSeriesData.
     */
    @Test
    public void testGetData() {
        System.out.println("getPoint");
        TimeSeriesData<Point3D> instance = new TimeSeriesData<>(new Point3D(1.0, -4.32, 0.00041), -0.38);
        assertTrue(Point.equals(new Point3D(1.0, -4.32, 0.00041), instance.getData(), 1.0e-10));
    }

    /**
     * Test of getTime method, of class TimeSeriesData.
     */
    @Test
    public void testGetTime() {
        System.out.println("getTime");
        TimeSeriesData<Point3D> instance = new TimeSeriesData<>(new Point3D(1.0, -4.32, 0.00041), -0.38);
        assertTrue(Precision.equals(-0.38, instance.getTime(), 1.0e-10));
    }
    
}
