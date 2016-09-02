/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import java.util.function.BiFunction;
import javaslang.collection.Array;
import javaslang.collection.Traversable;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.jumpaku.curves.utils.FuncUtils;
import org.jumpaku.curves.vector.Point;

/**
 *
 * @author ito tomohiko
 */
public class BezierDeCasteljau extends AbstractBezier{
    public BezierDeCasteljau(Array<? extends Point> cp, Integer dimention) {
        super(cp, dimention);
    }
    
    @Override
    public final Point evaluate(Double t) {
        if(!getDomain().contains(t))
            throw new IllegalArgumentException("Parameter t out of domain [0,1]");
        
        Array<Point> cps = getControlPoints();
        Integer degree = getDegree();

        
        
        return Point.origin(0);
    }
    
    public static Array<? extends Point> decasteljau(Double t, Array<? extends Point> ps){
        if(ps.size() <= 1){
            return ps;
        }
        else{
            return decasteljau(t, Array.ofAll(FuncUtils.zipWith(ps, ps.tail(), (p0, p1) -> p0.divide(t, p1))));
        }
    }
}
