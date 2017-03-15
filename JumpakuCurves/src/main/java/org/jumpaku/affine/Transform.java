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
public interface Transform extends UnaryOperator<Point.Crisp>{

    public static final class Matrix implements Transform{

        private final RealMatrix matrix;

        public Matrix(RealMatrix matrix) {
            this.matrix = matrix;
        }
        
        @Override public Point.Crisp apply(Point.Crisp p) {
            double[] array = matrix.operate(new double[]{p.getX(), p.getY(), p.getZ(), 1.0});
            return Point.crisp(array[0], array[1], array[2]);
        }

        @Override public Transform invert() {
            return new Matrix(MatrixUtils.inverse(matrix));
        }

        @Override
        public Transform concatnate(Transform a) {
            return a instanceof Matrix ?
                    new Matrix(((Matrix)a).matrix.multiply(matrix)) : Transform.super.concatnate(a);
        }
    }
    
    static Transform translation(Vector.Crisp v){
        return of(MatrixUtils.createRealMatrix(new double[][]{
            { 1, 0, 0, v.getX() },
            { 0, 1, 0, v.getY() },
            { 0, 0, 1, v.getZ() },
            { 0, 0, 0, 1 }
        }));
    }
        
    static Transform rotation(Vector.Crisp axis, Double radian){
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
    
    static Transform scaling(Double x, Double y, Double z){
        return of(MatrixUtils.createRealDiagonalMatrix(new double[]{x, y, z, 1}));
    }
    
    static Transform of(RealMatrix m){
        return new Matrix(m);
    }
    
    static Transform IDENTITY = of(MatrixUtils.createRealIdentityMatrix(4));
    
    static Transform id(){
        return IDENTITY;
    }
    
    static Transform similarity(Tuple2<Point.Crisp, Point.Crisp> ab, Tuple2<Point.Crisp, Point.Crisp> cd){
        Vector.Crisp a = ab._2().diff(ab._1());
        Vector.Crisp b = cd._2().diff(cd._1());
        Vector.Crisp ac = cd._1().diff(ab._1());
        return id().rotateAt(ab._1(), a, b).scaleAt(ab._1(), b.length()/a.length()).translate(ac);
    }
    
    static Transform cariblate(Tuple4<Point.Crisp, Point.Crisp, Point.Crisp, Point.Crisp> befor, Tuple4<Point.Crisp, Point.Crisp, Point.Crisp, Point.Crisp> after){
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
    
    static Transform transformationAt(Point.Crisp p, Transform a){
        return translation(p.toVector().negate()).concatnate(a).translate(p.toVector());
    }
    
    default Transform transformAt(Point.Crisp p, Transform a){
        return concatnate(transformationAt(p, a));
    }
    
    default Transform scale(Double x, Double y, Double z){
        return concatnate(scaling(x, y, z));
    }
    
    default Transform scale(Double scale){
        return scale(scale, scale, scale);
    }
    
    default Transform scaleAt(Point.Crisp center, Double x, Double y, Double z){
        return transformAt(center, scaling(x, y, z));
    }
    
    default Transform scaleAt(Point.Crisp center, Double scale){
        return scaleAt(center, scale, scale, scale);
    }
    
    default Transform rotate(Vector.Crisp axis, Double radian){
        return concatnate(rotation(axis, radian));
    }

    default Transform rotate(Point.Crisp axisInitial, Point.Crisp axisTerminal, Double radian){
        return rotate(axisTerminal.diff(axisInitial), radian);
    }
 
    default Transform rotateAt(Point.Crisp center, Vector.Crisp axis, Double radian){
        return transformAt(center, rotation(axis, radian));
    }
    
    default Transform rotate(Vector.Crisp from, Vector.Crisp to, Double radian){
        return rotate(from.cross(to), radian);
    }
    
    default Transform rotate(Vector.Crisp from, Vector.Crisp to){
        return rotate(from, to, from.angle(to));
    }
    
    default Transform rotateAt(Point.Crisp p, Vector.Crisp from, Vector.Crisp to, Double radian){
        return transformAt(p, rotation(from.cross(to), radian));
    }
    
    default Transform rotateAt(Point.Crisp p, Vector.Crisp from, Vector.Crisp to){
        return rotateAt(p, from, to, from.angle(to));
    }

    default Transform translate(Vector.Crisp v){
        return concatnate(translation(v));
    }
    
    default Transform translate(Double x, Double y, Double z){
        return translate(Vector.crisp(x, y, z));
    }

    @Override Point.Crisp.Crisp apply(Point.Crisp t);
    
    Transform invert();
    
    /**
     * transforms a point p to b(a(p))
     * @param a
     * @param b
     * @return 
     */
    static Transform concatnate(Transform a, Transform b){
        return new Transform() {
            @Override public Point.Crisp apply(Point.Crisp p) {
                return a.andThen(b).apply(p);
            }

            @Override public Transform invert() {
                return Transform.concatnate(b.invert(), a.invert());
            }

            @Override public Transform concatnate(Transform c) {
                return Transform.concatnate(Transform.concatnate(a, b), c);
            }
        };
    }
    
    /**
     * transforms a point p to a(this(p))
     * @param a
     * @return 
     */
    default Transform concatnate(Transform a){
        return concatnate(this, a);
    }
}
