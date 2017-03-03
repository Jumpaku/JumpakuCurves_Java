/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.jumpaku.fuzzy.Grade;
import org.jumpaku.fuzzy.Membership;

/**
 *
 * @author Jumpaku
 * @param <FV>
 * @param <FP>
 */
public interface FuzzyPoint<FV extends FuzzyVector<FV>, FP extends FuzzyPoint<FV, FP>> extends Membership<FP, Point>, Point{

    public static final class Cone implements FuzzyPoint<FuzzyVector.Cone, Cone>{
        
        private final FuzzyVector.Cone fuzzyVector;

        public Cone(FuzzyVector.Cone fuzzyVector) {
            this.fuzzyVector = fuzzyVector;
        }
        
        @Override public Cone fMove(FuzzyVector.Cone v) {
            return new Cone(fuzzyVector.fAdd(v));
        }

        @Override public FuzzyVector.Cone fDiff(Cone p) {
            return fuzzyVector.fSub(p.fuzzyVector);
        }

        @Override public Grade membership(Point p) {
            return fuzzyVector.membership(p.getVector());
        }

        @Override public Grade possibility(Cone p) {
            return fuzzyVector.possibility(p.fuzzyVector);
        }

        @Override public Grade necessity(Cone p) {
            return fuzzyVector.necessity(p.fuzzyVector);
        }

        @Override public Vector getVector() {
            return fuzzyVector;
        }
    }
    
    FP fMove(FV v);
    
    FV fDiff(FP p);
    
    default FP fDivide(Double t, FP p){
        return p.fMove(fDiff(p).fScale(1-t));
    }
}
