/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.fitting;

import java.util.Comparator;
import org.jumpaku.curves.TimeSeriesData;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.curves.spline.BSpline;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 * @param <P>
 */
public class SplineFitting<P extends Point> {
    private final Array<Double> knots;
    private final Array<TimeSeriesData<P>> data;
    private final Integer degree;

    public SplineFitting(Double maxKnotInterval, Integer degree, Array<TimeSeriesData<P>> data) {
        this.knots = BSpline.createUniformedClampedKnots(degree, data.head().getTime(), data.last().getTime(), maxKnotInterval);
        this.degree = degree;
        this.data = data;
    }
    
    public BSpline fit(Integer degree, Array<Double> knots, Array<TimeSeriesData<P>> data){
        return BSpline.create(knots, coreateControlPoints(degree, knots, data), degree, data.get().getData().getDimention());
    }
    private static <P extends Point> Array<Point> coreateControlPoints(Integer degree, Array<Double> knots, Array<TimeSeriesData<P>> data){
        final int dataSize = data.size();
        final int knotsSize = knots.size();
        final int cpSize = knotsSize - degree - 1;
        final int dimention = data.head().getData().getDimention();
        
        final double[][] dArray = new double[dataSize][dimention];
        for(int i = 0; i < dataSize; ++i){
            for (int j = 0; j < dimention; j++) {
                dArray[i][j] = data.get(i).getData().get(j);
            }
        }
        final double[][] nArray = new double[dataSize][cpSize];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < cpSize; j++) {
                nArray[i][j] = BSpline.bSplineBasis(degree, j, data.get(i).getTime(), knots);
            }
        }
        final double[] wArray = new double[dataSize];
        for (int i = 0; i < dataSize; i++) {
            wArray[i] = data.get(i).getWeight();
            
        }
        
        final RealMatrix w = new DiagonalMatrix(wArray); // W
        final RealMatrix d = MatrixUtils.createRealMatrix(dArray); // D
        final RealMatrix n = MatrixUtils.createRealMatrix(nArray); // N
        final RealMatrix a = n.transpose().multiply(w).multiply(n); // A = N^T W N
        final RealMatrix y = n.transpose().multiply(w).multiply(d); // y = N^T W D
        RealMatrix x =  new LUDecomposition(a).getSolver().solve(y); // x = A^-1 y
        
        return Stream.of(x.getData())
                .map(ds -> Point.of(Stream.ofAll(ds).toJavaArray(Double.class)))
                .toArray();
    }
    
    public static <P extends Point> Array<Point> coreateControlPoints(final Integer degree, Double maxKnotInterval, Array<TimeSeriesData<P>> data){
        //data = data.sorted(Comparator.comparing(TimeSeriesData::getTime));
        final Double begin = data.head().getTime();
        final Double end = data.last().getTime();
        final Array<Double> knots = BSpline.createUniformedClampedKnots(degree, begin, end, maxKnotInterval);
        
        return coreateControlPoints(degree, knots, data);
    }
}
