/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import fj.data.Stream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import mpc.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public class BezierCurve2DByDeCasteljau extends BezierCurve2D{

    final Vector2D[] controlPoints;
    
    public BezierCurve2DByDeCasteljau(List<Vector2D> cp) {
        super(cp);
        controlPoints = cp.toArray(new Vector2D[cp.size()]);
    }
    
    public BezierCurve2DByDeCasteljau(Vector2D... cp) {
        this(Arrays.asList(cp));
    }
    
    @Override
    public Vector2D evaluate(Double t) {
        if(!getDomain().isIn(t)){
            throw new IllegalArgumentException("The parameter t is must be in domain [0,1], but t = " + t);
        }
        Vector2D[] cp = Arrays.copyOf(controlPoints, controlPoints.length);
        for(int n = controlPoints.length - 1; n > 0; --n){
            Vector2D[] tmp = cp;
            cp = new Vector2D[n];
            for(int i = 0; i < n; ++i){
                cp[i] = GeomUtils.internallyDivide(t, tmp[i], tmp[i+1]);
            }
        }
        return cp[0];
        /*Stream<Vector2D> controlPoints = Stream.iterableStream(getControlPoints());
            while(flg){//controlPoints.length() > 1){
                if(!flg)//(controlPoints.length() > 1))
                    System.out.println(controlPoints.length());// + ":" + tmplength);
                
                //synchronized(controlPoints){
                    Stream<Vector2D> tmp = controlPoints.drop(1);
                    Stream<Vector2D> nxt = controlPoints.zipWith(tmp,
                                (p, n) -> GeomUtils.internallyDivide(t, n, p));
                    controlPoints = nxt;
                //}
                flg = controlPoints.length() > 1;
                //tmplength = controlPoints.length();
            }
        //} 
        return controlPoints.head();*/
        
        //return decasteljau(controlPoints, t).head();
    }
    
    private static Stream<Vector2D> decasteljau(Stream<Vector2D> cp, Double t){
        if(cp.isEmpty())
            System.out.println();
        return cp.length() == 1 ?
                cp : decasteljau(cp.zipWith(cp.drop(1),
                        (p, n) -> GeomUtils.internallyDivide(t, n, p)), t);
    } 
}
