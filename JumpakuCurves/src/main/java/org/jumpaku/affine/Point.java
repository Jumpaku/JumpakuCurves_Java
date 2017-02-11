/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

/**
 *
 * @author Jumaku
 */
public interface Point {
    
    static Point of(Vector v){
        return () -> v;
    }    
    
    static Point of(Double x, Double y, Double z){
        return of(Vector.of(x, y, z));
    }
    
    static Point twod(Double x, Double y){
        return of(x, y, 0.0);
    }
    
    static Point oned(Double x){
        return twod(x, 0.0);
    }

    static Point origin(){
        return oned(0.0);
    }
    
    Vector getVector();
    
    default Point translate(Vector v){
        return of(getVector().add(v));
    }
    
    default Double getX(){
        return getVector().getX();
    }
    
    default Double getY(){
        return getVector().getY();
    }
    
    default Double getZ(){
        return getVector().getZ();
    }        
    
    /**
     * 
     * @param p
     * @return this - p
     */
    default Vector difference(Point p){
        return getVector().sub(p.getVector());
    }
    
    default Double distance(Point p){
        return difference(p).length();
    }
    
    default Double distanceSquare(Point p){
        return difference(p).square();
    }
    
    /**
     * 
     * @param p
     * @param r
     * @return (1-r)*this + r*p 
     */
    default Point divide(Point p, Double r) {
        return of(Vector .add(1-r, getVector(), r, p.getVector()));
    }
    
    default Double area(Point p1, Point p2){
        return difference(p1).cross(difference(p2)).length()/2.0;
    }
        
    default Vector normal(Point p1, Point p2){
        return p1.difference(this).cross(p2.difference(this)).normalize();
    }
}
