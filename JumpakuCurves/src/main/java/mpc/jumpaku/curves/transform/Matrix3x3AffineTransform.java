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
public class Matrix3x3AffineTransform implements AffineTransform2D {

    private final RealMatrix matrix;

    public Matrix3x3AffineTransform() {
        this.matrix = MatrixUtils.createRealIdentityMatrix(3);
    }
    
    public Matrix3x3AffineTransform(RealMatrix matrix){
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
    
    public static Matrix3x3AffineTransform scaling(Double scalar){
        return new Matrix3x3AffineTransform(MatrixUtils.createRealDiagonalMatrix(new double[]{scalar, scalar, 1}));
    }
    
    public static Matrix3x3AffineTransform rotation(Double radian){
        return new Matrix3x3AffineTransform(MatrixUtils.createRealMatrix(new double[][]{
            { Math.cos(radian), Math.sin(radian), 0 },
            { Math.sin(radian), -Math.sin(radian), 0},
            {0, 0, 1}
        }));
    }
    public static Matrix3x3AffineTransform translation(Vector2D v){
        return new Matrix3x3AffineTransform(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 0, v.getX() },
            { 0, 1, v.getY() },
            { 0, 0, 1 }
        }));
    }
    public static Matrix3x3AffineTransform shearing(Double x, Double y){
        return new Matrix3x3AffineTransform(MatrixUtils.createRealMatrix(new double[][]{
            { 1, x, 0 },
            { y, 1, 0 },
            { 0, 0, 1 }
        }));
    }
    
    public static Matrix3x3AffineTransform identity(){
        return new Matrix3x3AffineTransform();
    }
    
    @Override
    public AffineTransform2D scale(Double scalar) {
        return concatenate(scaling(scalar));
    }

    @Override
    public AffineTransform2D rotate(Double radian) {
        return concatenate(rotation(radian));
    }

    @Override
    public AffineTransform2D translate(Vector2D v) {
        return concatenate(translation(v));
    }

    @Override
    public AffineTransform2D shear(Double x, Double y) {
        return concatenate(shearing(x, y));
    }

    @Override
    public Vector2D apply(Vector2D v) {
        return multiply(matrix, v);
    }

    @Override
    public AffineTransform2D invert() {
        return new Matrix3x3AffineTransform(MatrixUtils.inverse(matrix));
    }

    @Override
    public AffineTransform2D concatenate(AffineTransform2D t) {
        return new Matrix3x3AffineTransform();
    }
    
}
