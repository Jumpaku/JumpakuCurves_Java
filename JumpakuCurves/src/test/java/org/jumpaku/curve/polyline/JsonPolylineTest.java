/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import java.lang.reflect.Type;
import org.jumpaku.json.Converter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tomohiko
 */
public class JsonPolylineTest {
    
    public JsonPolylineTest() {
    }

    /**
     * Test of getTemporaryType method, of class JsonPolyline.
     */
    @Test
    public void testGetTemporaryType() {
        System.out.println("getTemporaryType");
        JsonPolyline instance = new JsonPolyline();
        Type expResult = null;
        Type result = instance.getTemporaryType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTemporary method, of class JsonPolyline.
     */
    @Test
    public void testToTemporary() {
        System.out.println("toTemporary");
        Polyline polyline = null;
        JsonPolyline instance = new JsonPolyline();
        Converter.Temporary<Polyline> expResult = null;
        Converter.Temporary<Polyline> result = instance.toTemporary(polyline);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
