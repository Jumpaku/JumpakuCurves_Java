/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.spline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import main.old.curves.domain.ClosedOpen;
import main.old.curves.domain.Interval;
import main.old.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public final class BSplineDeBoor extends AbstractBSpline {
    
    private final Interval domain;
    public BSplineDeBoor(Array<Double> knots, Array<Point> controlPoints, Integer degree, Integer dimemtion) {
        super(knots, controlPoints, degree, dimemtion);
        domain = new ClosedOpen(super.getDomain().getFrom(), super.getDomain().getTo());
    }

    @Override
    public Interval getDomain(){
        return domain;
    }
    
    @Override
    public Point evaluate(Double t) {
        if(!getDomain().contains(t))
            throw new IllegalArgumentException("t is out closed domain, t = " + t);
                
        Integer l = Stream.ofAll(getKnots()).lastIndexWhere(knot -> knot <= t);
        
        Point[] result = new Point[getControlPoints().size()];
        for(int i = 0; i < getControlPoints().size(); ++i){
            result[i] = getControlPoints().get(i);
        }
        
        Integer n = getDegree();
        Array<Double> knots = getKnots();
        
        for(int k = 1; k <= n; ++k){
            for(int i = l; i >= l-n+k; --i){
                Double aki = (t - knots.get(i)) / (knots.get(i+n+1-k) - knots.get(i));
                Point cp = Point.of(result[i-1].getVec().scale(1.0-aki).add(result[i].getVec().scale(aki)));
                result[i] = cp;
            }
        }
        
        return result[l];
    }
}
