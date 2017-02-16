/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.json;

import com.google.gson.Gson;

/**
 *
 * @author Jumpaku
 * @param <T>
 */
public interface Converter<T> {
    
    static final Gson GSON = new Gson();
    
    String toJson(T t);
    
    T fromJson(String json);
    
}
