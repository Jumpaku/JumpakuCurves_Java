/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import java.util.Arrays;
import java.util.List;
import mpc.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author ito
 * @param <V>
 */
public class BezierCurve2DByDeCasteljau<V extends Vector> extends AbstractBezierCurve<V> {
    public BezierCurve2DByDeCasteljau(List<V> cp) {
        super(cp);
    }
    
    public BezierCurve2DByDeCasteljau(V... cp) {
        this(Arrays.asList(cp));
    }
    
    @Override
    public V evaluate(Double t) {
        if(!getDomain().isIn(t)){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        Object[] buffer = getControlPoints().toArray();
        for(int n = getDegree(); n > 0; --n){
            for(int i = 0; i < n; ++i){
                buffer[i] = GeomUtils.internallyDivide(t, (V)buffer[i], (V)buffer[i+1]);
            }
        }
        return (V)buffer[0];
    }

    @Override
    protected V evaluate(List<V> controlPoints, Double t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
