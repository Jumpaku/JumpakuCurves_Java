/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.bezier;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import main.old.curves.utils.FuncUtils;
import main.old.curves.vector.Point;
import main.old.curves.vector.Vec;

/**
 *
 * @author ito Jumpaku
 */
public class RationalBezierAccurate extends AbstractRationalBezier {

    public RationalBezierAccurate(Array<? extends Point> cps, Array<Double> ws, Integer dimention) {
        super(cps, ws, dimention);
    }
    
    @Override
    public Point evaluate(Double t) {
        if(!getDomain().contains(t))
            throw new IllegalArgumentException("Parameter t out closed domain [0,1]");
        
        return deCasteljau(t, Array.ofAll(getControlPoints().zip(getWeights()))).head()._1();
    }
    
    static Array<Tuple2<? extends Point, Double>> deCasteljau(Double t, Array<Tuple2<? extends Point, Double>> cpws){
        if(cpws.size() <= 1){
            return cpws;
        }
        else{
            return deCasteljau(t, Array.ofAll(FuncUtils.zipWith(cpws, cpws.tail(), (pw0, pw1) -> {
                Double w0 = pw0._2();
                Double w1 = pw1._2();
                Double w = (1-t)*w0 + t*w1;
                Vec v0 = pw0._1().getVec();
                Vec v1 = pw1._1().getVec();
                return Tuple.of(Point.of(Vec.add((1-t)*w0/w, v0, t*w1/w, v1)), w);
            })));
        }
    }
}
