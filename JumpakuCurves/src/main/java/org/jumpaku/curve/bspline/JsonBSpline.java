package org.jumpaku.curve.bspline;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.affine.JsonPoint;
import org.jumpaku.curve.JsonInterval;
import org.jumpaku.curve.Knot;
import org.jumpaku.json.Converter;

import java.lang.reflect.Type;

/**
 * Created by jumpaku on 2017/05/01.
 */
public final class JsonBSpline  implements Converter<BSpline> {

    public static final JsonBSpline CONVERTER = new JsonBSpline();

    @Override
    public Type getTemporaryType() {
        return Data.class;
    }

    @Override
    public Temporary<BSpline> toTemporary(BSpline bezier) {
        return new Data(bezier);
    }

    public static final class KnotData{

        private final Integer multiplicity;

        private final Double value;

        public KnotData(Double value, Integer multiplicity) {
            this.multiplicity = multiplicity;
            this.value = value;
        }

        public Integer getMultiplicity() {
            return multiplicity;
        }

        public Double getValue() {
            return value;
        }
    }

    public static final class Data implements Converter.Temporary<BSpline>{

        private final Integer degree;

        private final JsonPoint.Data[] controlPoints;

        private final KnotData[] knots;

        public Data(BSpline bSpline) {
            this.degree = bSpline.getDegree();
            this.controlPoints = bSpline.getControlPoints().map(JsonPoint.Data::new).toJavaArray(JsonPoint.Data.class);
            this.knots = bSpline.getKnots().map(knot->new KnotData(knot.getValue(), knot.getMultiplicity())).toJavaArray(KnotData.class);
        }

        @Override
        public BSpline newInstance() {
            return BSpline.create(degree,
                    Stream.of(controlPoints).map(JsonPoint.Data::newInstance),
                    Stream.of(knots).map(k->Knot.of(k.value, k.multiplicity)));
        }
    }
}