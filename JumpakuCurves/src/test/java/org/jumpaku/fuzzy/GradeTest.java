/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.fuzzy;

import java.util.Objects;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ito tomohiko
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
        Double value = 0.0;
        Grade expResult = new Grade(0.0);
        Grade result = Grade.of(value);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
        
        value = 1.0;
        expResult = new Grade(1.0);
        result = Grade.of(value);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
        
        value = 0.5;
        expResult = new Grade(0.5);
        result = Grade.of(value);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));   
    }

    /**
     * Test of of method, of class Grade.
     */
    @Test
    public void testOf_Boolean() {
        System.out.println("of");
        Boolean b1 = true;
        Grade expResult1 = Grade.of(1.0);
        Grade result1 = Grade.of(b1);
        assertTrue(Grade.equals(expResult1, result1, 1.0e-10));
        Boolean b2 = false;
        Grade expResult2 = Grade.of(0.0);
        Grade result2 = Grade.of(b2);
        assertTrue(Grade.equals(expResult2, result2, 1.0e-10));
    }

    /**
     * Test of tureValue method, of class Grade.
     */
    @Test
    public void testTureValue() {
        System.out.println("tureValue");
        Grade expResult = Grade.of(1.0);
        Grade result = Grade.tureValue();
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of falseValue method, of class Grade.
     */
    @Test
    public void testFalseValue() {
        System.out.println("falseValue");
        Grade expResult = Grade.of(0.0);
        Grade result = Grade.falseValue();
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of and method, of class Grade.
     */
    @Test
    public void testAnd_Grade_Grade() {
        System.out.println("and");
        Grade g1 = Grade.of(0.7);
        Grade g2 = Grade.of(0.4);
        Grade expResult = Grade.of(0.4);
        Grade result = Grade.and(g1, g2);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of or method, of class Grade.
     */
    @Test
    public void testOr_Grade_Grade() {
        System.out.println("or");
        Grade g1 = Grade.of(0.7);
        Grade g2 = Grade.of(0.4);
        Grade expResult = Grade.of(0.7);
        Grade result = Grade.or(g1, g2);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of not method, of class Grade.
     */
    @Test
    public void testNot_Grade() {
        System.out.println("not");
        Grade g = Grade.of(0.6);
        Grade expResult = Grade.of(0.4);
        Grade result = Grade.not(g);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of compare method, of class Grade.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Grade g = Grade.of(0.7);
        Grade g1 = Grade.of(0.6);
        Grade g2 = Grade.of(0.7);
        Grade g3 = Grade.of(0.8);
        assertTrue(Grade.compare(g, g1) > 0);
        assertTrue(Grade.compare(g, g2) == 0);
        assertTrue(Grade.compare(g, g3) < 0);
    }

    /**
     * Test of toString method, of class Grade.
     */
    @Test
    public void testToString_Grade() {
        System.out.println("toString");
        Grade g = Grade.of(0.5);
        String expResult = "0.5";
        String result = Grade.toString(g);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseGrade method, of class Grade.
     */
    @Test
    public void testParseGrade() {
        System.out.println("parseGrade");
        String s = "0.75";
        Grade expResult = Grade.of(0.75);
        Grade result = Grade.parseGrade(s);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of doubleValue method, of class Grade.
     */
    @Test
    public void testDoubleValue() {
        System.out.println("doubleValue");
        Grade instance = Grade.of(0.86);
        Double expResult = 0.86;
        Double result = instance.doubleValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of and method, of class Grade.
     */
    @Test
    public void testAnd_Grade() {
        System.out.println("and");
        Grade g = Grade.of(0.3);
        Grade instance = Grade.of(1.0);
        Grade expResult = Grade.of(0.3);
        Grade result = instance.and(g);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of or method, of class Grade.
     */
    @Test
    public void testOr_Grade() {
        System.out.println("or");
        Grade g = Grade.of(0.9);
        Grade instance = Grade.of(0.7);
        Grade expResult = Grade.of(0.9);
        Grade result = instance.or(g);
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of not method, of class Grade.
     */
    @Test
    public void testNot_0args() {
        System.out.println("not");
        Grade instance = Grade.of(0.4);
        Grade expResult = Grade.of(0.6);
        Grade result = instance.not();
        assertTrue(Grade.equals(expResult, result, 1.0e-10));
    }

    /**
     * Test of toString method, of class Grade.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        Grade instance = Grade.of(0.1234);
        String expResult = "" + 0.1234;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Grade.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Grade instance = Grade.of(0.3);
        int expResult = 43 * 3 + Objects.hashCode(0.3);
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Grade.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Grade g1 = Grade.of(0.2);
        Grade g2 = Grade.of(0.3);
        Grade g3 = Grade.of(0.4);
        Grade instance = Grade.of(0.3);
        assertTrue(instance.compareTo(g1) > 0);
        assertTrue(instance.compareTo(g2) == 0);
        assertTrue(instance.compareTo(g3) < 0);

    }

    /**
     * Test of equals method, of class Grade.
     */
    @Test
    public void testEquals_3args_1() {
        System.out.println("equals");
        Grade g1 = Grade.of(0.2);
        Grade g2 = Grade.of(0.2);
        Double eps = 1.0e-10;
        Boolean expResult = true;
        Boolean result = Grade.equals(g1, g2, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Grade.
     */
    @Test
    public void testEquals_3args_2() {
        System.out.println("equals");
        Grade g1 = Grade.of(0.2);
        Grade g2 = Grade.of(0.2);
        Integer ulp = 3;
        Boolean expResult = true;
        Boolean result = Grade.equals(g1, g2, ulp);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Grade.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Grade instance = Grade.of(0.3);
        Object g1 = null;
        Object g2 = new Object();
        Object g3 = Grade.of(0.3);
        Object g4 = instance;
        boolean expResult1 = false;
        boolean expResult2 = false;
        boolean expResult3 = true;
        boolean expResult4 = true;
        boolean result1 = instance.equals(g1);
        boolean result2 = instance.equals(g2);
        boolean result3 = instance.equals(g3);
        boolean result4 = instance.equals(g4);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
    }

    /**
     * Test of equals method, of class Grade.
     */
    @Test
    public void testEquals_Grade_Double() {
        System.out.println("equals");
        Grade g = Grade.of(0.2);
        Grade instance = Grade.of(0.2);
        Double eps = 1.0e-10;
        Boolean expResult = true;
        Boolean result = instance.equals(g, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Grade.
     */
    @Test
    public void testEquals_Grade_Integer() {
        System.out.println("equals");
        Grade g = Grade.of(0.2);
        Grade instance = Grade.of(0.2);
        Integer ulp = 3;
        Boolean expResult = true;
        Boolean result = instance.equals(g, ulp);
        assertEquals(expResult, result);
    }
    
}
