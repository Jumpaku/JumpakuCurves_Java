/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.fuzzy;

/**
 *
 * @author Jumpaku
 * @param <U>
 * @param <T>
 */
public interface Membership<U extends Membership<U, T>, T> {
    Grade mu(T t);
    
    Grade possibility(U u);
     
    Grade necessity(U u); 
}
