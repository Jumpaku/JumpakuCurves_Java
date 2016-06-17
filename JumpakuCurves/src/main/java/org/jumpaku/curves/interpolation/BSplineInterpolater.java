/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Array;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.jumpaku.curves.spline.BSplineCurveDeBoor;
import org.jumpaku.curves.utils.MathUtils;

/**
 *
 * @author ito tomohiko
 */
public class BSplineInterpolater implements Interoprater<Euclidean2D, Vector2D, BSplineCurveDeBoor<Euclidean2D, Vector2D>>{
    public static class Builder{
        private Integer degree;
        private final Array<Data<Euclidean2D, Vector2D>> data;
        private Array<Double> knots;
        
        public Builder(Array<Data<Euclidean2D, Vector2D>> data){
            this.data = data;
        }
        
        public Builder degree(Integer degree){
            this.degree = degree;
            return this;
        }
        
        public Builder knots(Array<Double> knots){
            this.knots = knots;
            return this;
        }
        
        public BSplineInterpolater build(){
            return new BSplineInterpolater(data, knots, degree);
        }

        public Integer getDegree() {
            return degree;
        }

        public Array<Data<Euclidean2D, Vector2D>> getData() {
            return data;
        }

        public Array<Double> getKnots() {
            return knots;
        }
    }
    
    public static Builder builder(Array<Data<Euclidean2D, Vector2D>> data){
        return new Builder(data);
    }
    
    private final Array<Data<Euclidean2D, Vector2D>> data;
    
    private final Array<Double> knots;
    
    private final Integer degree;

    public BSplineInterpolater(Array<Data<Euclidean2D, Vector2D>> data, Array<Double> knots, Integer degree) {
        this.data = data;
        this.knots = knots;
        this.degree = degree;
    }
    
    @Override
    public BSplineCurveDeBoor<Euclidean2D, Vector2D> interpolate() {
        
        RealMatrix m = new Array2DRowRealMatrix(degree+1, degree+1);
        for(int i = 0; i <= degree; ++i){
            for(int j = 0; j <= degree; ++j){
                m.setEntry(i, j, MathUtils.bSplineBasis(degree, j, data.get(i).getParam(), knots));
            }
        }
        System.out.println(m);
        m = MatrixUtils.inverse(m);
        
        RealMatrix dataPoint = new Array2DRowRealMatrix(degree+1, 2);    
        for(int i = 0; i <= degree; ++i){
            dataPoint.setEntry(i, 0, data.get(i).getPoint().getX());
            dataPoint.setEntry(i, 1, data.get(i).getPoint().getY());
        }
        
        RealMatrix tmp = m.multiply(dataPoint);
        List<Vector2D> cp = new LinkedList<>();        
        for(int i = 0; i <= degree; ++i){
            cp.add(new Vector2D(tmp.getEntry(i, 0), tmp.getEntry(i, 1)));
        }
        
        return new BSplineCurveDeBoor<>(knots, Array.ofAll(cp), degree);
    }
    
}
