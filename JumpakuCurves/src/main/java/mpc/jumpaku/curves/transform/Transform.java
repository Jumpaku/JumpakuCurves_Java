/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.transform;

import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author ito
 * @param <V>
 */
public interface Transform <V extends Vector> {
    V apply(V v);    
}
