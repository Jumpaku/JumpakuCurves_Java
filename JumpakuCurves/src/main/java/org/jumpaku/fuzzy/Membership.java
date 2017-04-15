/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.fuzzy;

import java.util.function.Function;

/**
 *
 * @author Jumpaku
 * @param <M>
 * @param <T>
 */
public interface Membership<M extends Membership<M, T>, T> extends Function<T, Grade>{

    @Override
    default Grade apply(T t) {
        return membership(t);
    }
    
    Grade membership(T t);
    
    Grade possibility(M u);
     
    Grade necessity(M u); 
}
