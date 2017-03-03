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
 * @author tomohiko
 */
public class JsonFuzzyVector implements Converter<FuzzyVector>{

    public static final JsonFuzzyVector CONVERTER = new JsonFuzzyVector();

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Converter.Temporary<FuzzyVector> toTemporary(FuzzyVector v) {
        return new Data(v); 
    }
    
    public static final class Data implements Converter.Temporary<FuzzyVector>{
    
        private final Double x;

        private final Double y;

        private final Double z;
        
        private final Double r;

        public Data(FuzzyVector v) {
            this.x = v.getX();
            this.y = v.getY();
            this.z = v.getZ();
            this.r = v.getR();
        }

        @Override public FuzzyVector newInstance() {
            return FuzzyVector.of(x, y, z, r);
        }
    }
}