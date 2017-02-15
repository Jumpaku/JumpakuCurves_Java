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

    static Boolean equals(Point a, Point b, Double eps){
        return Vector.equals(a.getVector(), b.getVector(), eps);
    }

    static Point of(Vector v){
        return () -> v;
    }    
    
    static Point of(Double x, Double y, Double z){
        return Point.of(Vector.of(x, y, z));
    }
    
    static Point of(Double x, Double y){
        return Point.of(x, y, 0.0);
    }
    
    static Point of(Double x){
        return Point.of(x, 0.0);
    }
    
    Vector getVector();
    
    default Point translate(Vector v){
        return Point.of(getVector().add(v));
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
    default Vector diff(Point p){
        return getVector().sub(p.getVector());
    }
    
    default Double dist(Point p){
        return diff(p).length();
    }
    
    default Double distSquare(Point p){
        return diff(p).square();
    }
    
    /**
     * 
     * @param t
     * @param p
     * @return this+t*(p-this) = (1-t)*this + t*p 
     */
    default Point divide(Double t, Point p) {
        return Point.of(Vector .add(1-t, getVector(), t, p.getVector()));
    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @return area of a triangle (this, p1, p2) 
     */
    default Double area(Point p1, Point p2){
        return diff(p1).cross(diff(p2)).length()/2.0;
    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return volume of a Tetrahedron (this, p1, p2, p3)
     */
    default Double volume(Point p1, Point p2, Point p3){
        return diff(p1).cross(diff(p2)).dot(diff(p3))/6.0;
    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @return (p1-this)x(p2-this)/|(p1-this)x(p2-this)|
     */
    default Vector normal(Point p1, Point p2){
        return p1.diff(this).cross(p2.diff(this)).normalize();
    }
}
