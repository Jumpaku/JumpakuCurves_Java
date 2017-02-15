/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import java.util.function.UnaryOperator;
import javaslang.Tuple2;
import javaslang.Tuple4;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author Jumpaku
 */
public interface Affine extends UnaryOperator<Point>{

    public static class Matrix implements Affine{

        private final RealMatrix matrix;

        public Matrix(RealMatrix matrix) {
            this.matrix = matrix;
        }
        
        @Override public Point apply(Point p) {
            double[] array = matrix.operate(new double[]{p.getX(), p.getY(), p.getZ(), 1.0});
            return Point.of(array[0], array[1], array[2]);
        }

        @Override public Affine invert() {
            return new Matrix(MatrixUtils.inverse(matrix));
        }

        @Override
        public Affine concatenate(Affine a) {
            return a instanceof Matrix ?
                    new Matrix(((Matrix)a).matrix.multiply(matrix)) : Affine.super.concatenate(a);
        }
    }
    
    static Affine translation(Vector v){
        return of(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 0, 0, v.getX() },
            { 0, 1, 0, v.getY() },
            { 0, 0, 1, v.getZ() },
            { 0, 0, 0, 1 }
        }));
    }
        
    static Affine rotation(Vector axis, Double radian){
        axis = axis.normalize();
        Double x = axis.getX();
        Double y = axis.getY();
        Double z = axis.getZ();
        Double cos = FastMath.cos(radian);
        Double sin = FastMath.sin(radian);
        return of(MatrixUtils.createRealMatrix(new double[][]{
            { x*x*(1-cos)+cos,   x*y*(1-cos)-z*sin, z*x*(1-cos)+y*sin, 0 },
            { x*y*(1-cos)+z*sin, y*y*(1-cos)+cos,   y*z*(1-cos)-x*sin, 0 },
            { z*x*(1-cos)-y*sin, y*z*(1-cos)+x*sin, z*z*(1-cos)+cos,   0 },
            { 0,                 0,                 0,                 1 }
        }));
    }
    
    static Affine scaling(Double x, Double y, Double z){
        return of(MatrixUtils.createRealDiagonalMatrix(new double[]{x, y, z, 1}));
    }
    
    static Affine of(RealMatrix m){
        return new Matrix(m);
    }
    
    static Affine IDENTITY = of(MatrixUtils.createRealIdentityMatrix(4));
    
    static Affine identity(){
        return IDENTITY;
    }
    
    static Affine similarity(Tuple2<Point, Point> ab, Tuple2<Point, Point> cd){
        Vector a = ab._2().diff(ab._1());
        Vector b = cd._2().diff(cd._1());
        Vector ac = cd._1().diff(ab._1());
        return identity().rotateAt(ab._1(), a, b).translate(ac);
    }
    
    static Affine cariblate(Tuple4<Point, Point, Point, Point> befor, Tuple4<Point, Point, Point, Point> after){
        RealMatrix a = MatrixUtils.createRealMatrix(new double[][]{
            {befor._1().getX(), befor._1().getY(), befor._1().getZ(), 1},
            {befor._2().getX(), befor._2().getY(), befor._2().getZ(), 1},
            {befor._3().getX(), befor._3().getY(), befor._3().getZ(), 1},
            {befor._4().getX(), befor._4().getY(), befor._4().getZ(), 1}}).transpose();
        RealMatrix b = MatrixUtils.createRealMatrix(new double[][]{
            {after._1().getX(), after._1().getY(), after._1().getZ(), 1},
            {after._2().getX(), after._2().getY(), after._2().getZ(), 1},
            {after._3().getX(), after._3().getY(), after._3().getZ(), 1},
            {after._4().getX(), after._4().getY(), after._4().getZ(), 1}}).transpose();
        
        return of(b.multiply(MatrixUtils.inverse(a)));
    }
    
    static Affine transformationAt(Point p, Affine a){
        return translation(p.getVector().negate()).concatenate(a).translate(p.getVector());
    }
    
    default Affine transformAt(Point p, Affine a){
        return concatenate(transformationAt(p, a));
    }
    
    default Affine scale(Double x, Double y, Double z){
        return concatenate(scaling(x, y, z));
    }
    
    default Affine scale(Double scale){
        return scale(scale, scale, scale);
    }
    
    default Affine scaleAt(Point center, Double x, Double y, Double z){
        return transformAt(center, scaling(x, y, z));
    }
    
    default Affine scaleAt(Point center, Double scale){
        return scaleAt(center, scale, scale, scale);
    }
    
    default Affine rotate(Vector axis, Double radian){
        return concatenate(rotation(axis, radian));
    }

    default Affine rotate(Point axisInitial, Point axisTerminal, Double radian){
        return rotate(axisTerminal.diff(axisInitial), radian);
    }
 
    default Affine rotateAt(Point center, Vector axis, Double radian){
        return transformAt(center, rotation(axis, radian));
    }
    
    default Affine rotate(Vector from, Vector to, Double radian){
        return rotate(from.cross(to), radian);
    }
    
    default Affine rotate(Vector from, Vector to){
        return rotate(from, to, from.angle(to));
    }
    
    default Affine rotateAt(Point p, Vector from, Vector to, Double radian){
        return transformAt(p, rotation(from.cross(to), radian));
    }
    
    default Affine rotateAt(Point p, Vector from, Vector to){
        return rotateAt(p, from, to, from.angle(to));
    }

    default Affine translate(Vector v){
        return concatenate(translation(v));
    }
    
    default Affine translate(Double x, Double y, Double z){
        return translate(Vector.of(x, y, z));
    }

    @Override Point apply(Point t);
    
    Affine invert();
    
    /**
     * transforms a point p to b(a(p))
     * @param a
     * @param b
     * @return 
     */
    static Affine concatnate(Affine a, Affine b){
        return new Affine() {
            @Override public Point apply(Point p) {
                return a.andThen(b).apply(p);
            }

            @Override public Affine invert() {
                return concatnate(b.invert(), a.invert());
            }

            @Override public Affine concatenate(Affine c) {
                return concatnate(concatnate(a, b), c);
            }
        };
    }
    
    /**
     * transforms a point p to a(this(p))
     * @param a
     * @return 
     */
    default Affine concatenate(Affine a){
        return concatnate(this, a);
    }
}
