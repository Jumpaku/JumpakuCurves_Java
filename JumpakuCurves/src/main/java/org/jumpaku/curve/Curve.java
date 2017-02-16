/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import java.util.function.Function;
import org.jumpaku.affine.Point;

/**
 *
 * @author tomohiko
 * @param <P>
 */
public interface Curve<P extends Point> extends Function<Double, P>{

    @Override default P apply(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
        
        return evaluate(t);
    }
    
    P evaluate(Double t);
    
    Domain getDomain();
}
