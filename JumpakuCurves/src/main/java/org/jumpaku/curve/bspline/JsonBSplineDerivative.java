package org.jumpaku.curve.bspline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.affine.JsonVector;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.JsonKnot;
import org.jumpaku.curve.Knot;
import org.jumpaku.curve.bezier.Bezier;
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

        private final JsonVector.Data[] controlVectors;

        private final JsonKnot.Data[] knots;

        public Data(BSplineDerivative db) {
            this.controlVectors = db.getControlVectors().map(JsonVector.Data::new).toJavaArray(JsonVector.Data.class);
            this.knots = db.getKnots().map(JsonKnot.Data::new)
                    .toJavaArray(JsonKnot.Data.class);
        }

        @Override
        public BSplineDerivative newInstance() {
            Array<Knot> ts = Stream.of(knots)
                    .map(JsonKnot.Data::newInstance).toArray();

            return BSplineDerivative.create(
                    ts.flatMap(Knot::toArray).sum().intValue()-controlVectors.length-1,
                    Stream.of(controlVectors).map(JsonVector.Data::newInstance).map(Vector::toCrisp),
                    ts);
        }
    }
}
