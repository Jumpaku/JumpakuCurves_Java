/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import javaslang.collection.Array;
import org.jumpaku.curves.vector.Vec;

/**
 *
 * @author Jumpaku
 */
public interface KnotGenerator {
    <V extends Vec> Array<Double> generate(Integer degree, Array<Data<V>> data);
}
