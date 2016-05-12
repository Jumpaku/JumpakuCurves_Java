/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import fj.data.Stream;
import java.util.Collections;
import java.util.LinkedList;
import mpc.jumpaku.curves.domain.Domain;
import java.util.List;
import mpc.jumpaku.curves.Curve;
import mpc.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author ito
 * @param <V>
 */
public abstract interface BezierCurve<V extends Vector> extends Curve<V>{

    Domain getDomain();
    
    List<V> getControlPoints();
    
    Integer getDegree();
    
    BezierCurve<V> elevate();
    
    List<BezierCurve<V>> divide(Double t);
    
    public static <V extends Vector> List<V> createElevatedControlPonts(List<V> _cp){
        Integer n = _cp.size() - 1;
        Stream<V> result = Stream.single(_cp.get(0));
        
        Stream<V> cp = Stream.iterableStream(_cp).drop(1);
        Stream<V> tmp = cp.zip(cp.drop(1))
                .zipWith(Stream.range(1),
                        (p, i) -> (V)GeomUtils.internallyDivide(i/(double)(n+1), p._1(), p._2()));
        result.append(tmp);
        result.append(Stream.single(_cp.get(n)));
        
        return Collections.unmodifiableList(result.toJavaList());
    }
    
    public static <V extends Vector> V deCasteljau(Integer n, Integer m, Object[] cp, Double t){
        return n == 0 ? (V)cp[m] : (V) deCasteljau(n-1, m, cp, t).scalarMultiply(1-t).add(deCasteljau(n-1, m+1, cp, t).scalarMultiply(t));
    }
    public static <V extends Vector> V deCasteljau(Integer n, Integer m, List<V> cp, Double t){
        return deCasteljau(n, m, cp.toArray(), t);
    }
    
    public static <V extends Vector> List<List<V>> createDividedControlPoints(BezierCurve<V> original, Double t){
        if(!original.getDomain().isIn(t))
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        
        List<List<V>> result = new LinkedList<>();
        List<V> cp = original.getControlPoints();
        Integer n = original.getDegree();
        List<V> cp1 = new LinkedList<>();
        List<V> cp2 = new LinkedList<>();
        for(int i = 0; i < n+1; ++i){
            cp1.add(deCasteljau(i, 0, cp, t));
            cp2.add(deCasteljau(n-i, 0, cp, t));
        }
        result.add(Collections.unmodifiableList(cp1));
        result.add(Collections.unmodifiableList(cp2));
        
        return Collections.unmodifiableList(Collections.unmodifiableList(result));
    }
}
