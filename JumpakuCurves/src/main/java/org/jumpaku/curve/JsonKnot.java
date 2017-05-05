package org.jumpaku.curve;

import org.jumpaku.json.Converter;

import java.lang.reflect.Type;

/**
 * Created by jumpaku on 2017/05/04.
 */
public class JsonKnot implements Converter<Knot> {

    static final JsonKnot CONVERTER = new JsonKnot();

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<Knot> toTemporary(Knot knot) {
        return new Data(knot);
    }

    public static final class Data implements Converter.Temporary<Knot>{

        private final Double value;

        private final Integer multiplicity;

        public Data(Knot knot) {
            this.value = knot.getValue();
            this.multiplicity = knot.getMultiplicity();
        }

        @Override
        public Knot newInstance() {
            return Knot.of(value, multiplicity);
        }
    }
}
