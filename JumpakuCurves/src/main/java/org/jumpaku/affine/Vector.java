/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import javaslang.control.Option;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;
import org.jumpaku.fuzzy.Grade;
import org.jumpaku.fuzzy.Membership;

/**
 *
 * @author Jumpaku
 */
public interface Vector extends Membership<Vector, Vector.Crisp>{

    static Boolean equals(Crisp v1, Crisp v2, Double eps){
        return Precision.equals(v1.getX(), v2.getX(), eps)
                && Precision.equals(v1.getY(), v2.getY(), eps)
                && Precision.equals(v1.getZ(), v2.getZ(), eps);
    }

    static Fuzzy fuzzy(Crisp p, Double r){
        return new Fuzzy(p, r);
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
        return new Crisp(x, y, z);
    }
        
    static Crisp crisp(Double x, Double y){
        return crisp(x, y, 0.0);
    } 
    
    static Crisp crisp(Double x){
        return crisp(x, 0.0);
    }

    static final Crisp ZERO = crisp(0.0);
    
    static Crisp zero(){
        return ZERO;
    }
    
    static String toJson(Vector v){
        return JsonVector.CONVERTER.toJson(v);
    }
    
    static Option<Vector> fromJson(String json){
        return JsonVector.CONVERTER.fromJson(json);
    }

    default Vector add(Vector v){
        return new Fuzzy(Crisp.add(1.0, toCrisp(), 1.0, v.toCrisp()), getR()+v.getR());
    }

    Vector scale(Double w);
    
    Double getX();
    
    Double getY();
    
    Double getZ();
    
    Double getR();
    
    Crisp toCrisp();
    
    default Vector sub(Vector v){
        return add(v.negate());
    }
    
    default Vector sub(Double a, Vector v){
        return sub(v.scale(a));
    }

    default Vector add(Double a, Vector v){
        return add(v.scale(a));
    }
    
    default Vector negate(){
        return scale(-1.0);
    }
    
    @Override default Grade membership(Vector.Crisp v) {
        double d = sub(v).toCrisp().length();
        double r = getR();
        return Double.isFinite(d/r) ?
                Grade.clamped(1.0-d/r) : Grade.of(Vector.equals(toCrisp(), v.toCrisp(), 1.0e-10));
    }

    @Override default Grade possibility(Vector v) {
        double ra = getR();
        double rb = v.getR();
        double d = sub(v).toCrisp().length();
        return !Double.isFinite(d/(ra + rb)) ? 
                Grade.of(Vector.equals(toCrisp(), v.toCrisp(), 1.0e-10)) : Grade.clamped(1 - d/(ra + rb));
    }

    @Override default Grade necessity(Vector v) {
        double ra = getR();
        double rb = v.getR();
        double d = sub(v).toCrisp().length();
        return !Double.isFinite(d/(ra + rb)) ? Grade.of(Vector.equals(toCrisp(), v.toCrisp(), 1.0e-10)) : 
               d < rb                        ? Grade.of(FastMath.min(1-(ra - d)/(ra + rb), 1-(ra + d)/(ra + rb))) :
                                               Grade.falseValue();
    }
        
    static final class Fuzzy implements Vector{
        
        private final Crisp crisp;
        
        private final Double r;

        public Fuzzy(Crisp vector, Double r) {
            this.crisp = vector;
            this.r = r;
        }

        @Override public Fuzzy scale(Double w) {
            return new Fuzzy(crisp.scale(w), FastMath.abs(w*getR()));
        }

        @Override public Double getX() {
            return toCrisp().getX();
        }

        @Override public Double getY() {
            return toCrisp().getY();
        }

        @Override public Double getZ() {
            return toCrisp().getZ();
        }

        @Override public Double getR() {
            return r;
        }

        @Override public Crisp toCrisp() {
            return crisp;
        }

        @Override public String toString() {
            return toJson(this);
        }
    }
    
    static final class Crisp implements Vector{
        
        public static Crisp add(Double a, Crisp v1, Double b, Crisp v2){
            return new Crisp(new Vector3D(a, v1.vector, b, v2.vector));
        }
        
        private final Vector3D vector;

        public Crisp(Double x, Double y, Double z) {
             this(new Vector3D(x, y, z));
        }

        private Crisp(Vector3D v){
            this.vector = v;
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

        @Override public Double getR() {
            return 0.0;
        }

        @Override public Crisp toCrisp() {
            return this;
        }

        @Override public Crisp scale(Double w) {
            return new Crisp(vector.scalarMultiply(w));
        }

        @Override public Crisp negate() {
            return new Crisp(vector.negate());
        }
        
        public Crisp normalize(){
            return scale(1.0/length());
        }

        public Crisp resize(Double l){
            return scale(l/length());
        }

        public Double dot(Vector v) {
            return vector.dotProduct(new Vector3D(v.getX(), v.getY(), v.getZ()));
        }

        public Double square(){
            return vector.getNormSq();
        }

        public Double length(){
            return vector.getNorm();
        }

        public Crisp cross(Vector v){
            Vector3D cross = new Vector3D(getX(), getY(), getZ()).crossProduct(new Vector3D(v.getX(), v.getY(), v.getZ()));
            return new Crisp(cross.getX(), cross.getY(), cross.getZ());
        }

        public Double angle(Vector v){
            return Vector3D.angle(new Vector3D(getX(), getY(), getZ()), new Vector3D(v.getX(), v.getY(), v.getZ()));
        }

        @Override public String toString() {
            return toJson(this);
        }
    }
}
