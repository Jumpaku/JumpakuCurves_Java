/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.fuzzy;

import org.jumpaku.curves.vector.Point2D;
import org.jumpaku.curves.vector.Vec2;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
 */
public class Conic2DTest {
    
    public Conic2DTest() {
    }

    /**
     * Test of crisp method, of class Conic2D.
     */
    @Test
    public void testCrisp_Point2D() {
        System.out.println("crisp");
        Point2D p = new Point2D(2.4, 3.5);
        Conic2D expResult = new Conic2D(2.4, 3.5, 0.0);
        Conic2D result = Conic2D.crisp(p);
        assertTrue(Conic2D.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of crisp method, of class Conic2D.
     */
    @Test
    public void testCrisp_Double_Double() {
        System.out.println("crisp");
        Double x = 2.4;
        Double y = 3.5;
        Conic2D expResult = new Conic2D(2.4, 3.5, 0.0);
        Conic2D result = Conic2D.crisp(x, y);
        assertTrue(Conic2D.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of crisp method, of class Conic2D.
     */
    @Test
    public void testCrisp_Vec2() {
        System.out.println("crisp");
        Vec2 v = new Vec2(2.4, 3.5);
        Conic2D expResult = new Conic2D(2.4, 3.5, 0.0);
        Conic2D result = Conic2D.crisp(v);
        assertTrue(Conic2D.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of getFuzzyness method, of class Conic2D.
     */
    @Test
    public void testGetFuzzyness() {
        System.out.println("getRadius");
        Conic2D instance = new Conic2D(2.4, 4.6, 6.8);
        Double expResult = 6.8;
        Double result = instance.getFuzzyness();
        assertEquals(expResult, result);
    }

    /**
     * Test of mu method, of class Conic2D.
     */
    @Test
    public void testMu() {
        System.out.println("mu");
        Point2D p1 = new Point2D(1.0, 1.0);
        Point2D p2 = new Point2D(1.0, 0.0);
        Point2D p3 = new Point2D(1.0, -1.0);
        Point2D p4 = new Point2D(0.0, 0.0);
        Conic2D instance = new Conic2D(1.0, 1.0, 2.0);
        
        Grade expResult1 = Grade.tureValue();
        Grade result1 = instance.mu(p1);
        assertTrue(Grade.equals(expResult1, result1, 1.0e-10));
        
        Grade expResult2 = Grade.of(0.5);
        Grade result2 = instance.mu(p2);
        assertTrue(Grade.equals(expResult2, result2, 1.0e-10));
        
        Grade expResult3 = Grade.falseValue();
        Grade result3 = instance.mu(p3);
        assertTrue(Grade.equals(expResult3, result3, 1.0e-10));
        
        Grade expResult4 = Grade.of(1.0 - 1.0 / Math.sqrt(2));
        Grade result4 = instance.mu(p4);
        assertTrue(Grade.equals(expResult4, result4, 1.0e-10));
    }

    /**
     * Test of possibility method, of class Conic2D.
     */
    @Test
    public void testPossibility() {
        System.out.println("possibility");
        Conic2D p1 = new Conic2D(0.0, 1.0, 2.0);
        Conic2D p2 = new Conic2D(0.0, 2.0, 2.0);
        Conic2D p3 = new Conic2D(0.0, 3.0, 2.0);
        Conic2D p4 = new Conic2D(2.0, 2.0, 2.0);
        Conic2D instance = new Conic2D(0.0, 0.0, 2.0);
        Grade expResult1 = Grade.of(0.75);
        Grade expResult2 = Grade.of(0.5);
        Grade expResult3 = Grade.of(0.25);
        Grade expResult4 = Grade.of(1.0 - 1.0/Math.sqrt(2.0));
        Grade result1 = instance.possibility(p1);
        Grade result2 = instance.possibility(p2);
        Grade result3 = instance.possibility(p3);
        Grade result4 = instance.possibility(p4);
        assertTrue(Grade.equals(expResult1, result1, 1.0e-10));
        assertTrue(Grade.equals(expResult2, result2, 1.0e-10));
        assertTrue(Grade.equals(expResult3, result3, 1.0e-10));
        assertTrue(Grade.equals(expResult4, result4, 1.0e-10));
    }

    /**
     * Test of necessity method, of class Conic2D.
     */
    @Test
    public void testNecessity() {
        System.out.println("necessity");
        Conic2D p1 = new Conic2D(0.0, 0.0, 2.0);
        Conic2D p2 = new Conic2D(1.0, 0.0, 2.0);
        Conic2D p3 = new Conic2D(2.0, 0.0, 2.0);
        Conic2D p4 = new Conic2D(3.0, 0.0, 5.0);
        Conic2D instance = new Conic2D(0.0, 0.0, 2.0);
        Grade expResult1 = Grade.of(0.5);
        Grade expResult2 = Grade.of(0.25);
        Grade expResult3 = Grade.of(0.0);
        Grade expResult4 = Grade.of(0.0);
        Grade result1 = instance.necessity(p1);
        Grade result2 = instance.necessity(p2);
        Grade result3 = instance.necessity(p3);
        Grade result4 = instance.necessity(p4);
        assertTrue(Grade.equals(expResult1, result1, 1.0e-10));
        assertTrue(Grade.equals(expResult2, result2, 1.0e-10));
        assertTrue(Grade.equals(expResult3, result3, 1.0e-10));
        assertTrue(Grade.equals(expResult4, result4, 1.0e-10));
    }

    /**
     * Test of equals method, of class Conic2D.
     */
    @Test
    public void testEquals_3args_1() {
        System.out.println("equals");
        Conic2D p1 = new Conic2D(1.2, 3.4, 3.2);
        Conic2D p2 = new Conic2D(1.2, 3.4, 3.2);
        Double eps = 1.0e-10;
        Boolean expResult = true;
        Boolean result = Conic2D.equals(p1, p2, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Conic2D.
     */
    @Test
    public void testEquals_3args_2() {
        System.out.println("equals");
        Conic2D p1 = new Conic2D(1.2, 3.4, 3.2);
        Conic2D p2 = new Conic2D(1.2, 3.4, 3.2);
        Integer ulp = 1;
        Boolean expResult = true;
        Boolean result = Conic2D.equals(p1, p2, ulp);
        assertEquals(expResult, result);
    }
}
