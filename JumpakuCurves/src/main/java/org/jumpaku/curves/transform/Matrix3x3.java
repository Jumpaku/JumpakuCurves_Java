/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.transform;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author Jumpaku
 */
public class Matrix3x3 implements Affine2D {

    private final RealMatrix matrix;
    
    private static final Matrix3x3 IDENTITY = new Matrix3x3(MatrixUtils.createRealIdentityMatrix(3));

    protected Matrix3x3(RealMatrix matrix){
        this.matrix = matrix.copy();
    }
    
    protected static Vector2D convert(RealVector v){
        return new Vector2D(v.getEntry(0), v.getEntry(1));
    }
    protected static RealVector convert(Vector2D v){
        return new ArrayRealVector(new double[]{v.getX(), v.getY(), 1});
    }
    protected static Vector2D multiply(RealMatrix m, Vector2D v){
        return convert(m.operate(convert(v)));
    }
    
    public static Affine2D createScaling(Double x, Double y){
        if(x.compareTo(0.0) == 0)
            throw new IllegalArgumentException("x must be not 0");
        if(y.compareTo(0.0) == 0)
            throw new IllegalArgumentException("y must be not 0");
        return new Matrix3x3(MatrixUtils.createRealDiagonalMatrix(new double[]{x, y, 1}));
    }
    public static Affine2D createRotation(Double radian){
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { Math.cos(radian), -Math.sin(radian), 0 },
            { Math.sin(radian), Math.cos(radian), 0},
            {0, 0, 1}
        }));
    }
    public static Affine2D createTranslation(Vector2D move){
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 0, move.getX() },
            { 0, 1, move.getY() },
            { 0, 0, 1 }
        }));
    }
    public static Affine2D createShearing(Double x, Double y){
        if(Double.compare(x*y, 1.0) == 0)
            throw new IllegalArgumentException("x*y must be not 1");
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { 1, x, 0 },
            { y, 1, 0 },
            { 0, 0, 1 }
        }));
    }
    public static Affine2D createScaling(Double scalar){
        return createScaling(scalar, scalar);
    }
    public static Affine2D createScalingAt(Vector2D center, Double scalar){
        return createScalingAt(center, scalar, scalar);
    }
    public static Affine2D createScalingAt(Vector2D center, Double x, Double y){
        return createTranslation(center.negate()).scale(x, y).translate(center);
    }
    public static Affine2D createRotationAt(Vector2D center, Double radian){
        return createTranslation(center.negate()).rotate(radian).translate(center);
    }
    public static Affine2D createShearingAt(Vector2D pivot, Double x, Double y){
        return createTranslation(pivot.negate()).shear(x, y).translate(pivot);
    }
    public static Affine2D createShearingX(Double x){
        return createShearing(x, 0.0);
    }    
    public static Affine2D createShearingY(Vector2D pivot, Double x, Double y){
        return createShearing(0.0, y);
    }
    public static Affine2D createShearingXAt(Vector2D v, Double x){
        return createShearingAt(v, x, 0.0);
    }
    public static Affine2D createShearingYAt(Vector2D v, Double y){
        return createShearingAt(v, 0.0, y);
    }
    public static Affine2D createSqueeze(Double k){
        return createScaling(k, 1/k);
    }
    public static Affine2D createRefrectOrigin(){
        return createScaling(-1.0);
    }
    public static Affine2D createRefrectXAxis(){
        return createScaling(1.0, -1.0);
    }
    public static Affine2D createRefrectYAxis(){
        return createScaling(-1.0, 1.0);
    }
    
    public static Affine2D identity(){
        return IDENTITY;
    }
    
    protected final Double get(Integer i, Integer j){
        return matrix.getEntry(i, j);
    }
    
    @Override
    public final Affine2D scale(Double x, Double y) {
        return concatenate(createScaling(x, y));
    }
    @Override
    public final Affine2D rotate(Double radian) {
        return concatenate(createRotation(radian));
    }
    @Override
    public final Affine2D translate(Vector2D v) {
        return concatenate(createTranslation(v));
    }
    @Override
    public final Affine2D shear(Double x, Double y) {
        return concatenate(createShearing(x, y));
    }

    @Override
    public final Vector2D apply(Vector2D v) {
        return multiply(matrix, v);
    }

    @Override
    public final Affine2D invert() {
        return new Matrix3x3(MatrixUtils.inverse(matrix));
    }

    @Override
    public final Affine2D concatenate(Affine2D t) {
        Affine2D original = this;
        return (t instanceof Matrix3x3) ?
                new Matrix3x3(((Matrix3x3)t).matrix.multiply(matrix)) :
                new AbstractAffine2D(){
                    @Override
                    protected Affine2D createScaling(Double x, Double y) {
                        return Matrix3x3.createScaling(x, y);
                    }

                    @Override
                    protected Affine2D createRotation(Double radian) {
                        return Matrix3x3.createRotation(radian);
                    }

                    @Override
                    protected Affine2D createTranslation(Vector2D v) {
                        return Matrix3x3.createTranslation(v);
                    }

                    @Override
                    protected Affine2D createShearing(Double x, Double y) {
                        return Matrix3x3.createShearing(x, y);
                    }

                    @Override
                    public Vector2D apply(Vector2D v) {
                        return t.apply(original.apply(v));
                    }

                    @Override
                    public Affine2D invert() {
                        return t.invert().concatenate(original.invert());
                    }
                };
    }
}
