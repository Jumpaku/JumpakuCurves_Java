/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import mpc.jumpaku.curves.domain.ClosedDomain;
import mpc.jumpaku.curves.domain.Domain;
import mpc.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author ito
 * @param <V>
 */
public abstract class AbstractBezierCurve<V extends Vector> implements BezierCurve<V>{
    
    private final List<V> controlPoints;
    
    private static final Domain domain = new ClosedDomain(0.0, 1.0);
    
    public AbstractBezierCurve(List<V> cp) {
        if(cp.isEmpty()){
            throw new IllegalArgumentException("The number of control points must be greater than 0 but the number of given control points was 0.");
        }
        controlPoints = new LinkedList<>();
        cp.forEach(v -> controlPoints.add(v));
    }
    
    public AbstractBezierCurve(V... cp) {
        this(Arrays.asList(cp));
    }

    public static <V extends Vector> V deCasteljau(Integer n, Integer m, Object[] cp, Double t){
        return (V) (n == 0 ?
                cp[m] : GeomUtils.scalingAdd(1-t, deCasteljau(n-1, m, cp, t), t, deCasteljau(n-1, m+1, cp, t)));
    }
    public static <V extends Vector> V deCasteljau(Integer n, Integer m, List<V> cp, Double t){
        return deCasteljau(n, m, cp.toArray(), t);
    }
    
    public static <V extends Vector> List<V> createElevatedControlPonts(List<V> _cp){
        Integer n = _cp.size() - 1;
        Object[] cp = _cp.toArray();
        List<V> result = new LinkedList<>();
        result.add((V)cp[0]);        
        for(int i = 1; i <= n; ++i){
            Double a = i/(double)(n+1);
            Double b = 1-a;
            result.add(GeomUtils.scalingAdd(b, (V)cp[i], a, (V)cp[i-1]));
        }
        result.add((V)cp[n]);
        return Collections.unmodifiableList(result);
    }
    
    public static <V extends Vector> List<List<V>> createDividedControlPoints(List<V> controlPoints, Double t){
        if(!domain.isIn(t))
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        
        List<List<V>> result = new LinkedList<>();
        List<V> cp = controlPoints;
        Integer n = controlPoints.size()-1;
        List<V> cp1 = new LinkedList<>();
        List<V> cp2 = new LinkedList<>();
        for(int i = 0; i < n+1; ++i){
            cp1.add(deCasteljau(i, 0, cp, t));
            cp2.add(deCasteljau(n-i, i, cp, t));
        }
        result.add(Collections.unmodifiableList(cp1));
        result.add(Collections.unmodifiableList(cp2));
        
        return Collections.unmodifiableList(Collections.unmodifiableList(result));
    }
    
    public static <V extends Vector> List<BezierCurve<V>> divide(Double t, List<V> controlPoints, Function<Double, V> evaluator){
        List<List<V>> cps = createDividedControlPoints(controlPoints, t);
        List<BezierCurve<V>> result = new LinkedList<>();
        result.add(new AbstractBezierCurve<V>(cps.get(0)){
            @Override public V evaluate(Double s) {
                return evaluator.apply(s*t);
            }            
        });
        result.add(new AbstractBezierCurve<V>(cps.get(1)){
            @Override public V evaluate(Double s) {
                return evaluator.apply(t + s*(1-t));
            }
        });
        return Collections.unmodifiableList(result);
    }
    
    @Override
    public final Domain getDomain() {
        return domain;
    }
    
    @Override
    public final List<V> getControlPoints() {
        return Collections.unmodifiableList(controlPoints);
    }
    
    @Override
    public final Integer getDegree(){
        return controlPoints.size() - 1;
    }

    @Override
    public final BezierCurve<V> elevate(){
        return elevate(getControlPoints(), this::evaluate);
    }
    
    @Override
    public final List<BezierCurve<V>> divide(Double t){
        return divide(t, getControlPoints(), this::evaluate);
    }
    
    @Override
    public abstract V evaluate(Double t);
    
    private static <V extends Vector> BezierCurve<V> elevate(
            List<V> originalControlPoints,
            Function<Double, V> evaluator){
        final List<V> elevatedControlPoints = createElevatedControlPonts(originalControlPoints);
        return new BezierCurve<V>(){            
            @Override
            public Domain getDomain() {
                return domain;
            }

            @Override
            public List<V> getControlPoints() {
                return elevatedControlPoints;
            }

            @Override
            public Integer getDegree() {
                return elevatedControlPoints.size() - 1;
            }

            @Override
            public BezierCurve<V> elevate() {
                return AbstractBezierCurve.elevate(getControlPoints(), evaluator);
            }
            
            @Override
            public V evaluate(Double t) {
                return evaluator.apply(t);
            }

            @Override
            public List<BezierCurve<V>> divide(Double t) {
                return AbstractBezierCurve.divide(t, elevatedControlPoints, evaluator);
            }
        };
    }
}
