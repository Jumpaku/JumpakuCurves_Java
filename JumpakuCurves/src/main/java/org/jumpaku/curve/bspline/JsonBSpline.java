package org.jumpaku.curve.bspline;

import javaslang.collection.Stream;
import org.jumpaku.affine.JsonPoint;
import org.jumpaku.curve.JsonKnot;
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
    public Temporary<BSpline> toTemporary(BSpline bSpline) {
        return new Data(bSpline);
    }

    public static final class Data implements Converter.Temporary<BSpline>{

        private final JsonPoint.Data[] controlPoints;

        private final JsonKnot.Data[] knots;

        public Data(BSpline bSpline) {
            this.controlPoints = bSpline.getControlPoints().map(JsonPoint.Data::new).toJavaArray(JsonPoint.Data.class);
            this.knots = bSpline.getKnots().map(JsonKnot.Data::new).toJavaArray(JsonKnot.Data.class);
        }

        @Override
        public BSpline newInstance() {
            return BSpline.create(
                    Stream.of(controlPoints).map(JsonPoint.Data::newInstance),
                    Stream.of(knots).map(JsonKnot.Data::newInstance));
        }
    }
}