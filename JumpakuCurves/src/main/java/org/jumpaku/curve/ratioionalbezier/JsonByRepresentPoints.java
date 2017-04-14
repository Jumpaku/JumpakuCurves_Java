/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import java.lang.reflect.Type;
import org.jumpaku.affine.JsonPoint;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.json.Converter;

/**
 *
 * @author jumpaku
 */
public class JsonByRepresentPoints implements Converter<ByRepresentPoints>{

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<ByRepresentPoints> toTemporary(ByRepresentPoints bezier) {
        return new Data(bezier);
    }
    
    public static final class Data implements Converter.Temporary<ByRepresentPoints>{
        private final JsonPoint.Data[] representPoints;
        private final Double weight;
        private final JsonInterval.Data interval;

        public Data(ByRepresentPoints bezier) {
            this.representPoints = bezier.getRepresentPoints()
                    .map(JsonPoint.Data::new).toJavaArray(JsonPoint.Data.class);
            this.weight = bezier.getWeight();
            this.interval = new JsonInterval.Data(bezier.getDomain());
        }

        @Override public ByRepresentPoints newInstance() {
            return new ByRepresentPoints(interval.newInstance(), weight,
                    representPoints[0].newInstance(), representPoints[1].newInstance(), representPoints[2].newInstance());
        }
    }
    
    public static final JsonByRepresentPoints CONVERTER = new JsonByRepresentPoints();
}
