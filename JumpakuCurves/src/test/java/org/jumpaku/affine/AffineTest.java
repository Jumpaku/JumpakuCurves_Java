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
import static org.hamcrest.core.Is.is;
import static org.jumpaku.affine.PointMatcher.pointOf;

/**
 *
 * @author Jumpaku
 */
public class AffineTest {

    /**
     * Test of translation method, of class Transform.
     */
    @Test
    public void testTranslation() {
        System.out.println("translation");
        assertThat(Transform.translation(Vector.crisp(-2.3, 5.4, 0.5)).apply(Point.crisp(3.3, -2.4, -1.0)), 
                is(pointOf(1.0, 3.0, -0.5, 0.0)));
    }

    /**
     * Test of rotation method, of class Transform.
     */
    @Test
    public void testRotation() {
        System.out.println("rotation");
        assertThat(Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), Math.PI*2.0/3.0).apply(Point.crisp(1.0, 1.0, -1.0)), 
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of scaling method, of class Transform.
     */
    @Test
    public void testScaling() {
        System.out.println("scaling");
        assertThat(Transform.scaling(2.0, 3.0, 4.0).apply(Point.crisp(2.0, -2.0, -1.0)), 
                is(pointOf(4.0, -6.0, -4.0, 0.0)));
    }

    /**
     * Test of of method, of class Transform.
     */
    @Test
    public void testOf() {
        System.out.println("of");
        assertThat(Transform.of(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 2, 3, 4,},
            { 5, 6, 7, 8,},
            { 9,10,11,12,},
            { 0, 0, 0, 1,},
        })).apply(Point.crisp(1.0, 1.0, 1.0)), 
                is(pointOf(10.0, 26.0, 42.0, 0.0)));
    }

    /**
     * Test of id method, of class Transform.
     */
    @Test
    public void testId() {
        System.out.println("identity");
        assertThat(Transform.id().apply(Point.crisp(2.0, -2.0, -1.0)), 
                is(pointOf(2.0, -2.0, -1.0, 0.0)));
    }

    /**
     * Test of similarity method, of class Transform.
     */
    @Test
    public void testSimilarity() {
        System.out.println("similarity");
        Tuple2<Point.Crisp, Point.Crisp> ab = Tuple.of(Point.crisp(1.0, 0.0, 0.0), Point.crisp(0.0, 1.0, 0.0));
        Tuple2<Point.Crisp, Point.Crisp> cd = Tuple.of(Point.crisp(1.0, 1.0,-1.0), Point.crisp(-1.0,1.0, 1.0));
        assertThat(Transform.similarity(ab, cd).apply(Point.crisp(0.0, 0.0, 1.0)),
                is(pointOf(1.0, -1.0, 1.0, 0.0)));
    }

    /**
     * Test of calibrate method, of class Transform.
     */
    @Test
    public void testCalibrate() {
        System.out.println("calibrate");
        Tuple4<Point.Crisp, Point.Crisp, Point.Crisp, Point.Crisp> before = Tuple.of(Point.crisp(1.0, 0.0, 0.0), Point.crisp(0.0, 1.0, 0.0), Point.crisp(0.0, 0.0, 1.0), Point.crisp(-1.0, -1.0, -1.0));
        Tuple4<Point.Crisp, Point.Crisp, Point.Crisp, Point.Crisp> after = Tuple.of(Point.crisp(1.0,-1.0, 1.0), Point.crisp(1.0, 1.0,-1.0), Point.crisp(-1.0, 1.0, 1.0), Point.crisp( 1.0,  1.0,  1.0));
        assertThat(Transform.calibrate(before, after).apply(Point.crisp(1.0, 0.0, 0.0)),
                is(pointOf(1.0,-1.0, 1.0, 0.0)));
        assertThat(Transform.calibrate(before, after).apply(Point.crisp(0.0, 1.0, 0.0)),
                is(pointOf(1.0, 1.0,-1.0, 0.0)));
        assertThat(Transform.calibrate(before, after).apply(Point.crisp(0.0, 0.0, 1.0)),
                is(pointOf(-1.0, 1.0,1.0, 0.0)));
        assertThat(Transform.calibrate(before, after).apply(Point.crisp(-1.0,-1.0,-1.0)),
                is(pointOf( 1.0, 1.0,1.0, 0.0)));
        assertThat(Transform.calibrate(before, after).apply(Point.crisp(0.0, 0.0, 0.0)),
                is(pointOf(0.5, 0.5, 0.5, 0.0)));
    }

    /**
     * Test of transformationAt method, of class Transform.
     */
    @Test
    public void testTransformationAt() {
        System.out.println("transformationAt");
        assertThat(Transform.transformationAt(Point.crisp(1.0, 2.0, 3.0),
                        Transform.translation(Vector.crisp(-2.3, 5.4, 0.5))).apply(Point.crisp(3.3, -2.4, -1.0)),
                is(pointOf(1.0, 3.0, -0.5, 0.0)));
        assertThat(Transform.transformationAt(Point.crisp(1.0, -1.0, 1.0),
                        Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), Math.PI/3.0)).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
        assertThat(Transform.transformationAt(Point.crisp(1.0, 1.0, 1.0),
                        Transform.scaling(2.3, 5.4, 0.5)).apply(Point.crisp(3.0, -2.0, -1.0)),
                is(pointOf(5.6, -15.2, 0.0, 0.0)));
    }

    /**
     * Test of transformAt method, of class Transform.
     */
    @Test
    public void testTransformAt() {
        System.out.println("transformAt");
        assertThat(Transform.id().transformAt(Point.crisp(1.0, 2.0, 3.0),
                        Transform.translation(Vector.crisp(-2.3, 5.4, 0.5))).apply(Point.crisp(3.3, -2.4, -1.0)),
                is(pointOf(1.0, 3.0, -0.5, 0.0)));
        assertThat(Transform.id().transformAt(Point.crisp(1.0, -1.0, 1.0),
                        Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), Math.PI/3.0)).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
        assertThat(Transform.id().transformAt(Point.crisp(1.0, 1.0, 1.0),
                        Transform.scaling(2.3, 5.4, 0.5)).apply(Point.crisp(3.0, -2.0, -1.0)),
                is(pointOf(5.6, -15.2, 0.0, 0.0)));
    }

    /**
     * Test of scale method, of class Transform.
     */
    @Test
    public void testScale_3args() {
        System.out.println("scale");
        assertThat(Transform.id().scale(2.0, 3.0, 4.0).apply(Point.crisp(2.0, -2.0, -1.0)),
                is(pointOf(4.0, -6.0, -4.0, 0.0)));
    }

    /**
     * Test of scale method, of class Transform.
     */
    @Test
    public void testScale_Double() {
        System.out.println("scale");
        assertThat(Transform.id().scale(2.0).apply(Point.crisp(2.0, -2.0, -1.0)), 
                is(pointOf(4.0, -4.0, -2.0, 0.0)));
    }

    /**
     * Test of scaleAt method, of class Transform.
     */
    @Test
    public void testScaleAt_4args() {
        System.out.println("scaleAt");
        assertThat(Transform.id().scaleAt(Point.crisp(1.0, 1.0, 1.0), 2.0, 3.0, 4.0).apply(Point.crisp(2.0, -2.0, -1.0)),
                is(pointOf(3.0, -8.0, -7.0, 0.0)));
    }

    /**
     * Test of scaleAt method, of class Transform.
     */
    @Test
    public void testScaleAt_Point_Double() {
        System.out.println("scaleAt");
        assertThat(Transform.id().scaleAt(Point.crisp(1.0, 1.0, 1.0), 3.0).apply(Point.crisp(2.0, -2.0, -1.0)),
                is(pointOf(4.0, -8.0, -5.0, 0.0)));
    }

    /**
     * Test of rotate method, of class Transform.
     */
    @Test
    public void testRotate_Vector_Double() {
        System.out.println("rotate");
        assertThat(Transform.id().rotate(Vector.crisp(1.0, 1.0, 1.0), Math.PI*2.0/3.0).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of rotate method, of class Transform.
     */
    @Test
    public void testRotate_3args_1() {
        System.out.println("rotate");
        assertThat(Transform.id().rotate(Point.crisp(0.0, 0.0, -1.0), Point.crisp(1.0, 1.0, 0.0), Math.PI*2.0/3.0).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of rotateAt method, of class Transform.
     */
    @Test
    public void testRotateAt_3args_1() {
        System.out.println("rotateAt");
        assertThat(Transform.id().rotateAt(Point.crisp(1.0, 1.0, 1.0), Vector.crisp(0.0, 1.0, 0.0), Math.PI/2).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of rotate method, of class Transform.
     */
    @Test
    public void testRotate_3args_2() {
        System.out.println("rotate");
        assertThat(Transform.id().rotate(Vector.crisp(0.0, 1.0, -1.0), Vector.crisp(-1.0, 1.0, 0.0), Math.PI/3.0*2.0).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of rotate method, of class Transform.
     */
    @Test
    public void testRotate_Vector_Vector() {
        System.out.println("rotate");
        assertThat(Transform.id().rotate(Vector.crisp(0.0, 1.0, -1.0), Vector.crisp(-1.0, 0.0, 1.0)).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of rotateAt method, of class Transform.
     */
    @Test
    public void testRotateAt_4args() {
        System.out.println("rotateAt");
        assertThat(Transform.id().rotateAt(Point.crisp(1.0, -1.0, 1.0), Vector.crisp(0.0, 1.0, -1.0), Vector.crisp(-1.0, 0.0, 1.0), Math.PI/3.0).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of rotateAt method, of class Transform.
     */
    @Test
    public void testRotateAt_3args_2() {
        System.out.println("rotateAt");
        assertThat(Transform.id().rotateAt(Point.crisp(1.0, -1.0, 1.0), Vector.crisp(0.0, 2.0, -2.0), Vector.crisp(-2.0, 2.0, 0.0)).apply(Point.crisp(1.0, 1.0, -1.0)), 
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
    }

    /**
     * Test of move method, of class Transform.
     */
    @Test
    public void testTranslate_Vector() {
        System.out.println("translate");
        assertThat(Transform.id().translate(Vector.crisp(-2.3, 5.4, 0.5)).apply(Point.crisp(3.3, -2.4, -1.0)), 
                is(pointOf(1.0, 3.0, -0.5, 0.0)));
    }

    /**
     * Test of move method, of class Transform.
     */
    @Test
    public void testTranslate_3args() {
        System.out.println("translate");
        assertThat(Transform.id().translate(-2.3, 5.4, 0.5).apply(Point.crisp(3.3, -2.4, -1.0)),
                is(pointOf(1.0, 3.0, -0.5, 0.0)));
    }

    /**
     * Test of apply method, of class Transform.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        assertThat(Transform.translation(Vector.crisp(2.3, -5.4, -0.5)).apply(Point.crisp(3.3, -2.4, -1.0)),
                is(pointOf(5.6, -7.8, -1.5, 0.0)));
        assertThat(Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), -Math.PI*4.0/3.0).apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
        assertThat(Transform.scaling(0.5, 0.5, 2.0).apply(Point.crisp(3.0, -2.0, -1.0)),
                is(pointOf(1.5, -1.0, -2.0, 0.0)));
    }

    /**
     * Test of invert method, of class Transform.
     */
    @Test
    public void testInvert() {
        System.out.println("invert");
        assertThat(Transform.translation(Vector.crisp(-2.3, 5.4, 0.5)).invert().apply(Point.crisp(3.3, -2.4, -1.0)),
                is(pointOf(5.6, -7.8, -1.5, 0.0)));
        assertThat(Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), Math.PI*4.0/3.0).invert().apply(Point.crisp(1.0, 1.0, -1.0)),
                is(pointOf(-1.0, 1.0, 1.0, 0.0)));
        assertThat(Transform.scaling(2.0, 2.0, 0.5).invert().apply(Point.crisp(3.0, -2.0, -1.0)),
                is(pointOf(1.5, -1.0, -2.0, 0.0)));
    }

    /**
     * Test of concatenate method, of class Transform.
     */
    @Test
    public void testConcatenate_2args() {
        System.out.println("concatenate");
        assertThat(Transform.concatenate(Transform.translation(Vector.crisp(-2.0, 5.0, 1.0)), Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), Math.PI*2.0/3.0))
                        .apply(Point.crisp(3.0, -2.0, -1.0)),
                is(pointOf(0.0, 1.0, 3.0, 0.0)));
    }

    /**
     * Test of concatenate method, of class Transform.
     */
    @Test
    public void testConcatenate() {
        System.out.println("concatenate");
        assertThat(Transform.id()
                        .concatenate(Transform.translation(Vector.crisp(-2.0, 5.0, 1.0)))
                        .concatenate(Transform.rotation(Vector.crisp(1.0, 1.0, 1.0), Math.PI*2.0/3.0))
                        .apply(Point.crisp(3.0, -2.0, -1.0)), 
                is(pointOf(0.0, 1.0, 3.0, 0.0)));
    }
}
