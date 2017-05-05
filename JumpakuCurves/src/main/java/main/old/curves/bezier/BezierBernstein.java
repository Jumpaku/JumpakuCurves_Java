/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.bezier;

import javaslang.collection.Array;
import main.old.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public class BezierBernstein extends AbstractBezier {
    
    private final Array<Double> combinations;
    
    public BezierBernstein(Array<? extends Point> cp, Integer dimention) {
        super(cp, dimention);
        final Integer degree = cp.size() - 1;
        combinations = Bezier.combinations(degree);
    }
    
    @Override
    public final Point evaluate(Double t) {
        if(!getDomain().contains(t))
            throw new IllegalArgumentException("Parameter t out closed domain [0,1]");
        
        Array<Point> cps = getControlPoints();
        Integer degree = getDegree();

        if(!Double.isFinite(1.0/(1.0-t))){
            return cps.get(degree);
        }
        if(!Double.isFinite(1.0/t)){
            return cps.get(0);
        }
        
        Double ct = Math.pow(1-t, degree);
        Point result = Point.origin(cps.get(0).getDimention());
        
        for(int i = 0; i <= degree; ++i){
            result = Point.of(result.getVec().add(cps.get(i).getVec().scale(combinations.get(i)*ct)));
            ct *= (t / (1 - t));
        }
        
        return result;
    }
}
