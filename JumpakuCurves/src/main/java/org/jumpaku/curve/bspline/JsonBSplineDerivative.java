package org.jumpaku.curve.bspline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.affine.JsonVector;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.curve.Knot;
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

        private final Integer degree;

        private final JsonVector.Data[] controlVectors;

        private final JsonBSpline.KnotData[] knots;

        public Data(BSplineDerivative db) {
            this.degree = db.getDegree();
            this.controlVectors = db.getControlVectors().map(JsonVector.Data::new).toJavaArray(JsonVector.Data.class);
            this.knots = db.getKnots().map(k->new JsonBSpline.KnotData(k.getValue(), k.getMultiplicity()))
                    .toJavaArray(JsonBSpline.KnotData.class);
        }

        @Override
        public BSplineDerivative newInstance() {
            return BSplineDerivative.create(
                    degree,
                    Stream.of(controlVectors)
                            .map(JsonVector.Data::newInstance)
                            .map(Vector::toCrisp),
                    Stream.of(knots)
                            .map(k-> Knot.of(k.getValue(), k.getMultiplicity())));
        }
    }
}
