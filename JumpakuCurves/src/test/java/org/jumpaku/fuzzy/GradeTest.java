/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.fuzzy;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class GradeTest {
    
    public GradeTest() {
    }

    /**
     * Test of of method, of class Grade.
     */
    @Test
    public void testOf_Double() {
        System.out.println("of");
        assertEquals(1.0, Grade.of(1.0).getValue(), 1.0e-10);
        assertEquals(0.0, Grade.of(0.0).getValue(), 1.0e-10);
        assertEquals(0.5, Grade.of(0.5).getValue(), 1.0e-10);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testOf_Double_Exception_Grater() {
        System.out.println("of");
        Grade.of(1.5);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testOf_Double_Exception_Less() {
        System.out.println("of");
        Grade.of(-0.5);
    }

    /**
     * Test of clamped method, of class Grade.
     */
    @Test
    public void testClamped() {
        System.out.println("clamped");
        assertEquals(1.0, Grade.clamped(1.0).getValue(), 1.0e-10);
        assertEquals(0.0, Grade.clamped(0.0).getValue(), 1.0e-10);
        assertEquals(0.5, Grade.clamped(0.5).getValue(), 1.0e-10);
        assertEquals(1.0, Grade.clamped(1.5).getValue(), 1.0e-10);
        assertEquals(0.0, Grade.clamped(-0.5).getValue(), 1.0e-10);
    }

    /**
     * Test of of method, of class Grade.
     */
    @Test
    public void testOf_Integer() {
        System.out.println("of");
        assertEquals(1.0, Grade.of(1).getValue(), 1.0e-10);
        assertEquals(0.0, Grade.of(0).getValue(), 1.0e-10);
    }

    /**
     * Test of of method, of class Grade.
     */
    @Test
    public void testOf_Boolean() {
        System.out.println("of");
        assertEquals(1.0, Grade.of(true).getValue(), 1.0e-10);
        assertEquals(0.0, Grade.of(false).getValue(), 1.0e-10);
    }

    /**
     * Test of and method, of class Grade.
     */
    @Test
    public void testAnd_Grade_Grade() {
        System.out.println("and");
        assertEquals(0.3, Grade.and(Grade.of(0.7), Grade.of(0.3)).getValue(), 1.0e-10);
    }

    /**
     * Test of or method, of class Grade.
     */
    @Test
    public void testOr_Grade_Grade() {
        System.out.println("or");
        assertEquals(0.7, Grade.or(Grade.of(0.7), Grade.of(0.3)).getValue(), 1.0e-10);
    }

    /**
     * Test of not method, of class Grade.
     */
    @Test
    public void testNot_Grade() {
        System.out.println("not");
        assertEquals(0.3, Grade.not(Grade.of(0.7)).getValue(), 1.0e-10);
    }

    /**
     * Test of compare method, of class Grade.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        assertEquals(-1, Grade.compare(Grade.of(0.3), Grade.of(0.7)));
        assertEquals(1, Grade.compare(Grade.of(0.9), Grade.of(0.7)));
        assertEquals(0, Grade.compare(Grade.of(0.7), Grade.of(0.7)));
    }

    /**
     * Test of toString method, of class Grade.
     */
    @Test
    public void testToString_Grade() {
        System.out.println("toString");
        assertEquals(0.6, Grade.parseGrade(Grade.toString(Grade.of(0.6))).getValue(), 1.0e-10);
    }

    /**
     * Test of parseGrade method, of class Grade.
     */
    @Test
    public void testParseGrade() {
        System.out.println("parseGrade");
        assertEquals(0.6, Grade.parseGrade("0.6").getValue(), 1.0e-10);
    }

    /**
     * Test of getValue method, of class Grade.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        assertEquals(1.0, Grade.of(1.0).getValue(), 1.0e-10);
        assertEquals(0.0, Grade.of(0.0).getValue(), 1.0e-10);
        assertEquals(0.5, Grade.of(0.5).getValue(), 1.0e-10);
    }

    /**
     * Test of and method, of class Grade.
     */
    @Test
    public void testAnd_Grade() {
        System.out.println("and");
        assertEquals(0.3, Grade.of(0.7).and(Grade.of(0.3)).getValue(), 1.0e-10);
    }

    /**
     * Test of or method, of class Grade.
     */
    @Test
    public void testOr_Grade() {
        System.out.println("or");
        assertEquals(0.7, Grade.of(0.7).or(Grade.of(0.3)).getValue(), 1.0e-10);
    }

    /**
     * Test of not method, of class Grade.
     */
    @Test
    public void testNot_0args() {
        System.out.println("not");
        assertEquals(0.7, Grade.of(0.3).not().getValue(), 1.0e-10);
    }

    /**
     * Test of toString method, of class Grade.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        assertEquals(0.6, Grade.parseGrade(Grade.of(0.6).toString()).getValue(), 1.0e-10);
    }

    /**
     * Test of compareTo method, of class Grade.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        assertEquals(-1, Grade.of(0.3).compareTo(Grade.of(0.7)));
        assertEquals(1, Grade.of(0.9).compareTo(Grade.of(0.7)));
        assertEquals(0, Grade.of(0.7).compareTo(Grade.of(0.7)));
    }
    
}
