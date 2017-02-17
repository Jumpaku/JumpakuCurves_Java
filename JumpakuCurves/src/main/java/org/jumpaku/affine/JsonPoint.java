/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.jumpaku.json.Converter;

/**
 *
 * @author Jumpaku
 */
public final class JsonPoint implements Converter<Point>{
    
    public static class Data{
    
        private final Double x;

        private final Double y;

        private final Double z;

        public Data(Double x, Double y, Double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    
    @Override public String toJson(Point v){
        return GSON.toJson(new Data(v.getX(), v.getY(), v.getZ()));
    }
    
    @Override public Point fromJson(String json){
        Data p = GSON.fromJson(json, Data.class);
        return Point.of(p.x, p.y, p.z);
    }   
}
