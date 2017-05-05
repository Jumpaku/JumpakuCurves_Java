/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import java.lang.reflect.Type;
import javaslang.collection.Array;
import org.jumpaku.affine.JsonVector;
import org.jumpaku.affine.Vector;
import org.jumpaku.json.Converter;

/**
 *
 * @author  Jumpaku 
 */
public final class JsonBezierDerivative implements Converter<BezierDerivative>{

    public static final Converter<BezierDerivative> CONVERTER = new JsonBezierDerivative();

    @Override public Type getTemporaryType() {
        return JsonBezierDerivative.Data.class;
    }

    @Override public Temporary<BezierDerivative> toTemporary(BezierDerivative d) {
        return new Data(d);
    }
    
    public static final class Data implements Converter.Temporary<BezierDerivative> {

        private final JsonVector.Data[] controlVectors;
        
        public Data(BezierDerivative db) {
            this.controlVectors = db.getControlVectors().map(JsonVector.Data::new).toJavaArray(JsonVector.Data.class);
        }
        
        @Override public BezierDerivative newInstance() {
            return BezierDerivative.create(
                    Array.of(controlVectors)
                            .map(JsonVector.Data::newInstance)
                            .map(Vector::toCrisp));
        }
    }
}
