/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.fitting;

import java.util.stream.DoubleStream;
import javaslang.collection.Array;
import javaslang.collection.List;
import javaslang.collection.Stream;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.curves.spline.BSpline;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.spline.Spline;
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
        this.knots = createKnots(degree, data.head().getTime(), data.last().getTime(), maxKnotInterval);
        this.degree = degree;
        this.data = data;
    }
    
    public static Array<Double> createKnots(Integer degree, Double begin, Double end, Double maxKnotInterval){
        long n = (long)(FastMath.ceil(end - begin/maxKnotInterval));
        return Stream.fill(degree, () -> begin)
                .appendAll(Stream.rangeClosed(0, n)
                        .map(i -> begin*(n - i)/n + end*i/n))
                .appendAll(Stream.fill(degree, () -> end))
                .toArray();
    }
    
    public static <P extends Point> Array<Point> coreateControlPoints(final Integer degree, Double maxKnotInterval, Array<TimeSeriesData<P>> data){
        final Double tHead = data.head().getTime();
        final Double tLast = data.last().getTime();
        final Point pHead = data.head().getData();
        final Point pLast = data.last().getData();
        final Array<Double> knots = createKnots(degree, tHead, tLast, maxKnotInterval);
        final int dataSize = data.size();
        final int knotsSize = knots.size();
        final int cpSize = knotsSize - degree - 1;
        final int dimention = pHead.getDimention();
        
        final double[][] dArray = new double[dataSize - 2][dimention];
        for(int i = 1; i <= dataSize - 1; ++i){
            double ti = data.get(i).getTime();
            Vec qi = data.get(i).getData().getVec()
                    .sub(pHead.getVec().scale(BSpline.bSplineBasis(degree, 1, ti, knots)))
                    .sub(pLast.getVec().scale(BSpline.bSplineBasis(degree, cpSize - 1, ti, knots)));
            for (int j = 0; j < dimention; j++) {
                dArray[i-1][j] = qi.get(j);
            }
        }
        
        final double[][] nArray = new double[dataSize - 2][cpSize - 2];
        for (int i = 1; i <= dataSize - 1; i++) {
            double ti = data.get(i).getTime();
            for (int j = 1; j < cpSize - 1; j++) {
                nArray[i-1][j-1] = BSpline.bSplineBasis(degree, j, ti, knots);
            }
        }

        final RealMatrix d = MatrixUtils.createRealMatrix(dArray); // D
        final RealMatrix n = MatrixUtils.createRealMatrix(nArray); // N  
        RealMatrix cp = new LUDecomposition(n.transpose().multiply(n).multiply(d)).getSolver()
                .solve(n.transpose().multiply(d));
        
        return Stream.of(pHead)
                .appendAll(Stream.of(cp.getData())
                        .map(ds -> Point.of(Stream.ofAll(ds).toJavaArray(Double.class))))
                .append(pLast)
                .toArray();
    }
}
