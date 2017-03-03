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
public class JsonFuzzyPoint implements Converter<FuzzyPoint>{
    
    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Converter.Temporary<FuzzyPoint> toTemporary(FuzzyPoint p) {
        return new Data(p);
    }
    
    public static final class Data implements Converter.Temporary<FuzzyPoint>{
    
        private final Double x;

        private final Double y;

        private final Double z;
        
        private final Double r;

        public Data(FuzzyPoint p) {
            this.x = p.getX();
            this.y = p.getY();
            this.z = p.getZ();
            this.r = p.getR();
        }

        @Override public FuzzyPoint newInstance() {
            return FuzzyPoint.of(x, y, z, r);
        }
    }
    
    public static final Converter<FuzzyPoint> CONVERTER = new JsonFuzzyPoint();
}
