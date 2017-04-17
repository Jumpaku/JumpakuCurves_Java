/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import java.lang.reflect.Type;
import javaslang.collection.Array;
import org.jumpaku.affine.JsonPoint;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.json.Converter;

/**
 *
 * @author Jumpaku
 */
public final class JsonBezier implements Converter<Bezier>{

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<Bezier> toTemporary(Bezier bezier) {
        return new Data(bezier);
    }

    public static final class Data implements Converter.Temporary<Bezier>{
        private final JsonPoint.Data[] controlPoints;
        private final JsonInterval.Data interval;

        public Data(Bezier bezier) {
            this.controlPoints = bezier.getControlPoints().map(JsonPoint.Data::new).toJavaArray(JsonPoint.Data.class);
            this.interval = new JsonInterval.Data(bezier.getDomain());
        }

        @Override public Bezier newInstance() {
            return Bezier.create(interval.newInstance(), Array.of(controlPoints).map(JsonPoint.Data::newInstance));
        }
    }
    
    public static final JsonBezier CONVERTER = new JsonBezier();
}