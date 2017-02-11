/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import java.util.function.UnaryOperator;
import javaslang.Tuple4;
import javaslang.collection.Array;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author Jumpaku
 */
public interface Affine extends UnaryOperator<Point>{

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
        return new Affine() {
            @Override public Point apply(Point p) {
                double[] array = m.operate(new double[]{p.getX(), p.getY(), p.getZ(), 1.0});
                return Point.of(array[0], array[1], array[2]);
            }

            @Override public Affine invert() {
                return of(MatrixUtils.inverse(m));
            }
        };
    }
    
    static Affine IDENTITY = new Affine() {
        @Override public Point apply(Point p) {
            return p;
        }

        @Override public Affine invert() {
            return this;
        }

        @Override
        public Affine concatenate(Affine a) {
            return a;
        }
    };
    
    static Affine identity(){
        return IDENTITY;
    }
    
    static Affine changeBasis(Tuple4<Point, Point, Point, Point> befor, Tuple4<Point, Point, Point, Point> after){
        RealMatrix a = MatrixUtils.createRealMatrix(new double[][]{{},{},{},{}});
        RealMatrix b = MatrixUtils.createRealMatrix(new double[][]{{},{},{},{}});
        return of(MatrixUtils.inverse(a).multiply(b));
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
        return rotate(axisTerminal.difference(axisInitial), radian);
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
    
    default Affine concatenate(Affine a){
        return concatnate(this, a);
    }
}
