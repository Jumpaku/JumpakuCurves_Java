/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import java.util.function.Function;

/**
 *
 * @author Jumpaku
 * @param <C>
 */
public interface Restrictable<C extends Function> {
    
    C restrict(Interval i);
    
    default C restrict(Double begin, Double end){
        return restrict(Interval.closed(begin, end));
    }
}