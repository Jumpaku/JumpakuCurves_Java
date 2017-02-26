/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import java.lang.reflect.Type;
import org.jumpaku.json.Converter;

/**
 *
 * @author Jumpaku
 */
public final class JsonVector implements Converter<Vector>{

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<Vector> toTemporary(Vector v) {
        return new Data(v); 
    }
    
    public static class Data implements Converter.Temporary<Vector>{
    
        private final Double x;

        private final Double y;

        private final Double z;

        public Data(Vector v) {
            this.x = v.getX();
            this.y = v.getY();
            this.z = v.getZ();
        }

        @Override public Vector newInstance() {
            return Vector.of(x, y, z);
        }
    }
}
