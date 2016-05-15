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
    
    public static Matrix3x3AffineTransform scaling(Double x, Double y){
        return new Matrix3x3AffineTransform(MatrixUtils.createRealDiagonalMatrix(new double[]{x, y, 1}));
    }
    public static Matrix3x3AffineTransform scaling(Double scalar){
        return scaling(scalar, scalar);
    }    
    public static Matrix3x3AffineTransform rotation(Double radian){
        return new Matrix3x3AffineTransform(MatrixUtils.createRealMatrix(new double[][]{
            { Math.cos(radian), Math.sin(radian), 0 },
            { Math.sin(radian), -Math.sin(radian), 0},
            {0, 0, 1}
        }));
    }
    public static AffineTransform2D rotationAt(Vector2D center, Double radian){
        return rotation(radian).translate(center);
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
    
    protected final Double get(Integer i, Integer j){
        return matrix.getEntry(i, j);
    }
    
    @Override
    public final AffineTransform2D scale(Double x, Double y) {
        return concatenate(scaling(x, y));
    }

    @Override
    public final AffineTransform2D scale(Double scalar) {
        return scale(scalar, scalar);
    }

    @Override
    public final AffineTransform2D rotate(Double radian) {
        return concatenate(rotation(radian));
    }
    
    @Override
    public final AffineTransform2D rotateAt(Vector2D center, Double radian) {
        return invert().rotate(radian).translate(center);
    }

    @Override
    public final AffineTransform2D translate(Vector2D v) {
        return concatenate(translation(v));
    }

    @Override
    public final AffineTransform2D shearAt(Vector2D pivot, Double x, Double y) {
        return invert().shear(x, y).translate(pivot);
    }

    @Override
    public final AffineTransform2D shear(Double x, Double y) {
        return concatenate(shearing(x, y));
    }

    @Override
    public final Vector2D apply(Vector2D v) {
        return multiply(matrix, v);
    }

    @Override
    public final AffineTransform2D invert() {
        return new Matrix3x3AffineTransform(MatrixUtils.inverse(matrix));
    }

    @Override
    public final AffineTransform2D concatenate(AffineTransform2D t) {
        AffineTransform2D original = this;
        return (t instanceof Matrix3x3AffineTransform) ?
                new Matrix3x3AffineTransform(matrix.multiply(((Matrix3x3AffineTransform)t).matrix)) :
                new AbstractAffineTransform2D(){
                    @Override
                    protected AffineTransform2D scaling(Double x, Double y) {
                        return Matrix3x3AffineTransform.scaling(x, y);
                    }

                    @Override
                    protected AffineTransform2D rotation(Double radian) {
                        return Matrix3x3AffineTransform.rotation(radian);
                    }

                    @Override
                    protected AffineTransform2D translation(Vector2D v) {
                        return Matrix3x3AffineTransform.translation(v);
                    }

                    @Override
                    protected AffineTransform2D shearing(Double x, Double y) {
                        return Matrix3x3AffineTransform.shearing(x, y);
                    }

                    @Override
                    public Vector2D apply(Vector2D v) {
                        return t.apply(original.apply(v));
                    }
                };
    }
}
