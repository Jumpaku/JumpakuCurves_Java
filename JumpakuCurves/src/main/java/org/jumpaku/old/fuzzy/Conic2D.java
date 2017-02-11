/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.fuzzy;

import org.apache.commons.math3.util.Precision;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Point2D;
import org.jumpaku.old.curves.vector.Vec2;

/**
 *
 * @author Jumpaku
 */
public class Conic2D extends Point2D implements Membership<Conic2D, Point2D>, FuzzyPoint<Double>{
    
    public static Conic2D crisp(Point2D p){
        return new Conic2D(p, 0.0);
    }
    
    public static Conic2D crisp(Double x, Double y){
        return new Conic2D(x, y, 0.0);
    }
    
    public static Conic2D crisp(Vec2 v){
        return new Conic2D(v, 0.0);
    }
    
    public static Boolean equals(Conic2D p1, Conic2D p2, Double eps){
        return Point.equals(p1, p2, eps) && Precision.equals(p1.getFuzzyness(), p2.getFuzzyness(), eps);
    }
    
    public static Boolean equals(Conic2D p1, Conic2D p2, Integer ulp){
        return Point.equals(p1, p2, ulp) && Precision.equals(p1.getFuzzyness(), p2.getFuzzyness(), ulp);
    }
    
    private final Double radius;

    public Conic2D(Point p, Double radius) {
        super(p);
        if(radius.compareTo(0.0) < 0.0)
            throw new IllegalArgumentException("must be radius >= 0.0");
        
        this.radius = radius;
    }

    public Conic2D(Double x, Double y, Double radius) {
        this(new Point2D(x, y), radius);
    }

    public Conic2D(Vec2 vector, Double radius) {
        this(new Point2D(vector), radius);
    }

    @Override
    public Double getFuzzyness() {
        return radius;
    }
    
    @Override
    public Grade mu(Point2D p) {
        double r = getFuzzyness();
        double d = distance(p);
        return Double.isInfinite(d/r) ? 
                Grade.of(Point.equals(this, p, 1.0e-10)) : Grade.of(Math.max(0.0, 1.0 - d/r));
    }

    @Override
    public Grade possibility(Conic2D p) {
        double ra = getFuzzyness();
        double rb = p.getFuzzyness();
        double d = distance(p);
        return Double.isInfinite(d/(ra + rb)) ? 
                Grade.of(Point.equals(this, p, 1.0e-10)) : Grade.of(Math.max(0.0, 1.0 - d/(ra + rb)));
    }

    @Override
    public Grade necessity(Conic2D p) {
        double ra = getFuzzyness();
        double rb = p.getFuzzyness();
        double d = distance(p);
        return Double.isInfinite(d/(ra + rb)) ? Grade.of(Point.equals(this, p, 1.0e-10)) : 
               d < ra                         ? Grade.of(Math.min((ra - d)/(ra + rb), (ra + d)/(ra + rb))) :
                                                Grade.falseValue();
    }
}
