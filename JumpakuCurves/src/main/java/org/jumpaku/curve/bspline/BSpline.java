/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bspline;

/**
 *
 * @author Jumpaku
 */
public final class BSpline{}/* implements FuzzyCurve, Differentiable, Reversible<BSpline>{
    public static Double bSplineBasis(Integer n, Integer j, Double t, Array<Double> knots){
        if(n == 0)
            return (knots.get(j).compareTo(t) <= 0 && knots.get(j+1).compareTo(t) > 0) ? 1.0 : 0.0;


        Double left = bSplineBasisHelper(t, knots.get(j), knots.get(j+n), knots.get(j));
        if(left.compareTo(0.0) != 0){
            left = left * bSplineBasis(n-1, j, t, knots);
        }
        Double right = bSplineBasisHelper(knots.get(j+n+1), t, knots.get(j+n+1), knots.get(j+1));
        if(right.compareTo(0.0) != 0){
            right = right * bSplineBasis(n-1, j+1, t, knots);
        }
        return left + right;
    }

    private static Double bSplineBasisHelper(Double a, Double b, Double c, Double d){
        return Double.isFinite((a-b)/(c-d)) ? (a-b)/(c-d) : 0.0;
    }

    private final Interval domain;

    private final Integer degree;

    private final Array<Point> controlPoints;

    private final Array<Double> knots;

    public BSpline(Interval domain, Integer degree, Array<Point> controlPoints, Array<Double> knots) {
        if(knots.exists(k -> k == null))
            throw new IllegalArgumentException("knots contain null");

        if(controlPoints.exists(cp -> cp == null))
            throw new IllegalArgumentException("control points contain null");

        for(int i = 0; i < knots.size()-1; ++i){
            if(knots.get(i) > knots.get(i+1))
                throw new IllegalArgumentException("knots must be in ascending order, but knot[" + i +  "] > knot[" + (i+1)+ "]");
        }

        if(controlPoints.isEmpty())
            throw new IllegalArgumentException("control points must be not empty");

        if(degree < 0)
            throw new IllegalArgumentException("degree must be positive or 0");

        if(controlPoints.size() != knots.size() - degree - 1)
            throw new IllegalArgumentException("control points and knots are wrong");

        this.domain = domain;
        this.degree = degree;
        this.controlPoints = controlPoints;
        this.knots = knots;
    }

    @Override
    public Interval getDomain(){
        return domain;
    }
    
    @Override
    public Point evaluate(Double t){
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t is out of domain, t = " + t);

        Integer l = getKnots().lastIndexWhere(knot -> knot <= t);

        Point[] result = new Point[getControlPoints().size()];
        for(int i = 0; i < getControlPoints().size(); ++i){
            result[i] = getControlPoints().get(i);
        }

        Integer n = getDegree();
        Array<Double> knots = getKnots();

        for(int k = 1; k <= n; ++k){
            for(int i = l; i >= l-n+k; --i){
                Double aki = (t - knots.get(i)) / (knots.get(i+n+1-k) - knots.get(i));
                result[i] = result[i-1].divide(aki, result[i]);
            }
        }

        return result[l];
    }

    @Override
    public Derivative differentiate(){

    }

    @Override
    public BSpline restrict(Interval i){

    }

    @Override
    public BSpline reverse(){

    }

    @Override
    public String toString(){

    }

    public Array<? extends Point> getControlPoints(){
        return controlPoints;
    }

    public Array<Double> getKnots(){
        return knots;
    }
    
    public Integer getDegree(){
        return degree;
    }
    
    public BSpline insertKnot(Double t){

    }
    
    public Array<? extends Bezier> toBeziers(){

    }
    
    public Tuple2<? extends Bezier, ? extends Bezier> subdivide(Double t){

    }
}*/
