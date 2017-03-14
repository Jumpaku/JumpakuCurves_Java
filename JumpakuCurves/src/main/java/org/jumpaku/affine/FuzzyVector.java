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

/**
 *
 * @author Jumpaku
 */
public interface FuzzyVector extends Membership<FuzzyVector, Vector>, Vector{

    public static FuzzyVector add(double a, FuzzyVector v0, double b, FuzzyVector v1){
        return new Cone(Vector.add(a, v0, b, v1),
                FastMath.abs(a*v0.getR())+FastMath.abs(b*v1.getR()));
    }
    
    public static final class Cone implements FuzzyVector{

        private final Vector vector;
        
        private  final Double r;

        public Cone(Vector vector, Double fuzziness) {
            this.vector = vector;
            this.r = fuzziness;
        }
        
        @Override public Double getR() {
            return r;
        }

        @Override public FuzzyVector add(Vector v) {
            return new Cone(vector.add(v), getR() + (v instanceof FuzzyVector ? ((FuzzyVector)v).getR() : 0.0));
        }

        @Override public FuzzyVector scale(Double a) {
            return new Cone(vector.scale(a), getR()*FastMath.abs(a));
        }

        @Override public Grade membership(Vector v) {
            double d = vector.sub(v).length();
            double r = getR();
            return Double.isFinite(d/r) ?
                    Grade.clamped(1.0-d/r) : Grade.of(Vector.equals(this, v, 1.0e-10));
        }

        @Override public Grade possibility(FuzzyVector v) {
            double ra = getR();
            double rb = v.getR();
            double d = sub(v).length();
            return !Double.isFinite(d/(ra + rb)) ? 
                    Grade.of(Vector.equals(this, v, 1.0e-10)) : Grade.clamped(1.0 - d/(ra + rb));
        }

        @Override public Grade necessity(FuzzyVector v) {
            double ra = getR();
            double rb = v.getR();
            double d = sub(v).length();
            return !Double.isFinite(d/(ra + rb)) ? Grade.of(Vector.equals(this, v, 1.0e-10)) : 
                   d < ra                        ? Grade.of(Math.min((ra - d)/(ra + rb), (ra + d)/(ra + rb))) :
                                                   Grade.falseValue();
        }

        @Override public Double dot(Vector v) {
            return vector.dot(v);
        }

        @Override public Double getX() {
            return vector.getX();
        }

        @Override public Double getY() {
            return vector.getY();
        }

        @Override public Double getZ() {
            return vector.getZ();
        }
        @Override public String toString(){
            return FuzzyVector.toJson(this);
        }
    }
    
    static FuzzyVector of(Double x, Double y, Double z, Double r){
        if(r.compareTo(0.0) < 0)
            throw new IllegalArgumentException("r must be grater than or equal to 0.0, but r = " + r);
 
        return of(Vector.of(x, y, z), r);
    }
    
    static FuzzyVector of(Double x, Double y, Double r){
        return of(Vector.of(x, y, 0.0), r);
    }
    
    static FuzzyVector of(Double x, Double r){
        return of(Vector.of(x, 0.0), r);
    }
    
    static FuzzyVector zero(Double r){
        return of(Vector.zero(), r);
    }
    
    static FuzzyVector of(Vector v, Double r){
        return new Cone(v, r);
    }
    
    static FuzzyVector crisp(Double x, Double y, Double z){
        return of(x, y, z, 0.0);
    }
    
    static FuzzyVector crisp(Double x, Double y){
        return of(x, y, 0.0);
    }    
    
    static FuzzyVector crisp(Double x){
        return of(x, 0.0);
    }    
    
    static FuzzyVector crisp(Vector v){
        return of(v, 0.0);
    }    
    
    static FuzzyVector zero(){
        return zero(0.0);
    }
    
    static String toJson(FuzzyVector v){
        return JsonFuzzyVector.CONVERTER.toJson(v);
    }
    
    static Option<FuzzyVector> fromJson(String json){
        return JsonFuzzyVector.CONVERTER.fromJson(json);
    }

    Double getR();
    
    @Override FuzzyVector add(Vector v);
    
    @Override FuzzyVector scale(Double a);

    @Override default FuzzyVector sub(Vector v){
        return add(v.negate());
    }
    
    @Override default FuzzyVector sub(Double a, Vector v){
        return sub(v.scale(a));
    }
    
    @Override default FuzzyVector add(Double a, Vector v){
        return add(v.scale(a));
    }
    
    @Override default FuzzyVector negate(){
        return scale(-1.0);
    }
    
    @Override default FuzzyVector normalize(){
        return scale(1.0/length());
    }
    
    @Override default FuzzyVector resize(Double l){
        return scale(l/length());
    }    
    
}
