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
public final class JsonPoint implements Converter<Point>{

    public static final Converter<Point> CONVERTER = new JsonPoint();

    @Override
    public Type getTemporaryType() {
        return Data.class;
    }

    @Override
    public Converter.Temporary<Point> toTemporary(Point p) {
        return new Data(p);
    }
    
    public static final class Data implements Converter.Temporary<Point>{
    
        private final Double x;

        private final Double y;

        private final Double z;
        
        private final Double r;

        public Data(Point p) {
            this.x = p.getX();
            this.y = p.getY();
            this.z = p.getZ();
            this.r = p.getR();
        }

        @Override public Point newInstance() {
            return Point.fuzzy(x, y, z, r);
        }
    }
    
}
