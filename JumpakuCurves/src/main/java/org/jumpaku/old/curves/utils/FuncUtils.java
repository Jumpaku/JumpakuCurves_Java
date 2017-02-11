/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.old.curves.utils;

import java.util.function.BiFunction;
import javaslang.collection.Traversable;

/**
 *
 * @author Jumpaku
 */
public class FuncUtils {
    private FuncUtils(){}
    
    public static <T, U, R> Iterable<R> zipWith(Traversable<T> traversable, Iterable<? extends U> iterable, BiFunction<? super T, ? super U, ? extends R> combiner) {
        return traversable.zip(iterable).map(t -> combiner.apply(t._1, t._2));
    }
}
