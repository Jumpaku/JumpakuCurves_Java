package org.jumpaku.affine;



import java.lang.reflect.Type;

import org.jumpaku.json.Converter;

/**
 *
 * @author jumpaku
 */

public final class JsonWeightedPoint implements Converter<WeightedPoint>{

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<WeightedPoint> toTemporary(WeightedPoint bezier) {
        return new Data(bezier);
    }

    public static final class Data implements Converter.Temporary<WeightedPoint>{
        private final JsonPoint.Data point;
        private final Double weight;

        public Data(WeightedPoint point) {
            this.point = new JsonPoint.Data(point.getPoint());
            this.weight = point.getWeight();
        }

        @Override public WeightedPoint newInstance() {
            return new WeightedPoint(point.newInstance(), weight);
        }
    }

    public static final JsonWeightedPoint CONVERTER = new JsonWeightedPoint();
}
