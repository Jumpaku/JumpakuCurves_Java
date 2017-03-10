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
 * @author Jumpaku
 */
public interface Curve extends Function<Double, Point>{

    @Override default Point apply(Double t) {
        if(!getDomain().includes(t))
            throw new IllegalArgumentException("t must be in " + getDomain() + ", but t = " + t);
        
        return evaluate(t);
    }
    
    /**
     * @param t
     * @return
     * @throws IllegalArgumentException !getDomain().includes(t)
     */
    Point evaluate(Double t);

    Interval getDomain();
}
