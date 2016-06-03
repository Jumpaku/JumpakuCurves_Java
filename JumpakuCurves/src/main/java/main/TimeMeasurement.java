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
    
    static MersenneTwister random = new MersenneTwister(13024019);        
    
    static List<Vector2D> cps = Collections.unmodifiableList(java.util.stream.Stream.generate(
                ()->new Vector2D(random.nextDouble(), random.nextDouble())).limit(n + 1).collect(Collectors.toList()));
    
    static Double t = 0.6;
    
    static Long measureAndCheck(BiFunction<Double, List<Vector2D>, Vector2D> bezier){
        long s = System.nanoTime();
        //System.out.println();
        bezier.apply(t, cps);
        return System.nanoTime() - s;
    }
    
    public static void main(String[] args) {
        List<Double> bs = IntStream.rangeClosed(0, n).mapToObj(i -> binomialCoefficientDouble(n, i)).collect(Collectors.toList());
        for(int i = 0; i < 10000; ++i){
            measureAndCheck((Double t1, List<Vector2D> u) -> Vector2D.NaN);
        }
        
        Long d2 = 0L;
        for(int i = 0; i < 10000; ++i){
            d2 += measureAndCheck((t, cps)->{
                Double ct = Math.pow(1-t, n);
                Vector2D result = Vector2D.ZERO;
                for(int j = 0; j <= n; ++j){
                    result = result.add(cps.get(j).scalarMultiply(bs.get(j)*ct));
                    ct *= (t / (1 - t));
                }
                return result;
            });
        }
        System.out.println(d2 + "ns : O(n)");
        
        Long d = 0L;
        for(int i = 0; i < 10000; ++i){
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
        System.out.println(d + "ns : decas");
        
        Long d3 = 0L;
        for(int i = 0; i < 10000; ++i){
            d3 += measureAndCheck((t, cps)->{
                Vector2D result = Vector2D.ZERO;
                for(int j = 0; j <= n; ++j){
                    result = result.add(cps.get(j).scalarMultiply(bs.get(j)* Math.pow(t, j)*Math.pow(1-t, n-j)));
                }
                return result;
            });
        }
        System.out.println(d3 + "ns : O(n^2)");   
    }
}
