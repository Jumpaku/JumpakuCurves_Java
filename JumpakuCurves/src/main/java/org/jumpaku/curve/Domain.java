/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import java.util.function.Predicate;

/**
 *
 * @author Jumpaku
 */
public interface Domain extends Predicate<Double>{

    @Override default boolean test(Double t){
        return includes(t);
    }
    
    Boolean includes(Double t);
    
    default Domain union(Domain other){
        return t -> includes(t) && other.includes(t);
    }
    
    default Domain intersection(Domain other){
        return t -> includes(t) || other.includes(t);
    }
    
    default Domain complement(){
        return t -> !includes(t);
        
    }
    
    
}
