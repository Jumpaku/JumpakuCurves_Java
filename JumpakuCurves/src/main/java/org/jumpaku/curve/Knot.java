package org.jumpaku.curve;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Stream;


/**
 * Created by jumpaku on 2017/05/03.
 */
public final class Knot implements Comparable<Knot> {

    public static Knot of(Double value, Integer multiple) {
        return new Knot(value, multiple);
    }


    private final Double value;

    private final Integer multiplicity;

    public Knot(Double value, Integer multiplicity) {
        this.value = value;
        this.multiplicity = multiplicity;
    }

    public Double getValue() {
        return value;
    }

    public Array<Double> toArray(){ return Stream.fill(getMultiplicity(), this::getValue).toArray();}

    public Integer getMultiplicity() {
        return multiplicity;
    }

    public Knot reduceMultiplicity() {
        return reduceMultiplicity(1);
    }

    public Knot reduceMultiplicity(Integer m) {
        return of(getValue(), getMultiplicity() - m);
    }

    public Knot elevateMultiplicity() {
        return elevateMultiplicity(1);
    }

    public Knot elevateMultiplicity(Integer m) {
        return of(getValue(), getMultiplicity() + m);
    }

    @Override
    public int compareTo(Knot o) {
        return Double.compare(getValue(), o.getValue());
    }

    public Tuple2<Double, Integer> toTuple() {
        return Tuple.of(getValue(), getMultiplicity());
    }
}
