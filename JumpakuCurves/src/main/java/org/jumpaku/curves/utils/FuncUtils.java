/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.utils;

import java.util.function.BiFunction;
import javaslang.collection.Traversable;

/**
 *
 * @author Jumpaku
 */
public class FuncUtils {
    private FuncUtils(){}
    public static <X, Y, Z> Traversable<Z> zipWith(BiFunction<? super X, ? super Y, ? extends Z> f, Traversable<X> xs, Iterable<Y> ys){
        return xs.zip(ys).map(t -> f.apply(t._1(), t._2()));
    }
}
