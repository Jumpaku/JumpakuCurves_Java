/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.transform;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author ito
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
        return new Matrix3x3(MatrixUtils.createRealDiagonalMatrix(new double[]{x, y, 1}));
    }
    public static Affine2D createScaling(Double scalar){
        return Matrix3x3.createScaling(scalar, scalar);
    }
    public static Affine2D createScalingAt(Vector2D center, Double scalar){
        return createScalingAt(center, scalar, scalar);
    }
    public static Affine2D createScalingAt(Vector2D center, Double x, Double y){
        return createTranslation(center.negate()).scale(x, y).translate(center);
    }
    public static Affine2D createRotation(Double radian){
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { Math.cos(radian), -Math.sin(radian), 0 },
            { Math.sin(radian), Math.cos(radian), 0},
            {0, 0, 1}
        }));
    }
    public static Affine2D createRotationAt(Vector2D center, Double radian){
        return createTranslation(center.negate()).rotate(radian).translate(center);
    }
    public static Affine2D createTranslation(Vector2D v){
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 0, v.getX() },
            { 0, 1, v.getY() },
            { 0, 0, 1 }
        }));
    }
    public static Affine2D createShearing(Double x, Double y){
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { 1, x, 0 },
            { y, 1, 0 },
            { 0, 0, 1 }
        }));
    }
    public static Affine2D createShearingAt(Vector2D pivot, Double x, Double y){
        return createTranslation(pivot.negate()).shear(x, y).translate(pivot);
    }
    
    public static Affine2D identity(){
        return IDENTITY;
    }
    
    protected final Double get(Integer i, Integer j){
        return matrix.getEntry(i, j);
    }
    
    @Override
    public final Affine2D scale(Double x, Double y) {
        return concatenate(Matrix3x3.createScaling(x, y));
    }

    @Override
    public final Affine2D scale(Double scalar) {
        return scale(scalar, scalar);
    }

    @Override
    public Affine2D scaleAt(Vector2D center, Double x, Double y) {
        return concatenate(createScalingAt(center, x, y));
    }

    @Override
    public Affine2D scaleAt(Vector2D center, Double scale) {
        return concatenate(createScalingAt(center, scale));
    }
    
    @Override
    public final Affine2D rotate(Double radian) {
        return concatenate(createRotation(radian));
    }
    
    @Override
    public final Affine2D rotateAt(Vector2D center, Double radian) {
        return invert().rotate(radian).translate(center);
    }

    @Override
    public final Affine2D translate(Vector2D v) {
        return concatenate(createTranslation(v));
    }

    @Override
    public final Affine2D shearAt(Vector2D pivot, Double x, Double y) {
        return invert().shear(x, y).translate(pivot);
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
                };
    }
}
