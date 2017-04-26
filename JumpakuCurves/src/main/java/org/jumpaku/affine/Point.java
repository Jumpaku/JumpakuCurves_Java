/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import javaslang.control.Option;
import org.apache.commons.math3.util.FastMath;
import org.jumpaku.fuzzy.Grade;
import org.jumpaku.fuzzy.Membership;
import org.jumpaku.json.Converter;

/**
 *
 * @author Jumaku
 */
public interface Point extends Membership<Point, Point.Crisp>, Dividable<Point> {
    
    static Fuzzy fuzzy(Crisp p, Double r){
        return new Fuzzy(p.toVector(), r);
    }

    static Fuzzy fuzzy(Double x, Double y, Double z, Double r){
        return fuzzy(crisp(x, y, z), r);
    }

    static Fuzzy fuzzy(Double x, Double y, Double r){
        return fuzzy(x, y, 0.0, r);
    } 
    
    static Fuzzy fuzzy(Double x, Double r){
        return fuzzy(x, 0.0, r);
    }

    static Fuzzy zero(Double r){
        return fuzzy(ZERO, r);
    }

    static Crisp crisp(Double x, Double y, Double z){
        return new Crisp(Vector.crisp(x, y, z));
    }
        
    static Crisp crisp(Double x, Double y){
        return crisp(x, y, 0.0);
    } 
    
    static Crisp crisp(Double x){
        return crisp(x, 0.0);
    }

    Crisp ZERO = crisp(0.0);

    Converter<Point> CONVERTER = new JsonPoint();

    static String toJson(Point p){
        return CONVERTER.toJson(p);
    }
    
    static Option<Point> fromJson(String json){
        return CONVERTER.fromJson(json);
    }
    
    Vector toVector();
    
    default Crisp toCrisp(){
        return new Crisp(toVector().toCrisp());
    }
    
    default Double getX(){
        return toVector().getX();
    }
    
    default Double getY(){
        return toVector().getY();
    }
    
    default Double getZ(){
        return toVector().getZ();
    }
    
    default Double getR(){
        return toVector().getR();
    }
    
    @Override default Grade membership(Crisp p){
        return toVector().membership(p.toVector());
    }

    @Override default Grade possibility(Point p){
        return toVector().possibility(p.toVector());
    }

    @Override default Grade necessity(Point p){
        return toVector().necessity(p.toVector());
    }
    
    /**
     * 
     * @param t
     * @param p
     * @return this+t*(p-this) = (1-t)*this + t*p 
     */
    @Override default Point divide(Double t, Point p){
        return new Fuzzy(toVector().scale(1-t).add(t, p.toVector()).toCrisp(),
                FastMath.abs(1-t)*getR()+FastMath.abs(t)*p.getR());
    }

    final class Fuzzy implements Point{

        private final Vector.Fuzzy vector;

        public Fuzzy(Vector.Crisp vector, Double r) {
            this.vector = new Vector.Fuzzy(vector, r);
        }
        
        @Override public Vector.Fuzzy toVector() {
            return vector;
        }
        
        @Override public Crisp toCrisp() {
            return new Crisp(vector.toCrisp());
        }

        @Override public String toString() {
            return toJson(this);
        }
    }
    
    final class Crisp implements Point{
        
        private final Vector.Crisp vector;

        public Crisp(Vector.Crisp vector) {
            this.vector = vector;
        }
        
        @Override public Vector.Crisp toVector() {
            return vector;
        }

        @Override public Crisp toCrisp() {
            return this;
        }

        @Override public String toString() {
            return toJson(this);
        }

        /**
         * 
         * @param v
         * @return this + v
         */
        public Crisp move(Vector.Crisp v){
            return new Crisp(toVector().add(v).toCrisp());
        }

        /**
         * 
         * @param p
         * @return this - p
         */
        public Vector.Crisp diff(Crisp p){
            return toVector().sub(p.toVector()).toCrisp();
        }

        public Double dist(Crisp p){
            return diff(p).length();
        }

        /**
         * distance between this point and line ab. 
         * @param a
         * @param b
         * @return 
         */
        public Double dist(Crisp a, Crisp b){
            Crisp p = this;
            Vector.Crisp ap = p.diff(a);
            Vector.Crisp ab = b.diff(a);
            Crisp h = a.move(ab.scale(ap.dot(ab)/ab.square()));
            return p.dist(h);
        }

        public Double distSquare(Crisp p){
            return diff(p).square();
        }

        /**
         * 
         * @param p1
         * @param p2
         * @return area of a triangle (this, p1, p2) 
         */
        public Double area(Crisp p1, Crisp p2){
            return diff(p1).cross(diff(p2)).length()/2.0;
        }

        /**
         * 
         * @param p1
         * @param p2
         * @param p3
         * @return volume of a Tetrahedron (this, p1, p2, p3)
         */
        public Double volume(Crisp p1, Crisp p2, Crisp p3){
            return diff(p1).cross(diff(p2)).dot(diff(p3))/6.0;
        }

        /**
         * 
         * @param p1
         * @param p2
         * @return (p1-this)x(p2-this)/|(p1-this)x(p2-this)|
         */
        public Vector.Crisp normal(Crisp p1, Crisp p2){
            return p1.diff(this).cross(p2.diff(this)).normalize();
        }

        public Crisp transform(Transform a){
            return a.apply(this);
        }
    }
}
