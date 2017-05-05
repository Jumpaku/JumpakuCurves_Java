package org.jumpaku.curve;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import javaslang.control.Option;


/**
 * Created by jumpaku on 2017/05/03.
 */
public final class Knot implements Comparable<Knot> {

    public static Knot of(Double value, Integer multiplicity) {
        if (multiplicity < 0) {
            throw new IllegalArgumentException("negative multiplicity");
        }
        return new Knot(value, multiplicity);
    }

    public static Array<Knot> clampedUniformKnots(Integer degree, Integer nControlPoints){
        return clampedUniformKnots(0.0, (double)(nControlPoints - degree), degree, nControlPoints);
    }

    public static Array<Knot> clampedUniformKnots(Interval domain, Integer degree, Integer nControlPoints){
        return clampedUniformKnots(domain.getBegin(), domain.getEnd(), degree, nControlPoints);
    }

    public static Array<Knot> clampedUniformKnots(Double begin, Double end, Integer degree, Integer nControlPoints){
        int l = nControlPoints - degree;

        return Stream.of(Knot.of(begin, degree+1))
                .appendAll(Stream.range(1, l).map(
                        i->Knot.of((1.0-i)/l*begin + i/(double)l*end, 1)))
                .appendAll(Stream.of(Knot.of(end, degree+1)))
                .toArray();
    }

    public static String toJson(Knot knot) {
        return JsonKnot.CONVERTER.toJson(knot);
    }

    public static Option<Knot> fromJson(String json){
        return JsonKnot.CONVERTER.fromJson(json);
    }

    private final Double value;

    private final Integer multiplicity;

    public Knot(Double value, Integer multiplicity) {
        this.value = value;
        this.multiplicity = multiplicity;
    }

    @Override
    public int compareTo(Knot o) {
        return Double.compare(getValue(), o.getValue());
    }

    @Override
    public String toString() {
        return Knot.toJson(this);
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
}
