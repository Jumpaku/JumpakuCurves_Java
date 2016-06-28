/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.affine;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.jumpaku.curves.vector.Point2D;
import org.jumpaku.curves.vector.Vec2;

/**
 *
 * @author Jumpaku
 */
public class Matrix3x3 implements Affine2D {

    private final RealMatrix matrix;
    
    private static final Matrix3x3 IDENTITY = new Matrix3x3(MatrixUtils.createRealIdentityMatrix(3));

    protected Matrix3x3(RealMatrix matrix){
        if(matrix.getRowDimension() != 3 || matrix.getColumnDimension() != 3)
            throw new IllegalArgumentException("dimention is not 3x3");
                
        this.matrix = matrix.copy();
    }
    
    public static Affine2D identity(){
        return IDENTITY;
    }
    
    protected static Point2D convert(RealVector v){
        return new Point2D(v.getEntry(0), v.getEntry(1));
    }

    protected static RealVector convert(Vec2 v){
        return new ArrayRealVector(new double[]{v.getX(), v.getY(), 1});
    }

    protected static Point2D transform(RealMatrix m, Point2D v){
        return convert(m.operate(convert(new Vec2(v.getVec()))));
    }

    public static Affine2D scaling(Double x, Double y){
        if(!Double.isFinite(1/x))
            throw new IllegalArgumentException("x must be not 0");
        if(!Double.isFinite(1/y))
            throw new IllegalArgumentException("y must be not 0");
        
        return new Matrix3x3(MatrixUtils.createRealDiagonalMatrix(new double[]{x, y, 1}));
    }
    public static Affine2D rotation(Double radian){
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { Math.cos(radian), -Math.sin(radian), 0 },
            { Math.sin(radian), Math.cos(radian), 0},
            {0, 0, 1}
        }));
    }
    public static Affine2D translation(Vec2 move){
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 0, move.getX() },
            { 0, 1, move.getY() },
            { 0, 0, 1 }
        }));
    }
    public static Affine2D shearing(Double x, Double y){
        if(Double.compare(x*y, 1.0) == 0)
            throw new IllegalArgumentException("x*y must be not 1");
        return new Matrix3x3(MatrixUtils.createRealMatrix(new double[][]{
            { 1, x, 0 },
            { y, 1, 0 },
            { 0, 0, 1 }
        }));
    }
        
    @Override
    public final Affine2D scale(Double x, Double y) {
        return concatenate(scaling(x, y));
    }
    @Override
    public final Affine2D rotate(Double radian) {
        return concatenate(rotation(radian));
    }
    @Override
    public final Affine2D translate(Vec2 v) {
        return concatenate(translation(v));
    }
    @Override
    public final Affine2D shear(Double x, Double y) {
        return concatenate(shearing(x, y));
    }

    @Override
    public final Point2D apply(Point2D v) {
        return transform(matrix, v);
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
                        return Matrix3x3.scaling(x, y);
                    }

                    @Override
                    protected Affine2D createRotation(Double radian) {
                        return Matrix3x3.rotation(radian);
                    }

                    @Override
                    protected Affine2D createTranslation(Vec2 v) {
                        return Matrix3x3.translation(v);
                    }

                    @Override
                    protected Affine2D createShearing(Double x, Double y) {
                        return Matrix3x3.shearing(x, y);
                    }

                    @Override
                    public Point2D apply(Point2D v) {
                        return t.apply(original.apply(v));
                    }

                    @Override
                    public Affine2D invert() {
                        return t.invert().concatenate(original.invert());
                    }
                };
    }
}
