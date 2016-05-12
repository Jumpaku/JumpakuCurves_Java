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
    
    private final Domain domain;
    
    public AbstractBezierCurve(List<V> cp) {
        if(cp.isEmpty()){
            throw new IllegalArgumentException("The number of control points must be greater than 0 but the number of given control points was 0.");
        }
        controlPoints = new LinkedList<>();
        domain = new ClosedDomain(0.0, 1.0);
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
    public List<V> getControlPoints() {
        return Collections.unmodifiableList(controlPoints);
    }
    
    @Override
    public Integer getDegree(){
        return controlPoints.size() - 1;
    }

    @Override
    public BezierCurve<V> elevate(){
        return elevate(this);
    }
    
    public static <V extends Vector> BezierCurve<V> elevate(BezierCurve<V> original){
        List<V> cp = BezierCurve.createElevatedControlPonts(original.getControlPoints());
        return new BezierCurve<V>(){            
            @Override
            public Domain getDomain() {
                return original.getDomain();
            }

            @Override
            public List<V> getControlPoints() {
                return cp;
            }

            @Override
            public Integer getDegree() {
                return original.getDegree() + 1;
            }

            @Override
            public BezierCurve<V> elevate() {
                return AbstractBezierCurve.elevate(this);
            }

            @Override
            public V evaluate(Double t) {
                return original.evaluate(t);
            }
        };
    }
}
