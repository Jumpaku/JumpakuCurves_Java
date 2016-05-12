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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import mpc.jumpaku.curves.domain.ClosedDomain;
import mpc.jumpaku.curves.domain.Domain;
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
        return elevate(controlPoints, this::evaluate, domain);
    }
    
    private static class DividedBezierCurve<V extends Vector> extends AbstractBezierCurve<V> {
        private final BiFunction<List<V>, Double, V> evaluator;
        public DividedBezierCurve(BiFunction<List<V>, Double, V> evaluator, List<V> cp){
            super(cp);
            this.evaluator = evaluator;
        }
        @Override
        protected V evaluate(List<V> controlPoints, Double t) {
            return evaluator.apply(controlPoints, t);
        }        
    } 
    
    @Override
    public final List<BezierCurve<V>> divide(Double t){
        return Collections.unmodifiableList(
                BezierCurve.createDividedControlPoints(this, t).stream()
                        .map(cp -> new DividedBezierCurve<>(this::evaluate, cp))
                        .collect(Collectors.toList()));
    }
    
    protected abstract V evaluate(List<V> controlPoints, Double t);
    
    @Override
    public V evaluate(Double t){
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("The parameter t must be in domain [0,1], but t = " + t);

        return evaluate(getControlPoints(), t);
    }
    
    private static <V extends Vector> BezierCurve<V> elevate(List<V> originalControlPoints, BiFunction<List<V>, Double, V> evaluator, Domain domain){
        final List<V> elevatedControlPoints = BezierCurve.createElevatedControlPonts(originalControlPoints);
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
                return elevatedControlPoints.size();
            }

            @Override
            public BezierCurve<V> elevate() {
                return AbstractBezierCurve.elevate(getControlPoints(), evaluator, domain);
            }
            
            @Override
            public V evaluate(Double t) {
                return evaluator.apply(originalControlPoints, t);
            }

            @Override
            public List<BezierCurve<V>> divide(Double t) {
                return BezierCurve.createDividedControlPoints(this, t).stream()
                                .map(cp -> new DividedBezierCurve<V>(evaluator, cp))
                                .collect(Collectors.toList());
            }
        };
    }
}
