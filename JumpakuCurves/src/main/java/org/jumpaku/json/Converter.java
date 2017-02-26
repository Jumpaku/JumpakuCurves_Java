/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.json;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import javaslang.control.Option;

/**
 *
 * @author Jumpaku
 * @param <D>
 */
public interface Converter<D> {
    
    static interface Temporary<D>{
        D newInstance();
    }
    
    static final Gson GSON = new Gson();
    
    default String toJson(D d){
        return GSON.toJson(toTemporary(d));
    }
    
    default Option<D> fromJson(String json){
        try{
            return Option.of(((Temporary<D>)GSON.fromJson(json, getTemporaryType()))
                    .newInstance());
        }catch(Exception e){
            return Option.none();
        }
    }

    Type getTemporaryType();
    
    Temporary<D> toTemporary(D d);
}
