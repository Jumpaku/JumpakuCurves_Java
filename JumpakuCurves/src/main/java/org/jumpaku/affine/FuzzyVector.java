/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.apache.commons.math3.util.FastMath;
import org.jumpaku.fuzzy.Grade;
import org.jumpaku.fuzzy.Membership;

/**
 *
 * @author Jumpaku
 * @param <FV>
 */
public interface FuzzyVector<FV extends FuzzyVector<FV>> extends Membership<FV, Vector>, Vector{
    
    public static final class Cone implements FuzzyVector<Cone>{

        private final Vector vector;
        
        private  final Double fuzziness;

        public Cone(Vector vector, Double fuzziness) {
            this.vector = vector;
            this.fuzziness = fuzziness;
        }
        
        public Double getFuzziness() {
            return fuzziness;
        }

        @Override public Cone fAdd(Cone v) {
            return new Cone(vector.add(v), FastMath.abs(fuzziness) + FastMath.abs(v.fuzziness));
        }

        @Override public Cone fScale(Double a) {
            return new Cone(vector.scale(a), FastMath.abs(fuzziness*a));
        }

        @Override public Grade membership(Vector v) {
            return Grade.clamped(vector.sub(v).length()/getFuzziness());
        }

        @Override public Grade possibility(Cone v) {
        double ra = getFuzziness();
        double rb = v.getFuzziness();
        double d = sub(v).length();
        return !Double.isFinite(d/(ra + rb)) ? 
                Grade.of(Vector.equals(this, v, 1.0e-10)) : Grade.clamped(1.0 - d/(ra + rb));
        }

        @Override public Grade necessity(Cone v) {
        double ra = getFuzziness();
        double rb = v.getFuzziness();
        double d = sub(v).length();
        return !Double.isFinite(d/(ra + rb)) ? Grade.of(Vector.equals(this, v, 1.0e-10)) : 
               d < ra                        ? Grade.of(Math.min((ra - d)/(ra + rb), (ra + d)/(ra + rb))) :
                                               Grade.falseValue();
        }

        @Override public Vector add(Vector v) {
            return vector.add(v);
        }

        @Override public Vector scale(Double w) {
            return vector.scale(w);
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
    }
    
    FV fAdd(FV v);
    
    FV fScale(Double a);
    
    default FV fAdd(Double a, FV v){
        return fAdd(v.fScale(a));
    }

    default FV fSub(FV v){
        return fAdd(v.fNegate());
    }

    default FV fSub(Double a, FV v){
        return fSub(v.fScale(a));
    }
    
    default FV fNegate(){
        return fScale(-1.0);
    }
}
