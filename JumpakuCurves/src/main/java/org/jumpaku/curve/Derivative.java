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
 * @author tomohiko
 */
public interface Derivative extends Function<Double, Vector>{

    @Override default Vector apply(Double t) {
        return evaluate(t);
    }
    
    Vector evaluate(Double t);

    Domain getDomain();
}
