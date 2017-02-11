/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.interpolation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Vec;
import org.jumpaku.old.curves.spline.Spline;
import org.jumpaku.old.curves.spline.BSpline;

/**
 *
 * @author Jumpaku
 */
public class BSplineInterpolater implements Interpolater<Spline> {
    
    public static class Builder{
        private final Integer degree;
        private Stream<Data<Point>> data = Stream.empty();
        private Array<Double> knots = Array.empty();
        private final Integer dimention;

        public Builder(Integer degree, Integer dimention) {
            this.degree = degree;
            this.dimention = dimention;
        }
        
        public Builder addAllData(Iterable<Data<Point>> data){
            this.data = this.data.appendAll(data);
            return this;
        }
        
        public Builder addData(Point p, Double param){
            data = data.append(new Data<>(p, param));
            return this;
        }
        
        public Builder parameterize(Array<Point> points, Parameterizer parameterizer){
            addAllData(parameterizer.parameterize(points));
            return this;
        }
        
        public Builder parameterize(Array<Point> points, Parameterizer parameterizer, Double a, Double b){
            addAllData(parameterizer.parameterize(points, a, b));
            return this;
        }
        public Builder knots(Array<Double> knots){
            this.knots = knots;
            return this;
        }
        
        public Builder generateKnots(KnotGenerator generator){
            knots(generator.generate(degree, data.toArray()));
            return this;
        }
        
        public BSplineInterpolater build(){
            if(knots.isEmpty() || data.isEmpty())
                throw new IllegalStateException("data or knots are not set");
            
            if(data.size() != knots.size() - degree - 1)
                throw new IllegalStateException("wrong data size");
            
            return new BSplineInterpolater(data.toArray(), knots, degree, dimention);
        }
    }
    
    public static Builder builder(Integer degree, Integer dimention){
        return new Builder(degree, dimention);
    }
    
    private final Array<Data<Point>> data;
    
    private final Array<Double> knots;
    
    private final Integer degree;

    private final Integer dimention;
    
    public BSplineInterpolater(Array<Data<Point>> data, Array<Double> knots, Integer degree, Integer dimention) {
        this.data = data;
        this.knots = knots;
        this.degree = degree;
        this.dimention = dimention;
    }
    
    @Override
    public BSpline interpolate() {
        
        RealMatrix m = new Array2DRowRealMatrix(data.size(), data.size());
        for(int i = 0; i < data.size(); ++i){
            for(int j = 0; j < data.size(); ++j){
                m.setEntry(i, j, BSpline.bSplineBasis(degree, j, data.get(i).getParam(), knots));
            }
        }
        m = MatrixUtils.inverse(m);
        
        RealMatrix dataPoints = new Array2DRowRealMatrix(data.size(), 2);    
        for(int i = 0; i < data.size(); ++i){
            for(int j = 0; j < dimention; ++j){
                dataPoints.setEntry(i, j, data.get(i).getPoint().get(j));
            }
        }
        
        RealMatrix tmp = m.multiply(dataPoints);
        List<Point> cp = new LinkedList<>();        
        for(int i = 0; i < data.size(); ++i){
            cp.add(Point.of(Vec.of(Arrays.stream(tmp.getColumn(i)).boxed().toArray(Double[]::new))));
        }
        
        return BSpline.create(knots, Array.ofAll(cp), degree, dimention);
    }
    
}
