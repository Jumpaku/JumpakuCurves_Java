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
 * @author jumpaku
 */
public final class JsonVector implements Converter<Vector>{

    public static final JsonVector CONVERTER = new JsonVector();

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Converter.Temporary<Vector> toTemporary(Vector v) {
        return new Data(v); 
    }
    
    public static final class Data implements Converter.Temporary<Vector>{
    
        private final Double x;

        private final Double y;

        private final Double z;
        
        private final Double r;

        public Data(Vector v) {
            this.x = v.getX();
            this.y = v.getY();
            this.z = v.getZ();
            this.r = v.getR();
        }

        @Override public Vector newInstance() {
            return Vector.fuzzy(x, y, z, r);
        }
    }
}