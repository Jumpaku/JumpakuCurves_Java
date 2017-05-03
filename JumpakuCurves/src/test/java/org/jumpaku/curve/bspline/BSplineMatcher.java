package org.jumpaku.curve.bspline;

import org.apache.commons.math3.util.Precision;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jumpaku.affine.Vector;

/**
 * Created by jumpaku on 2017/05/03.
 */
public class BSplineMatcher extends TypeSafeMatcher<BSpline> {

    private final BSpline expected;

    @Factory public static Matcher<BSpline> bSplineOf(BSpline b){
        return new BSplineMatcher(b);
    }

    BSplineMatcher(BSpline b){
        this.expected = b;
    }

    @Override protected boolean matchesSafely(BSpline item) {
        return expected.getControlPoints()
                .zipWith(item.getControlPoints(),
                        (e, a)-> Vector.equals(e.toVector().toCrisp(), a.toVector().toCrisp(), 1.0e-10)
                                && Precision.equals(e.getR(), a.getR(), 1.0e-10)).forAll(b->b)
                && expected.getKnots().zipWith(item.getKnots(),
                        (e, a)-> Precision.equals(e.getValue(), a.getValue(), 1.0e-10)
                                && e.getMultiplicity().equals(a.getMultiplicity()))
                                .forAll(b->b)
                && expected.getControlPoints().size() == item.getControlPoints().size()
                && Precision.equals(expected.getDomain().getBegin(), item.getDomain().getBegin(), 1.0e-10)
                && Precision.equals(expected.getDomain().getEnd(), item.getDomain().getEnd(), 1.0e-10)
                && expected.getDegree().equals(item.getDegree());
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(BSpline item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}
