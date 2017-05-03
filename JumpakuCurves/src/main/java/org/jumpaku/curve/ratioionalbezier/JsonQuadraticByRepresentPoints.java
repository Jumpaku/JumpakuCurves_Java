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
public class JsonQuadraticByRepresentPoints implements Converter<QuadraticByRepresentPoints>{

    public static final JsonQuadraticByRepresentPoints CONVERTER = new JsonQuadraticByRepresentPoints();

    @Override
    public Type getTemporaryType() {
        return Data.class;
    }

    @Override
    public Temporary<QuadraticByRepresentPoints> toTemporary(QuadraticByRepresentPoints bezier) {
        return new Data(bezier);
    }
    
    public static final class Data implements Converter.Temporary<QuadraticByRepresentPoints>{
        private final JsonPoint.Data[] representPoints;
        private final Double weight;

        public Data(QuadraticByRepresentPoints bezier) {
            this.representPoints = bezier.getRepresentPoints()
                    .map(JsonPoint.Data::new).toJavaArray(JsonPoint.Data.class);
            this.weight = bezier.getWeight();
        }

        @Override
        public QuadraticByRepresentPoints newInstance() {
            return new QuadraticByRepresentPoints(
                    weight,
                    representPoints[0].newInstance(), representPoints[1].newInstance(), representPoints[2].newInstance());
        }
    }
}
