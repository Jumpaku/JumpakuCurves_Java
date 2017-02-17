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
public final class JsonVector implements Converter<Vector>{
    
    public static class VectorData{
    
        private final Double x;

        private final Double y;

        private final Double z;

        public VectorData(Double x, Double y, Double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    
    @Override public String toJson(Vector v){
        return GSON.toJson(new VectorData(v.getX(), v.getY(), v.getZ()));
    }
    
    @Override public Vector fromJson(String json){
        VectorData v = GSON.fromJson(json, VectorData.class);
        return Vector.of(v.x, v.y, v.z);
    }   
}
