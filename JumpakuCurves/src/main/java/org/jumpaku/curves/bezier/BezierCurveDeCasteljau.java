/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 */
public class BezierCurveDeCasteljau extends AbstractBezierCurve {
    public BezierCurveDeCasteljau(Array<Point> cp, Integer dimention) {
        super(cp, dimention);
    }
    
    @Override
    final public Point evaluate(Double t) {
        if(!getDomain().isIn(t))
            throw new IllegalArgumentException("t must be in domain [0,1], but t = " + t);
        
        Object[] cp = getControlPoints().toJavaArray();
        for(int n = getDegree(); n > 0; --n){
            for(int i = 0; i < n; ++i){
                cp[i] = Point.create(((Point)cp[i]).getVec().scale(1-t).add(t, ((Point)cp[i+1]).getVec()));
            }
        }
        return (Point)cp[0];
    }
}
