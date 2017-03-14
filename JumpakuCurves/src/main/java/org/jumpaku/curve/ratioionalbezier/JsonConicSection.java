/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import java.lang.reflect.Type;
import org.jumpaku.affine.JsonFuzzyPoint;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.json.Converter;

/**
 *
 * @author tomohiko
 */
public class JsonConicSection implements Converter<ConicSection>{

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<ConicSection> toTemporary(ConicSection bezier) {
        return new Data(bezier);
    }
    
    public static final class Data implements Converter.Temporary<ConicSection>{
        private final JsonFuzzyPoint.Data[] representPoints;
        private final Double weight;
        private final JsonInterval.Data interval;

        public Data(ConicSection bezier) {
            this.representPoints = bezier.getRepresentPoints()
                    .map(JsonFuzzyPoint.Data::new).toJavaArray(JsonFuzzyPoint.Data.class);
            this.weight = bezier.getWeight();
            this.interval = new JsonInterval.Data(bezier.getDomain());
        }

        @Override public ConicSection.ByRepresentPoints newInstance() {
            return RationalBezier.byRepresentPoints(interval.newInstance(), weight, 
                    representPoints[0].newInstance(), representPoints[1].newInstance(), representPoints[2].newInstance());
        }
    }
    
    public static final JsonConicSection CONVERTER = new JsonConicSection();
}
