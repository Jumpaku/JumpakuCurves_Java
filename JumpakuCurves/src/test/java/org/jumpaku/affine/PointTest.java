/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.PointMatcher.pointOf;
import static org.jumpaku.affine.VectorMatcher.vectorOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class PointTest {
    
    /**
     * Test closed fuzzy method, closed class Point.
     */
    @Test
    public void testFuzzy_PointCrisp_Double() {
        System.out.println("fuzzy");
        Point.Fuzzy p = Point.fuzzy(Point.crisp(1.0, 2.0, -3.0), 10.0);
        assertEquals(1.0, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(-3.0, p.getZ(), 1.0e-10);
        assertEquals(10.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed fuzzy method, closed class Point.
     */
    @Test
    public void testFuzzy_4args() {
        System.out.println("fuzzy");
        Point.Fuzzy p = Point.fuzzy(1.0, 2.0, -3.0, 10.0);
        assertEquals(1.0, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(-3.0, p.getZ(), 1.0e-10);
        assertEquals(10.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed fuzzy method, closed class Point.
     */
    @Test
    public void testFuzzy_3args() {
        System.out.println("fuzzy");
        Point.Fuzzy p = Point.fuzzy(1.0, 2.0, 10.0);
        assertEquals(1.0, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
        assertEquals(10.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed fuzzy method, closed class Point.
     */
    @Test
    public void testFuzzy_Double_Double() {
        System.out.println("fuzzy");
        Point.Fuzzy p = Point.fuzzy(1.0, 10.0);
        assertEquals(1.0, p.getX(), 1.0e-10);
        assertEquals(0.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
        assertEquals(10.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed zero method, closed class Point.
     */
    @Test
    public void testZero_Double() {
        System.out.println("zero");
        Point.Fuzzy p = Point.zero(10.0);
        assertEquals(0.0, p.getX(), 1.0e-10);
        assertEquals(0.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
        assertEquals(10.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed crisp method, closed class Point.
     */
    @Test
    public void testCrisp_3args() {
        System.out.println("crisp");
        Point.Crisp p = Point.crisp(1.0, 2.0, -3.0);
        assertEquals(1.0, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(-3.0, p.getZ(), 1.0e-10);
        assertEquals(0.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed crisp method, closed class Point.
     */
    @Test
    public void testCrisp_Double_Double() {
        System.out.println("crisp");
        Point.Crisp p = Point.crisp(1.0,  2.0);
        assertEquals(1.0, p.getX(), 1.0e-10);
        assertEquals(2.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
        assertEquals(0.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed crisp method, closed class Point.
     */
    @Test
    public void testCrisp_Double() {
        System.out.println("crisp");
        Point.Crisp p = Point.crisp(1.0);
        assertEquals(1.0, p.getX(), 1.0e-10);
        assertEquals(0.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
        assertEquals(0.0, p.getR(), 1.0e-10);
    }

    /**
     * Test closed zero method, closed class Point.
     */
    @Test
    public void testZero_0args() {
        System.out.println("zero");
        Point.Crisp p = Point.ZERO;
        assertEquals(0.0, p.getX(), 1.0e-10);
        assertEquals(0.0, p.getY(), 1.0e-10);
        assertEquals(0.0, p.getZ(), 1.0e-10);
        assertEquals(0.0, p.getR(), 1.0e-10);
    }
    
    @Test
    public void testToJson(){
        System.out.println("toJson");
        assertThat(Point.fromJson(Point.toJson(Point.fuzzy(1.23, 4.56, -7.89, 10.0))).get(),
                is(pointOf(1.23, 4.56, -7.89, 10.0)));
    }

    /**
     * Test closed fromJson method, closed class Point.
     */
    @Test
    public void testFromJson() {
        System.out.println("fromJson");
        assertThat(Point.fromJson("{x:1.23, y:4.56, z:-7.89,r:10.0}").get(), 
                is(pointOf(1.23, 4.56, -7.89, 10.0)));
        assertTrue(Point.fromJson("{x:1.23, y:4.56}").isEmpty());
    }

    /**
     * Test closed toVector method, closed class Point.
     */
    @Test
    public void testToVector() {
        System.out.println("toVector");
        assertThat(Point.fuzzy(1.4, 2.0, -0.2, 2.0e3).toVector(),
                is(vectorOf(1.4, 2.0, -0.2, 2.0e3)));
        assertThat(Point.crisp(1.4, 2.0, -0.2).toVector(),
                is(vectorOf(1.4, 2.0, -0.2, 0.0)));
    }

    /**
     * Test closed toCrisp method, closed class Point.
     */
    @Test
    public void testToCrisp() {
        System.out.println("toCrisp");
        assertThat(Point.fuzzy(-1.0, 2.0, -3.0, 4.0).toCrisp(),
                is(pointOf(-1.0, 2.0, -3.0, 0.0)));
        assertThat(Point.crisp(-1.0, 2.0, -3.0).toCrisp(),
                is(pointOf(-1.0, 2.0, -3.0, 0.0)));
    }

    /**
     * Test closed getR method, closed class Point.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        assertEquals(-3.0, Point.crisp(-3.0, 4.5, -0.3).getX(), 1.0e-10);
        assertEquals(-3.0, Point.fuzzy(-3.0, 4.5, -0.3, 100.0).getX(), 1.0e-10);
    }

    /**
     * Test closed getY method, closed class Point.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        assertEquals(4.5, Point.crisp(-3.0, 4.5, -0.3).getY(), 1.0e-10);
        assertEquals(4.5, Point.fuzzy(-3.0, 4.5, -0.3, 10.0).getY(), 1.0e-10);
     }

    /**
     * Test closed getZ method, closed class Point.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        assertEquals(-0.3, Point.crisp(-3.0, 4.5, -0.3).getZ(), 1.0e-10);
        assertEquals(-0.3, Point.fuzzy(-3.0, 4.5, -0.3, 100.0).getZ(), 1.0e-10);
     }

    /**
     * Test closed getR method, closed class Point.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        assertEquals(0.0, Point.crisp(-3.0, 4.5, -0.3).getR(), 1.0e-10);
        assertEquals(100.0, Point.fuzzy(-3.0, 4.5, -0.3, 100.0).getR(), 1.0e-10);
    }    

    /**
     * Test closed membership method, closed class Point.
     */
    @Test
    public void testMembership() {
        System.out.println("membership");
        Point.Crisp p0 = Point.ZERO;
        Point.Crisp p1 = Point.crisp(1.0);
        Point.Crisp p2 = Point.crisp(2.0);
        Point.Crisp p3 = Point.crisp(3.0);
        
        Point instance1 = Point.zero(2.0);
        assertEquals(1.0, instance1.membership(p0).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.membership(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.membership(p2).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.membership(p3).getValue(), 1.0e-10);
        
        Point instance2 = Point.ZERO;
        assertEquals(1.0, instance2.membership(p0).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.membership(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.membership(p2).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.membership(p3).getValue(), 1.0e-10);
    }

    /**
     * Test closed possibility method, closed class Point.
     */
    @Test
    public void testPossibility() {
        System.out.println("possibility");
        Point p0 = Point.fuzzy(0.0, 1.0);
        Point p1 = Point.fuzzy(0.0, 2.0);
        Point p2 = Point.fuzzy(1.0, 0.5);
        Point p3 = Point.fuzzy(1.0, 2.0);
        Point p4 = Point.fuzzy(2.0, 1.0);
        Point p5 = Point.fuzzy(2.0, 4.0);
        Point p6 = Point.fuzzy(3.0, 1.0);
        Point p7 = Point.fuzzy(3.0, 8.0);
        Point p8 = Point.ZERO;
        Point p9 = Point.crisp(1.0);
        Point p10 = Point.crisp(2.0);
        Point p11 = Point.crisp(3.0);
        
        Point instance1 = Point.zero(2.0);
        assertEquals(1.0, instance1.possibility(p0).getValue(), 1.0e-10);
        assertEquals(1.0, instance1.possibility(p1).getValue(), 1.0e-10);
        assertEquals(3.0/5, instance1.possibility(p2).getValue(), 1.0e-10);
        assertEquals(0.75, instance1.possibility(p3).getValue(), 1.0e-10);
        assertEquals(1.0/3, instance1.possibility(p4).getValue(), 1.0e-10);
        assertEquals(2.0/3, instance1.possibility(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.possibility(p6).getValue(), 1.0e-10);
        assertEquals(0.7, instance1.possibility(p7).getValue(), 1.0e-10);
        assertEquals(1.0, instance1.possibility(p8).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.possibility(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.possibility(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.possibility(p11).getValue(), 1.0e-10);
        
        Point instance2 = Point.ZERO;
        assertEquals(1.0, instance2.possibility(p0).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.possibility(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p2).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.possibility(p3).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p4).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.possibility(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p6).getValue(), 1.0e-10);
        assertEquals(5.0/8, instance2.possibility(p7).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.possibility(p8).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.possibility(p11).getValue(), 1.0e-10);
    }

    /**
     * Test closed necessity method, closed class Point.
     */
    @Test
    public void testNecessity() {
        System.out.println("necessity");
        Point p0 = Point.fuzzy(0.0, 1.0);
        Point p1 = Point.fuzzy(0.0, 2.0);
        Point p2 = Point.fuzzy(1.0, 0.5);
        Point p3 = Point.fuzzy(1.0, 2.0);
        Point p4 = Point.fuzzy(2.0, 1.0);
        Point p5 = Point.fuzzy(2.0, 4.0);
        Point p6 = Point.fuzzy(3.0, 1.0);
        Point p7 = Point.fuzzy(3.0, 8.0);
        Point p8 = Point.ZERO;
        Point p9 = Point.crisp(1.0);
        Point p10 = Point.crisp(2.0);
        Point p11 = Point.crisp(3.0);
        
        Point instance1 = Point.zero(2.0);
        assertEquals(1.0/3, instance1.necessity(p0).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.necessity(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p2).getValue(), 1.0e-10);
        assertEquals(0.25, instance1.necessity(p3).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p4).getValue(), 1.0e-10);
        assertEquals(1.0/3, instance1.necessity(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p6).getValue(), 1.0e-10);
        assertEquals(0.5, instance1.necessity(p7).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p8).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance1.necessity(p11).getValue(), 1.0e-10);
        
        Point instance2 = Point.ZERO;
        assertEquals(1.0, instance2.necessity(p0).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.necessity(p1).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p2).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.necessity(p3).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p4).getValue(), 1.0e-10);
        assertEquals(0.5, instance2.necessity(p5).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p6).getValue(), 1.0e-10);
        assertEquals(5.0/8, instance2.necessity(p7).getValue(), 1.0e-10);
        assertEquals(1.0, instance2.necessity(p8).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p9).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p10).getValue(), 1.0e-10);
        assertEquals(0.0, instance2.necessity(p11).getValue(), 1.0e-10);
    }

    /**
     * Test closed divide method, closed class Point.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        assertThat(Point.crisp(1.0).divide(0.3, Point.crisp(2.0)), 
                is(pointOf(1.3, 0.0, 0.0, 0.0)));
        assertThat(Point.crisp(1.0).divide(-1.0, Point.crisp(2.0)), 
                is(pointOf(0.0, 0.0, 0.0, 0.0)));
        assertThat(Point.crisp(1.0).divide(2.0, Point.crisp(2.0)),
                is(pointOf(3.0, 0.0, 0.0, 0.0)));
        assertThat(Point.crisp(1.0).divide(0.0, Point.crisp(2.0)), 
                is(pointOf(1.0, 0.0, 0.0, 0.0)));
        assertThat(Point.crisp(1.0).divide(1.0, Point.crisp(2.0)),
                is(pointOf(2.0, 0.0, 0.0, 0.0)));

        assertThat(Point.fuzzy(1.0, 10.0).divide(0.3, Point.crisp(2.0)), 
                is(pointOf(1.3, 0.0, 0.0, 7.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(-1.0, Point.crisp(2.0)), 
                is(pointOf(0.0, 0.0, 0.0, 20.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(2.0, Point.crisp(2.0)),
                is(pointOf(3.0, 0.0, 0.0, 10.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(0.0, Point.crisp(2.0)), 
                is(pointOf(1.0, 0.0, 0.0, 10.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(1.0, Point.crisp(2.0)),
                is(pointOf(2.0, 0.0, 0.0, 0.0)));

        assertThat(Point.crisp(1.0).divide(0.3, Point.fuzzy(2.0, 10.0)), 
                is(pointOf(1.3, 0.0, 0.0, 3.0)));
        assertThat(Point.crisp(1.0).divide(-1.0, Point.fuzzy(2.0, 10.0)), 
                is(pointOf(0.0, 0.0, 0.0, 10.0)));
        assertThat(Point.crisp(1.0).divide(2.0, Point.fuzzy(2.0, 10.0)), 
                is(pointOf(3.0, 0.0, 0.0, 20.0)));
        assertThat(Point.crisp(1.0).divide(0.0, Point.fuzzy(2.0, 10.0)), 
                is(pointOf(1.0, 0.0, 0.0, 0.0)));
        assertThat(Point.crisp(1.0).divide(1.0, Point.fuzzy(2.0, 10.0)), 
                is(pointOf(2.0, 0.0, 0.0, 10.0)));

        assertThat(Point.fuzzy(1.0, 10.0).divide(0.3, Point.fuzzy(2.0, 20.0)), 
                is(pointOf(1.3, 0.0, 0.0, 13.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(-1.0, Point.fuzzy(2.0, 20.0)), 
                is(pointOf(0.0, 0.0, 0.0, 40.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(2.0, Point.fuzzy(2.0, 20.0)), 
                is(pointOf(3.0, 0.0, 0.0, 50.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(0.0, Point.fuzzy(2.0, 20.0)), 
                is(pointOf(1.0, 0.0, 0.0, 10.0)));
        assertThat(Point.fuzzy(1.0, 10.0).divide(1.0, Point.fuzzy(2.0, 20.0)), 
                is(pointOf(2.0, 0.0, 0.0, 20.0)));
    }

    /**
     * Test closed toString method, closed class Point.
     */
    @Test
    public void testToString(){
        System.out.println("toString");    
        assertThat(Point.fromJson(Vector.fuzzy(1.23, 4.56, -7.89, 1.0).toString()).get(),
                is(pointOf(1.23, 4.56, -7.89, 1.0)));
        assertThat(Point.fromJson(Vector.crisp(1.23, 4.56, -7.89).toString()).get(),
                is(pointOf(1.23, 4.56, -7.89, 0.0)));
    }
    /**
     * Test closed move method, closed class Point.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        assertThat(Point.crisp(1.4,2.0,-0.7).move(Vector.crisp(3.4, 4.2, 5.4)), 
                is(pointOf(4.8, 6.2, 4.7, 0.0)));
    }

    /**
     * Test closed diff method, closed class Point.
     */
    @Test
    public void testDiff() {
        System.out.println("diff");
        assertThat(Point.crisp(1.4,2.0,-5.1).diff(Point.crisp(2.5, -5.3, 0.3)),
                is(vectorOf(-1.1, 7.3, -5.4, 0.0)));
    }

    /**
     * Test closed dist method, closed class Point.
     */
    @Test
    public void testDist_Point() {
        System.out.println("dist");
        assertEquals(3.0, Point.crisp(1.0, -2.0, 2.0).dist(Point.crisp(2.0, -4.0, 0.0)), 1.0e-10);
    }

    /**
     * Test closed dist method, closed class Point.
     */
    @Test
    public void testDist_Point_Point() {
        System.out.println("dist");
        Point.Crisp a = Point.crisp(1.0, 0.0, -1.0);
        Point.Crisp b = Point.crisp(0.0, 1.0, 1.0);
        Point.Crisp instance = Point.crisp(1.0, 1.0, 0.0);
        assertEquals(Math.pow(2, 0.5)/2, instance.dist(a, b), 1.0e-10);
    }
    
    /**
     * Test closed distSquare method, closed class Point.
     */
    @Test
    public void testDistSquare() {
        System.out.println("distSquare");
        assertEquals(9.0,
                Point.crisp(1.0, -2.0, 2.0).distSquare(Point.crisp(2.0, -4.0, 0.0)), 1.0e-10);
    }

    /**
     * Test closed area method, closed class Point.
     */
    @Test
    public void testArea() {
        System.out.println("area");
        assertEquals(7.5,
                Point.crisp(1.0, 1.0, -1.0).area(Point.crisp(-3.0, 1.0, 2.0), Point.crisp(1.0, 4.0, -1.0)), 1.0e-10);
    }

    /**
     * Test closed area method, closed class Point.
     */
    @Test
    public void testVolume() {
        System.out.println("area");
        assertEquals(1.0/3.0,
                Point.crisp(0.0, 0.0, 0.0).volume(Point.crisp(1.0, 1.0, 0.0), Point.crisp(-1.0, 1.0, 0.0), Point.crisp(1.0, 1.0, -1.0)), 1.0e-10);
    }

    /**
     * Test closed normal method, closed class Point.
     */
    @Test
    public void testNormal() {
        System.out.println("normal");
        assertThat(Point.crisp(1.0, 1.0, 0.0).normal(Point.crisp(-1.0, 1.0, 0.0), Point.crisp(0.0, 1.0, 1.0)),
                is(vectorOf(0.0, 1.0, 0.0, 0.0)));
    }
    
    @Test
    public void testTransform(){
        System.out.println("transform");
        assertThat(Point.crisp(3.3, -2.4, -1.0).transform(Transform.translation(Vector.crisp(-2.3, 5.4, 0.5))),
                is(pointOf(1.0, 3.0, -0.5, 0.0)));
        assertThat(Point.crisp(1.0, 1.0, -1.0).transform(Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), Math.PI*2.0/3.0)), 
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
        assertThat(Point.crisp(3.0, -2.0, -1.0).transform(Transform.scaling(2.3, 5.4, 0.5)),
                is(pointOf(6.9, -10.8, -0.5, 0.0)));
    }
}
