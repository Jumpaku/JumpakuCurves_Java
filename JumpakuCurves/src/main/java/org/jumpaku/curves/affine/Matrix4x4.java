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
import org.jumpaku.curves.vector.Point3D;
import org.jumpaku.curves.vector.Vec3;

/**
 *
 * @author Jumpaku
 */
public class Matrix4x4 implements Affine3D{

    private final RealMatrix matrix;
    
    private static final Matrix4x4 IDENTITY = new Matrix4x4(MatrixUtils.createRealIdentityMatrix(4));

    protected Matrix4x4(RealMatrix matrix){
        if(matrix.getRowDimension() != 4 || matrix.getColumnDimension() != 4)
            throw new IllegalArgumentException("dimention is not 4x4");
        
        this.matrix = matrix.copy();
    }
    
    public static Affine3D identity(){
        return IDENTITY;
    }
    
    
    protected static Point3D convert(RealVector v){
        return new Point3D(v.getEntry(0), v.getEntry(1), v.getEntry(2));
    }

    protected static RealVector convert(Vec3 v){
        return new ArrayRealVector(new double[]{v.getX(), v.getY(), v.getZ(), 1});
    }

    protected static Point3D transform(RealMatrix m, Point3D v){
        return convert(m.operate(convert(new Vec3(v.getVec()))));
    }

    public static Affine3D scaling(Double x, Double y, Double z){
        if(!Double.isFinite(1/x))
            throw new IllegalArgumentException("x must be not 0");
        if(!Double.isFinite(1/y))
            throw new IllegalArgumentException("y must be not 0");
        if(!Double.isFinite(1/z))
            throw new IllegalArgumentException("z must be not 0");

        return new Matrix4x4(MatrixUtils.createRealDiagonalMatrix(new double[]{x, y, z, 1}));
    }
    public static Affine3D rotation(Vec3 axis, Double radian){
        Double x = axis.getX();
        Double y = axis.getY();
        Double z = axis.getZ();
        Double cos = Math.cos(radian);
        Double sin = Math.sin(radian);
        return new Matrix4x4(MatrixUtils.createRealMatrix(new double[][]{
            { x*x*(1-cos)+cos,   x*y*(1-cos)-z*sin, z*x*(1-cos)+y*sin, 0 },
            { x*y*(1-cos)+z*sin, y*y*(1-cos)+cos,   y*z*(1-cos)-x*sin, 0 },
            { z*x*(1-cos)-y*sin, y*z*(1-cos)+x*sin, z*z*(1-cos)+cos,   0 },
            { 0,                 0,                 0,                 1 }
        }));
    }
    public static Affine3D translation(Vec3 move){
        return new Matrix4x4(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 0, 0, move.getX() },
            { 0, 1, 0, move.getY() },
            { 0, 0, 1, move.getZ() },
            { 0, 0, 0, 1 }
        }));
    }
    
    @Override
    public Affine3D scale(Double x, Double y, Double z) {
        return concatenate(scaling(x, y, x));
    }

    @Override
    public Affine3D rotate(Vec3 axis, Double radian) {
        return concatenate(rotation(axis, radian));
    }

    @Override
    public Affine3D translate(Vec3 v) {
        return concatenate(translation(v));
    }

    @Override
    public Affine3D invert() {
        return new Matrix4x4(MatrixUtils.inverse(matrix));
    }

    @Override
    public Affine3D concatenate(Affine3D a) {
        Affine3D original = this;
        return (a instanceof Matrix4x4) ?
                new Matrix4x4(((Matrix4x4)a).matrix.multiply(matrix)) :
                new AbstractAffine3D(){
                    @Override
                    protected Affine3D createScaling(Double x, Double y, Double z) {
                        return Matrix4x4.scaling(x, y, z);
                    }

                    @Override
                    protected Affine3D createRotation(Vec3 axis, Double radian) {
                        return Matrix4x4.rotation(axis, radian);
                    }

                    @Override
                    protected Affine3D createTranslation(Vec3 v) {
                        return Matrix4x4.translation(v);
                    }

                    @Override
                    public Point3D apply(Point3D v) {
                        return a.apply(original.apply(v));
                    }

                    @Override
                    public Affine3D invert() {
                        return a.invert().concatenate(original.invert());
                    }
                };
    }

    @Override
    public Point3D apply(Point3D p) {
        return transform(matrix, p);
    }
}
