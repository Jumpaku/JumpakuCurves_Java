/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.interpolation;

import javaslang.collection.Array;
import main.old.curves.vector.Point;

/**
 *
 * @author Jumpaku
 */
public interface KnotGenerator {
    <P extends Point> Array<Double> generate(Integer degree, Array<Data<P>> data);
}