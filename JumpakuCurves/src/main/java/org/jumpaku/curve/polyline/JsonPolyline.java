/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import java.lang.reflect.Type;
import javaslang.collection.Array;
import org.jumpaku.affine.JsonPoint;
import org.jumpaku.json.Converter;

/**
 *
 * @author jumpaku
 */
public final class JsonPolyline implements Converter<Polyline>{

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<Polyline> toTemporary(Polyline polyline) {
        return new Data(polyline);
    }

    public static final class Data implements Converter.Temporary<Polyline>{
        private final JsonPoint.Data[] points;

        public Data(Polyline polyline) {
            this.points = polyline.getPoints().map(JsonPoint.Data::new).toJavaArray(JsonPoint.Data.class);
        }

        @Override public Polyline newInstance() {
            return Polyline.create(Array.of(points).map(JsonPoint.Data::newInstance));
        }
    }
    
    public static final JsonPolyline CONVERTER = new JsonPolyline();
}

