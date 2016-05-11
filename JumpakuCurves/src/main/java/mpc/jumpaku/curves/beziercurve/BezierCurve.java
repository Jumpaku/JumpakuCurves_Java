/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import fj.data.Stream;
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
    
    public static <V extends Vector> List<V> createElevatedControlPonts(List<V> _cp){
        Integer n = _cp.size() - 1;
        Stream<V> result = Stream.single(_cp.get(0));
        
        Stream<V> cp = Stream.iterableStream(_cp).drop(1);
        Stream<V> tmp = cp.zip(cp.drop(1))
                .zipWith(Stream.range(1),
                        (p, i) -> (V)GeomUtils.internallyDivide(i/(double)(n+1), p._1(), p._2()));
        result.append(tmp);
        result.append(Stream.single(_cp.get(n)));
        
        return result.toJavaList();
    }
}
