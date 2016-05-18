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
import java.util.stream.Collectors;
import mpc.jumpaku.curves.domain.ClosedDomain;
import mpc.jumpaku.curves.domain.Domain;
import mpc.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Vector;
import mpc.jumpaku.curves.transform.Transform;
import mpc.jumpaku.curves.utils.MathUtils;

/**
 *
 * @author ito
 * @param <V>
 */
public abstract class AbstractBezierCurve<V extends Vector> implements BezierCurve<V>{
    
    private final List<V> controlPoints;
    
    private static final Domain DOMAIN = new ClosedDomain(0.0, 1.0);
    
    public AbstractBezierCurve(List<V> cp) {
        if(cp.isEmpty())
            throw new IllegalArgumentException("The number of control points must be greater than 0 but no control points was given.");

        controlPoints = new LinkedList<>();
        cp.forEach(v -> controlPoints.add(v));
    }
    
    public AbstractBezierCurve(V v, V... cp) {
        this(Arrays.asList(cp));
    }
    
    public static <V extends Vector> List<List<V>> createDividedControlPoints(Object[] cp, Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("The parameter t must be in domain [0,1], but t = " + t);
        
        List<List<V>> result = new LinkedList<>();
        LinkedList<V> first = new LinkedList<>();
        LinkedList<V> second = new LinkedList<>();
        int n = cp.length - 1;
        first.addLast((V)cp[0]);
        second.addFirst((V)cp[n]);
        while(n > 0){
            for(int i = 0; i < n; ++i){
                cp[i] = GeomUtils.internallyDivide(t, (V)cp[i], (V)cp[i+1]);
            }
            first.addLast((V)cp[0]);
            second.addFirst((V)cp[--n]);
        }
        result.add(Collections.unmodifiableList(first));
        result.add(Collections.unmodifiableList(second));
        
        return Collections.unmodifiableList(Collections.unmodifiableList(result));
    }
    
    private static <V extends Vector> List<V> createElevatedControlPonts(List<V> controlPoints){
        Integer n = controlPoints.size() - 1;
        Object[] cp = controlPoints.toArray();
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
    
    private static <V extends Vector> BezierCurve<V> elevate(List<V> originalControlPoints, Function<Double, V> evaluator){
        final List<V> elevatedControlPoints = createElevatedControlPonts(originalControlPoints);
        return new AbstractBezierCurve<V>(elevatedControlPoints){
            @Override
            public V evaluate(Double t) {
                return evaluator.apply(t);
            }
        };
    }
    
    private static <V extends Vector> List<BezierCurve<V>> divide(Double t, List<V> controlPoints, Function<Double, V> evaluator){
        List<List<V>> cps = createDividedControlPoints(controlPoints.toArray(), t);
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
    
    private static <V extends Vector> BezierCurve<V> transform(Transform<V> transform, List<V> controlPoints, Function<Double, V> evaluator){
        List<V> transformedControlPoints = controlPoints.stream()
                .map(cp -> transform.apply(cp))
                .collect(Collectors.toList());
        return new AbstractBezierCurve<V>(transformedControlPoints) {
            @Override
            public V evaluate(Double t) {
                return transform.apply(evaluator.apply(t));
            }
        };
    }
    
    private static <V extends Vector> BezierCurve<V> reverse(List<V> controlPoints, Function<Double, V> evaluator){
        return new AbstractBezierCurve<V>(javaslang.collection.List.ofAll(controlPoints).reverse().toJavaList()) {
            @Override
            public V evaluate(Double t) {
                return evaluator.apply(1-t);
            }
        };
    }
    
    @Override
    public final Domain getDomain() {
        return DOMAIN;
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
    public BezierCurve<V> transform(Transform<V> transform){
        return transform(transform, getControlPoints(), this::evaluate);
    }
    
    @Override
    public final BezierCurve<V> reverse(){
        return reverse(getControlPoints(), this::evaluate);
    }
    
    @Override
    public abstract V evaluate(Double t);
}
