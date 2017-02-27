/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import java.lang.reflect.Type;
import javaslang.collection.Array;
import org.jumpaku.affine.JsonVector;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.json.Converter;

/**
 *
 * @author  Jumpaku 
 */
public final class JsonBezierDerivative implements Converter<BezierDerivative>{

    public static final Converter<BezierDerivative> CONVERTER = new JsonBezierDerivative();
    
    @Override public Type getTemporaryType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override public Temporary<BezierDerivative> toTemporary(BezierDerivative d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static final class Data implements Converter.Temporary<BezierDerivative> {

        private final JsonVector.Data[] controlVectors;
        
        private final JsonInterval.Data interval;

        public Data(BezierDerivative db) {
            this.controlVectors = db.getControlVectors().map(JsonVector.Data::new).toJavaArray(JsonVector.Data.class);
            this.interval = new JsonInterval.Data(db.getDomain());
        }
        
        @Override public BezierDerivative newInstance() {
            return BezierDerivative.create(
                    Array.of(controlVectors).map(JsonVector.Data::newInstance), interval.newInstance());
        }
    }
}
