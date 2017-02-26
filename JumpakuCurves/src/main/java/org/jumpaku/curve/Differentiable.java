/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import org.jumpaku.affine.Vector;

/**
 *
 * @author tomohiko
 */
public interface Differentiable {

    default Vector differentiate(Double t){
        return differentiate().evaluate(t);
    }
    
    Derivative differentiate();
}
