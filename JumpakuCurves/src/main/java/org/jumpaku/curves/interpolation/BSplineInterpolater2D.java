/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.jumpaku.curves.spline.BSplineCurve;
import org.jumpaku.curves.spline.BSplineCurveDeBoor;
import org.jumpaku.curves.spline.SplineCurve;

/**
 *
 * @author Jumpaku
 */
public class BSplineInterpolater2D implements Interpolater<Euclidean2D, Vector2D, SplineCurve<Euclidean2D, Vector2D>>{
    
    public static class Builder{
        private Integer degree;
        private Stream<Data<Euclidean2D, Vector2D>> data = Stream.empty();
        private Array<Double> knots;

        public Builder addAllData(Iterable<Data<Euclidean2D, Vector2D>> data){
            this.data = this.data.appendAll(data);
            return this;
        }
        
        public Builder addData(Vector2D p, Double param){
            data = data.append(new Data<>(p, param));
            return this;
        }
        
        public Builder degree(Integer degree){
            this.degree = degree;
            return this;
        }
        
        public Builder knots(Array<Double> knots){
            this.knots = knots;
            return this;
        }
        
        public BSplineInterpolater2D build(){
            return new BSplineInterpolater2D(data.toArray(), knots, degree);
        }
    }
    
    public static Builder builder(){
        return new Builder();
    }
    
    private final Array<Data<Euclidean2D, Vector2D>> data;
    
    private final Array<Double> knots;
    
    private final Integer degree;

    public BSplineInterpolater2D(Array<Data<Euclidean2D, Vector2D>> data, Array<Double> knots, Integer degree) {
        this.data = data;
        this.knots = knots;
        this.degree = degree;
    }
    
    @Override
    public SplineCurve<Euclidean2D, Vector2D> interpolate() {
        
        RealMatrix m = new Array2DRowRealMatrix(degree+1, degree+1);
        for(int i = 0; i <= degree; ++i){
            for(int j = 0; j <= degree; ++j){
                m.setEntry(i, j, BSplineCurve.bSplineBasis(degree, j, data.get(i).getParam(), knots));
            }
        }
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
