/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import fj.data.Stream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import mpc.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public class BezierCurve2DByDeCasteljau extends AbstractBezierCurve<Vector2D> {

    Vector2D[] buffer;
    
    public BezierCurve2DByDeCasteljau(List<Vector2D> cp) {
        super(cp);
    }
    
    public BezierCurve2DByDeCasteljau(Vector2D... cp) {
        this(Arrays.asList(cp));
    }
    
    @Override
    public Vector2D evaluate(Double t) {
        if(!getDomain().isIn(t)){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        buffer = getControlPoints().toArray(new Vector2D[getDegree()+1]);
        for(int n = getDegree(); n > 0; --n){
            for(int i = 0; i < n; ++i){
                buffer[i] = GeomUtils.internallyDivide(t, buffer[i], buffer[i+1]);
            }
        }
        return buffer[0];
    }

    @Override
    public BezierCurve<Vector2D> elevate() {
        return new BezierCurve2DByDeCasteljau(BezierCurve.createElevatedControlPonts(getControlPoints()));
    }

    
}
