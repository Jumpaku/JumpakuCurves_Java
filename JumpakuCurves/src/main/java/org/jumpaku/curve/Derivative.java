/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import java.util.function.Function;
import org.jumpaku.affine.Vector;

/**
 *
 * @author Jumpaku
 */
public interface Derivative extends Function<Double, Vector>, Restrictable<Derivative>{

    @Override default Vector.Crisp apply(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);

        return evaluate(t);
    }
    
    Vector.Crisp evaluate(Double t);

    Interval getDomain();
    
    @Override public default Derivative restrict(Interval i) {
        if(!getDomain().includes(i))
            throw new IllegalArgumentException("i must be in " + getDomain() + ", but i = " + i);

        return new Derivative() {
            @Override public Vector.Crisp evaluate(Double t) {
                if(!i.includes(t))
                    throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
                
                return Derivative.this.evaluate(t);
            }

            @Override public Interval getDomain() {
                return i;
            }
        };
    }  
}
