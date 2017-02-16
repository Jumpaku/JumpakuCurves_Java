/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.Tuple4;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jumpaku
 */
public class AffineTest {
    
    public AffineTest() {
    }

    /**
     * Test of translation method, of class Affine.
     */
    @Test
    public void testTranslation() {
        System.out.println("translation");
        assertTrue(Point.equals(Point.of(1.0, 3.0, -0.5), Affine.translation(Vector.of(-2.3, 5.4, 0.5)).apply(Point.of(3.3, -2.4, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotation method, of class Affine.
     */
    @Test
    public void testRotation() {
        System.out.println("rotation");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0), Affine.rotation(Vector.of(1.0, 1.0, 1.0), Math.PI*2.0/3.0).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of scaling method, of class Affine.
     */
    @Test
    public void testScaling() {
        System.out.println("scaling");
        assertTrue(Point.equals(Point.of(4.0, -6.0, -4.0), Affine.scaling(2.0, 3.0, 4.0).apply(Point.of(2.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of of method, of class Affine.
     */
    @Test
    public void testOf() {
        System.out.println("of");
        assertTrue(Point.equals(Point.of(10.0, 26.0, 42.0), Affine.of(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 2, 3, 4,},
            { 5, 6, 7, 8,},
            { 9,10,11,12,},
            { 0, 0, 0, 1,},
        })).apply(Point.of(1.0, 1.0, 1.0)), 1.0e-10));
    }

    /**
     * Test of identity method, of class Affine.
     */
    @Test
    public void testIdentity() {
        System.out.println("identity");
        assertTrue(Point.equals(Point.of(2.0, -2.0, -1.0), Affine.identity().apply(Point.of(2.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of similarity method, of class Affine.
     */
    @Test
    public void testSimilarity() {
        System.out.println("similarity");
        Tuple2<Point, Point> ab = Tuple.of(Point.of(1.0, 0.0, 0.0), Point.of(0.0, 1.0, 0.0));
        Tuple2<Point, Point> cd = Tuple.of(Point.of(1.0, 1.0,-1.0), Point.of(-1.0,1.0, 1.0));
        assertTrue(Point.equals(Point.of(1.0, -1.0, 1.0), Affine.similarity(ab, cd).apply(Point.of(0.0, 0.0, 1.0)), 1.0e-10));
    }

    /**
     * Test of cariblate method, of class Affine.
     */
    @Test
    public void testCariblate() {
        System.out.println("cariblate");
        Tuple4<Point, Point, Point, Point> befor = Tuple.of(Point.of(1.0, 0.0, 0.0), Point.of(0.0, 1.0, 0.0), Point.of(0.0, 0.0, 1.0), Point.of(-1.0, -1.0, -1.0));
        Tuple4<Point, Point, Point, Point> after = Tuple.of(Point.of(1.0,-1.0, 1.0), Point.of(1.0, 1.0,-1.0), Point.of(-1.0, 1.0, 1.0), Point.of( 1.0,  1.0,  1.0));
        assertTrue(Point.equals(Point.of(1.0,-1.0, 1.0), Affine.cariblate(befor, after).apply(Point.of(1.0, 0.0, 0.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.0, 1.0,-1.0), Affine.cariblate(befor, after).apply(Point.of(0.0, 1.0, 0.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(-1.0, 1.0,1.0), Affine.cariblate(befor, after).apply(Point.of(0.0, 0.0, 1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of( 1.0, 1.0,1.0), Affine.cariblate(befor, after).apply(Point.of(-1.0,-1.0,-1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(0.5, 0.5, 0.5), Affine.cariblate(befor, after).apply(Point.of(0.0, 0.0, 0.0)), 1.0e-10));
    }

    /**
     * Test of transformationAt method, of class Affine.
     */
    @Test
    public void testTransformationAt() {
        System.out.println("transformationAt");
        assertTrue(Point.equals(Point.of(1.0, 3.0, -0.5),
                Affine.transformationAt(Point.of(1.0, 2.0, 3.0),
                        Affine.translation(Vector.of(-2.3, 5.4, 0.5))).apply(Point.of(3.3, -2.4, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.transformationAt(Point.of(1.0, -1.0, 1.0),
                        Affine.rotation(Vector.of(1.0, 1.0, 1.0), Math.PI/3.0)).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(5.6, -15.2, 0.0),
                Affine.transformationAt(Point.of(1.0, 1.0, 1.0),
                        Affine.scaling(2.3, 5.4, 0.5)).apply(Point.of(3.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of transformAt method, of class Affine.
     */
    @Test
    public void testTransformAt() {
        System.out.println("transformAt");
        assertTrue(Point.equals(Point.of(1.0, 3.0, -0.5),
                Affine.identity().transformAt(Point.of(1.0, 2.0, 3.0),
                        Affine.translation(Vector.of(-2.3, 5.4, 0.5))).apply(Point.of(3.3, -2.4, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.identity().transformAt(Point.of(1.0, -1.0, 1.0),
                        Affine.rotation(Vector.of(1.0, 1.0, 1.0), Math.PI/3.0)).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(5.6, -15.2, 0.0),
                Affine.identity().transformAt(Point.of(1.0, 1.0, 1.0),
                        Affine.scaling(2.3, 5.4, 0.5)).apply(Point.of(3.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of scale method, of class Affine.
     */
    @Test
    public void testScale_3args() {
        System.out.println("scale");
        assertTrue(Point.equals(Point.of(4.0, -6.0, -4.0), Affine.identity().scale(2.0, 3.0, 4.0).apply(Point.of(2.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of scale method, of class Affine.
     */
    @Test
    public void testScale_Double() {
        System.out.println("scale");
        assertTrue(Point.equals(Point.of(4.0, -4.0, -2.0), Affine.identity().scale(2.0).apply(Point.of(2.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of scaleAt method, of class Affine.
     */
    @Test
    public void testScaleAt_4args() {
        System.out.println("scaleAt");
        assertTrue(Point.equals(Point.of(3.0, -8.0, -7.0), Affine.identity().scaleAt(Point.of(1.0, 1.0, 1.0), 2.0, 3.0, 4.0).apply(Point.of(2.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of scaleAt method, of class Affine.
     */
    @Test
    public void testScaleAt_Point_Double() {
        System.out.println("scaleAt");
        assertTrue(Point.equals(Point.of(4.0, -8.0, -5.0), Affine.identity().scaleAt(Point.of(1.0, 1.0, 1.0), 3.0).apply(Point.of(2.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotate method, of class Affine.
     */
    @Test
    public void testRotate_Vector_Double() {
        System.out.println("rotate");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0), Affine.identity().rotate(Vector.of(1.0, 1.0, 1.0), Math.PI*2.0/3.0).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotate method, of class Affine.
     */
    @Test
    public void testRotate_3args_1() {
        System.out.println("rotate");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.identity().rotate(Point.of(0.0, 0.0, -1.0), Point.of(1.0, 1.0, 0.0), Math.PI*2.0/3.0).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotateAt method, of class Affine.
     */
    @Test
    public void testRotateAt_3args_1() {
        System.out.println("rotateAt");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.identity().rotateAt(Point.of(1.0, 1.0, 1.0), Vector.of(0.0, 1.0, 0.0), Math.PI/2).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotate method, of class Affine.
     */
    @Test
    public void testRotate_3args_2() {
        System.out.println("rotate");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.identity().rotate(Vector.of(0.0, 1.0, -1.0), Vector.of(-1.0, 1.0, 0.0), Math.PI/3.0*2.0).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotate method, of class Affine.
     */
    @Test
    public void testRotate_Vector_Vector() {
        System.out.println("rotate");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.identity().rotate(Vector.of(0.0, 1.0, -1.0), Vector.of(-1.0, 0.0, 1.0)).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotateAt method, of class Affine.
     */
    @Test
    public void testRotateAt_4args() {
        System.out.println("rotateAt");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.identity().rotateAt(Point.of(1.0, -1.0, 1.0), Vector.of(0.0, 1.0, -1.0), Vector.of(-1.0, 0.0, 1.0), Math.PI/3.0).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of rotateAt method, of class Affine.
     */
    @Test
    public void testRotateAt_3args_2() {
        System.out.println("rotateAt");
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.identity().rotateAt(Point.of(1.0, -1.0, 1.0), Vector.of(0.0, 2.0, -2.0), Vector.of(-2.0, 2.0, 0.0)).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of translate method, of class Affine.
     */
    @Test
    public void testTranslate_Vector() {
        System.out.println("translate");
        assertTrue(Point.equals(Point.of(1.0, 3.0, -0.5), Affine.identity().translate(Vector.of(-2.3, 5.4, 0.5)).apply(Point.of(3.3, -2.4, -1.0)), 1.0e-10));
    }

    /**
     * Test of translate method, of class Affine.
     */
    @Test
    public void testTranslate_3args() {
        System.out.println("translate");
        assertTrue(Point.equals(Point.of(1.0, 3.0, -0.5), Affine.identity().translate(-2.3, 5.4, 0.5).apply(Point.of(3.3, -2.4, -1.0)), 1.0e-10));
    }

    /**
     * Test of apply method, of class Affine.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        assertTrue(Point.equals(Point.of(5.6, -7.8, -1.5),
                Affine.translation(Vector.of(2.3, -5.4, -0.5)).apply(Point.of(3.3, -2.4, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.rotation(Vector.of(1.0, 1.0, 1.0), -Math.PI*4.0/3.0).apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.5, -1.0, -2.0),
                Affine.scaling(0.5, 0.5, 2.0).apply(Point.of(3.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of invert method, of class Affine.
     */
    @Test
    public void testInvert() {
        System.out.println("invert");
        assertTrue(Point.equals(Point.of(5.6, -7.8, -1.5),
                Affine.translation(Vector.of(-2.3, 5.4, 0.5)).invert().apply(Point.of(3.3, -2.4, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(-1.0, 1.0, 1.0),
                Affine.rotation(Vector.of(1.0, 1.0, 1.0), Math.PI*4.0/3.0).invert().apply(Point.of(1.0, 1.0, -1.0)), 1.0e-10));
        assertTrue(Point.equals(Point.of(1.5, -1.0, -2.0),
                Affine.scaling(2.0, 2.0, 0.5).invert().apply(Point.of(3.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of concatnate method, of class Affine.
     */
    @Test
    public void testConcatnate_2args() {
        System.out.println("concatnate");
        assertTrue(Point.equals(Point.of(0.0, 1.0, 3.0),
                Affine.concatnate(Affine.translation(Vector.of(-2.0, 5.0, 1.0)), Affine.rotation(Vector.of(1.0, 1.0, 1.0), Math.PI*2.0/3.0))
                        .apply(Point.of(3.0, -2.0, -1.0)), 1.0e-10));
    }

    /**
     * Test of concatnate method, of class Affine.
     */
    @Test
    public void testConcatnate() {
        System.out.println("concatenate");
        assertTrue(Point.equals(Point.of(0.0, 1.0, 3.0),
                Affine.identity()
                        .concatnate(Affine.translation(Vector.of(-2.0, 5.0, 1.0)))
                        .concatnate(Affine.rotation(Vector.of(1.0, 1.0, 1.0), Math.PI*2.0/3.0))
                        .apply(Point.of(3.0, -2.0, -1.0)), 1.0e-10));
    }
}
