package org.jumpaku.curve.bspline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.affine.JsonVector;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.json.Converter;

import java.lang.reflect.Type;

/**
 * Created by jumpaku on 2017/05/01.
 */
public class JsonBSplineDerivative implements Converter<BSplineDerivative> {

    public static final Converter<BSplineDerivative> CONVERTER = new JsonBSplineDerivative();

    @Override
    public Type getTemporaryType() {
        return JsonBSplineDerivative.Data.class;
    }

    @Override
    public Temporary<BSplineDerivative> toTemporary(BSplineDerivative d) {
        return new Data(d);
    }

    public static final class Data implements Converter.Temporary<BSplineDerivative> {

        private final JsonInterval.Data interval;

        private final Integer degree;

        private final JsonVector.Data[] controlVectors;

        private final Double[] knots;

        public Data(BSplineDerivative db) {
            this.interval = new JsonInterval.Data(db.getDomain());
            this.degree = db.getDegree();
            this.controlVectors = db.getControlVectors().map(JsonVector.Data::new).toJavaArray(JsonVector.Data.class);
            this.knots = db.getKnots().toJavaArray(Double.class);
        }

        @Override
        public BSplineDerivative newInstance() {
            return BSplineDerivative.create(interval.newInstance(),
                    degree,
                    Stream.of(controlVectors)
                            .map(JsonVector.Data::newInstance)
                            .map(Vector::toCrisp),
                    Stream.of(knots));
        }
    }
}
