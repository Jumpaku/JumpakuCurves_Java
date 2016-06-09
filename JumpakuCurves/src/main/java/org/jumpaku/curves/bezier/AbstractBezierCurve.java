/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the edJumpakur.
 */
package org.jumpaku.curves.bezier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Array;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Domain;
import org.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.transform.Transform;
import org.jumpaku.curves.utils.MathUtils;
import org.apache.commons.math3.geometry.Space;

/**
 * <p>Bezier曲線の基底クラス Basic class of Bezier Curve.</p>
 * <p>
 * {@link AbstractBezierCurve#evaluate(java.lang.Double) }をオーバーライドしてください.</p>
 * <p>
 * Override {@link AbstractBezierCurve#evaluate(java.lang.Double) }.</p>
 * @author Jumpaku
 * @param <S> 座標空間の種類  Type of the space. 
 * @param <V> {@link AbstractBezierCurve#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link AbstractBezierCurve#evaluate(java.lang.Double)}.
 */
public abstract class AbstractBezierCurve<S extends Space, V extends Vector<S>> implements BezierCurve<S, V>{
    
    private final Array<V> controlPoints;
    
    private static final Closed DOMAIN = new Closed(0.0, 1.0);
    
    public AbstractBezierCurve(Array<V> cp) {
        if(cp.isEmpty())
            throw new IllegalArgumentException("Control points is empty.");
        
        if(cp.exists(p -> p == null))
            throw new IllegalArgumentException("Control points contains null.");

        controlPoints = cp;
    }
    
    public AbstractBezierCurve(V... cp) {
        this(Array.of(cp));
    }
    
    private static <S extends Space, V extends Vector<S>> Array<Array<V>> createDividedControlPoints(Object[] cp, Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("The parameter t isout of domain [0,1], t = " + t);
        
        LinkedList<V> first = new LinkedList<>();
        LinkedList<V> second = new LinkedList<>();
        int n = cp.length - 1;
        first.addLast((V)cp[0]);
        second.addFirst((V)cp[n]);
        while(n > 0){
            for(int i = 0; i < n; ++i){
                cp[i] = GeomUtils.internallyDivide(t, (Vector<S>)cp[i], (Vector<S>)cp[i+1]);
            }
            first.addLast((V)cp[0]);
            second.addFirst((V)cp[--n]);
        }
        
        return Array.of(Array.ofAll(first), Array.ofAll(second));
    }
    
    private static <S extends Space, V extends Vector<S>> Array<V> createElevatedControlPonts(Array<? extends V> controlPoints){
        Integer n = controlPoints.size() - 1;
        List<V> result = new LinkedList<>();
        result.add(controlPoints.get(0));        
        for(int i = 1; i <= n; ++i){
            Double a = i/(double)(n+1);
            Double b = 1-a;
            result.add(GeomUtils.scalingAdd(b, controlPoints.get(i), a, controlPoints.get(i-1)));
        }
        result.add(controlPoints.get(n));
        return Array.ofAll(result);
    }
    
    private static <S extends Space, V extends Vector<S>> Array<V> createReducedControlPonts(Array<? extends V> originalcps){
        Integer n = originalcps.size() - 1;
        Integer m = n + 1;
        if(m < 2)
            throw new IllegalArgumentException("degree is too small");
            
        Object[] cps = new Object[n];
        if(m == 2){
            cps[0] = originalcps.get(0).add(originalcps.get(1)).scalarMultiply(0.5);
        }
        else if(m%2==0){
            Integer r = (m-2)/2;
            cps[0] = originalcps.get(0);
            for(int i = 1; i <= r-1; ++i){
                Double a = i/(m-1.0);
                cps[i] = originalcps.get(i).add(-a, (Vector<S>)cps[i-1]).scalarMultiply(1.0/(1.0-a));
            }
            cps[n-1] = originalcps.get(n);
            for(int i = m-3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = originalcps.get(i+1).add(-1.0+a, (Vector<S>)cps[i+1]).scalarMultiply(1.0/a);
            }
            Double al = r/(m-1.0);
            Double ar = (r+1)/(m-1.0);
            Vector<S> left = originalcps.get(r).add(-al, (Vector<S>)cps[r-1]).scalarMultiply(1.0/(1.0-al));
            Vector<S> right = originalcps.get(r+1).add(-1.0+ar, (Vector<S>)cps[r+1]).scalarMultiply(1.0/ar);
            cps[r] = left.add(right).scalarMultiply(0.5);
        }
        else{
            cps[0] = originalcps.get(0);
            Integer r = (m-3)/2;
            for(int i = 1; i <= r; ++i){
                Double a = i/(m-1.0);
                cps[i] = originalcps.get(i).add(-a, (Vector<S>)cps[i-1]).scalarMultiply(1.0/(1.0-a));
            }
            cps[n-1] = originalcps.get(n);
            for(int i = m - 3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = originalcps.get(i+1).add(-1.0+a, (Vector<S>)cps[i+1]).scalarMultiply(1.0/a);
            }
        }
        
        return Array.of(cps).map(o -> (V)o);//.collect(Collectors.toList());
    }
    
    private static class DegreeElevated<S extends Space, V extends Vector<S>> extends AbstractBezierCurve<S, V>{
        private final Integer elevated;
        private final AbstractBezierCurve<S, V> reduced;
        public DegreeElevated(Integer elevated, Array<V> cps, AbstractBezierCurve<S, V> reduced) {
            super(cps);
            this.elevated = elevated;
            this.reduced = reduced;
        }        
        
        @Override
        public V evaluate(Double t) {
            return reduced.evaluate(t);
        }
        
        @Override
        public final BezierCurve<S, V> elevate(){
            return new DegreeElevated<>(elevated+1, createElevatedControlPonts(getControlPoints()), this);
        }
        
        @Override
        public final BezierCurve<S, V> reduce(){
            return elevated >= 1 ? reduced : BezierCurve.create(createReducedControlPonts(getControlPoints()));
        }
    }
    
    private static <S extends Space, V extends Vector<S>> Array<BezierCurve<S, V>> divide(Double t, Array<V> controlPoints, Function<Double, V> evaluator){
        Array<Array<V>> cps = createDividedControlPoints(controlPoints.toJavaArray(), t);
        return Array.of(new AbstractBezierCurve<S, V>(cps.get(0)){
            @Override public V evaluate(Double s) {
                return evaluator.apply(s*t);
            }            
        },
        new AbstractBezierCurve<S, V>(cps.get(1)){
            @Override public V evaluate(Double s) {
                return evaluator.apply(t + s*(1-t));
            }
        });
        //return Collections.unmodifiableList(result);
    }
    
    private static <S extends Space, V extends Vector<S>> BezierCurve<S, V> transform(Transform<S, V> transform, Array<V> controlPoints, Function<Double, V> evaluator){
        Array<V> transformedControlPoints = controlPoints
                .map(cp -> transform.apply(cp));
                //collect(Collectors.toList());
        return new AbstractBezierCurve<S, V>(transformedControlPoints) {
            @Override
            public V evaluate(Double t) {
                return transform.apply(evaluator.apply(t));
            }
        };
    }
    
    private static <S extends Space, V extends Vector<S>> BezierCurve<S, V> reverse(Array<V> controlPoints, Function<Double, V> evaluator){
        return new AbstractBezierCurve<S, V>(controlPoints.reverse()) {
            @Override
            public V evaluate(Double t) {
                return evaluator.apply(1-t);
            }
        };
    }
    
    @Override
    public final Closed getDomain() {
        return DOMAIN;
    }
    
    @Override
    public final Array<V> getControlPoints() {
        return controlPoints;
    }
    
    @Override
    public final Integer getDegree(){
        return controlPoints.size() - 1;
    }

    @Override
    public BezierCurve<S, V> elevate(){
        return new DegreeElevated<>(1, createElevatedControlPonts(getControlPoints()), this);
    }
    
    @Override
    public BezierCurve<S, V> reduce(){
        return BezierCurve.create(createReducedControlPonts(getControlPoints()));
    }
    
    @Override
    public final Array<BezierCurve<S, V>> divide(Double t){
        return divide(t, getControlPoints(), this::evaluate);
    }
    
    @Override
    public final BezierCurve<S, V> transform(Transform<S, V> transform){
        return transform(transform, getControlPoints(), this::evaluate);
    }
    
    @Override
    public final BezierCurve<S, V> reverse(){
        return reverse(getControlPoints(), this::evaluate);
    }
    
    @Override
    public final V computeTangent(Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("t must be in [0, 1]");
        Integer n = getDegree();
        Array<V> cp = getControlPoints();
        Object[] combinations = MathUtils.combinations(n-1).toArray();
        Vector<S> result = (Vector<S>)cp.get(0).getZero();
        for(int i = 0; i < n; ++i){
            Vector<S> delta = (cp.get(i+1).subtract(cp.get(i))).scalarMultiply((Double)combinations[i] * Math.pow(t, i) * Math.pow(1-t, n-1-i));
            result = result.add(delta);
        }
        return (V)result.scalarMultiply(n);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Bezier曲線の評価処理を実装してください.</p>
     * <p>
     * Implement evaluation execution of Bezier Curve.</p>
     * @param t [0,1]に含まれるパラメータ parameter in [0,1]
     * @return 評価点 evaluated point
     */
    @Override
    public abstract V evaluate(Double t);
}
