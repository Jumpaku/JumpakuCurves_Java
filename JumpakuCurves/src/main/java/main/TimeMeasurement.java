/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.random.MersenneTwister;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;
import org.jumpaku.curves.utils.GeomUtils;

/**
 *
 * @author ito tomohiko
 */
public class TimeMeasurement {
    static final int n = 2;
    static Long measureAndCheck(BiFunction<Double, List<Vector2D>, Vector2D> bezier){
        Double t = 0.5;
        MersenneTwister random = new MersenneTwister(13024019);        
        List<Vector2D> cps = Collections.unmodifiableList(java.util.stream.Stream.generate(
                ()->new Vector2D(random.nextDouble(), random.nextDouble())).limit(n + 1).collect(Collectors.toList()));
        long s = System.nanoTime();
        System.out.println(bezier.apply(t, cps));
        return System.nanoTime() - s;
    }
    public static void main(String[] args) {
        Long d = 0L;
        for(int i = 0; i < 1000; ++i){
            d += measureAndCheck((t, _cps)->{
                List<Vector2D> cps = new ArrayList<>(_cps);
                for(int degree = n-1; degree > 0; --degree){
                    for(int j = 0; j < degree; ++j){
                        cps.set(j, GeomUtils.internallyDivide(t, cps.get(j), cps.get(j+1)));
                    }
                }
                return cps.get(0);
            });
        }
        System.out.println(d/1000 + "ns");
        
        Long d2 = 0L;
        double[] bs = IntStream.rangeClosed(0, n).mapToDouble(i -> binomialCoefficientDouble(n, i)).toArray();
        for(int i = 0; i < 1000; ++i){
            d2 += measureAndCheck((t, cps)->{
                Double ct = Math.pow(1-t, n);
                Vector2D result = Vector2D.ZERO;
                Vector2D error = Vector2D.ZERO;
                for(int j = 0; j <= n; ++j){
                    Vector2D cp = cps.get(j).scalarMultiply(bs[j]*ct).subtract(error);
                    Vector2D tmp = result.add(cp);
                    error = tmp.subtract(result).subtract(cp);
                    result = tmp;
                    ct *= (t / (1 - t));
                }
                return result;
            });
        }
        System.out.println(d2/1000 + "ns");

        Long d3 = 0L;
        for(int i = 0; i < 1000; ++i){
            d3 += measureAndCheck((t, cps)->{
                Vector2D result = Vector2D.ZERO;
                for(int j = 0; j <= n; ++j){
                    result = result.add(cps.get(j).scalarMultiply(bs[j]* Math.pow(t, j)*Math.pow(1-t, n-j)));
                }
                return result;
            });
        }
        System.out.println(d3/1000 + "ns");
    }
}
