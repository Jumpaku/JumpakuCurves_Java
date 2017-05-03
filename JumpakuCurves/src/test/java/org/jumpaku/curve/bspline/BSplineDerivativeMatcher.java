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
public class BSplineDerivativeMatcher extends TypeSafeMatcher<BSplineDerivative> {

    private final BSplineDerivative expected;

    @Factory
    public static Matcher<BSplineDerivative> bSplineDerivativeOf(BSplineDerivative b){
        return new BSplineDerivativeMatcher(b);
    }

    BSplineDerivativeMatcher(BSplineDerivative b){
        this.expected = b;
    }

    @Override protected boolean matchesSafely(BSplineDerivative item) {
        return expected.getControlVectors()
                .zipWith(item.getControlVectors(),
                        (e, a)-> Vector.equals(e.toCrisp(), a.toCrisp(), 1.0e-10)
                                && Precision.equals(e.getR(), a.getR(), 1.0e-10)).forAll(b->b)
                && expected.getKnots().zipWith(item.getKnots(),
                (e, a)-> Precision.equals(e, a, 1.0e-10)).forAll(b->b)
                && expected.getControlVectors().size() == item.getControlVectors().size()
                && Precision.equals(expected.getDomain().getBegin(), item.getDomain().getBegin(), 1.0e-10)
                && Precision.equals(expected.getDomain().getEnd(), item.getDomain().getEnd(), 1.0e-10)
                && expected.getDegree().equals(item.getDegree());
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(BSplineDerivative item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}
