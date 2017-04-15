package org.jumpaku.curve.ratioionalbezier;



import java.lang.reflect.Type;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.affine.JsonPoint;
import org.jumpaku.affine.WeightedPoint;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.json.Converter;

/**
 *
 * @author jumpaku
 */

public final class JsonRationalBezier implements Converter<RationalBezier>{

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<RationalBezier> toTemporary(RationalBezier bezier) {
        return new Data(bezier);
    }
    
    public static final class Data implements Converter.Temporary<RationalBezier>{
        private final JsonPoint.Data[] controlPoints;
        private final Double[] weights;
        private final JsonInterval.Data interval;

        public Data(RationalBezier bezier) {
            this.controlPoints = bezier.getControlPoints().map(JsonPoint.Data::new).toJavaArray(JsonPoint.Data.class);
            this.weights = bezier.getWeights().toJavaArray(Double.class);
            this.interval = new JsonInterval.Data(bezier.getDomain());
        }

        @Override public RationalBezier newInstance() {
            return RationalBezier.create(interval.newInstance(),
                    Array.of(controlPoints).map(JsonPoint.Data::newInstance)
                            .zipWith(Stream.of(weights), WeightedPoint::new));
        }
    }
    
    public static final JsonRationalBezier CONVERTER = new JsonRationalBezier();
}
