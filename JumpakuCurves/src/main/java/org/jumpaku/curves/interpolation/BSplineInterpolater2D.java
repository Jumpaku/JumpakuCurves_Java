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
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.jumpaku.curves.spline.BSplineCurve;
import org.jumpaku.curves.spline.BSplineCurve2D;
import org.jumpaku.curves.spline.SplineCurve;
import org.jumpaku.curves.vector.Point2D;

/**
 *
 * @author Jumpaku
 */
public class BSplineInterpolater2D implements Interpolater<Point2D, SplineCurve> {
    
    public static class Builder{
        private Integer degree;
        private Stream<Data<Point2D>> data = Stream.empty();
        private Array<Double> knots;

        public Builder addAllData(Iterable<Data<Point2D>> data){
            this.data = this.data.appendAll(data);
            return this;
        }
        
        public Builder addData(Point2D p, Double param){
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
            if(data.size() != knots.size() - degree - 1)
                throw new IllegalStateException("wrong data size, cannot solve");
            
            return new BSplineInterpolater2D(data.toArray(), knots, degree);
        }
    }
    
    public static Builder builder(){
        return new Builder();
    }
    
    private final Array<Data<Point2D>> data;
    
    private final Array<Double> knots;
    
    private final Integer degree;

    public BSplineInterpolater2D(Array<Data<Point2D>> data, Array<Double> knots, Integer degree) {
        this.data = data;
        this.knots = knots;
        this.degree = degree;
    }
    
    @Override
    public BSplineCurve2D interpolate() {
        
        RealMatrix m = new Array2DRowRealMatrix(data.size(), data.size());
        for(int i = 0; i < data.size(); ++i){
            for(int j = 0; j < data.size(); ++j){
                m.setEntry(i, j, BSplineCurve.bSplineBasis(degree, j, data.get(i).getParam(), knots));
            }
        }
        m = MatrixUtils.inverse(m);
        
        RealMatrix dataPoints = new Array2DRowRealMatrix(data.size(), 2);    
        for(int i = 0; i < data.size(); ++i){
            dataPoints.setEntry(i, 0, data.get(i).getPoint().getX());
            dataPoints.setEntry(i, 1, data.get(i).getPoint().getY());
        }
        
        RealMatrix tmp = m.multiply(dataPoints);
        List<Point2D> cp = new LinkedList<>();        
        for(int i = 0; i < data.size(); ++i){
            cp.add(new Point2D(tmp.getEntry(i, 0), tmp.getEntry(i, 1)));
        }
        
        return BSplineCurve2D.create(knots, Array.ofAll(cp), degree);
    }
    
}
