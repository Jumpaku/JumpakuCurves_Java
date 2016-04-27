/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import fj.data.Stream;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public class BezierCurve2DByDeCasteljau extends BezierCurve2D{

    public BezierCurve2DByDeCasteljau(List<Vector2D> cp) {
        super(cp);
    }
    
    public BezierCurve2DByDeCasteljau(Vector2D... cp) {
        super(Arrays.asList(cp));
    }
    
    @Override
    public Vector2D evaluate(Double t) {
        if(!getDomain().isIn(t)){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        Stream<Vector2D> controlPoints = Stream.iterableStream(getControlPoints());
        while(controlPoints.length() > 1){
            controlPoints = controlPoints.zipWith(controlPoints.drop(1),
                        (p, n) -> p.scalarMultiply(1.0-t).add(n.scalarMultiply(t)));
        }
        
        return controlPoints.head();
    }
}
