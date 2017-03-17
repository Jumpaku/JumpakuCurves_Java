/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.ratioionalbezier;

import org.apache.commons.math3.util.Precision;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jumpaku.affine.Vector;
import org.jumpaku.curve.bezier.Bezier;

/**
 *
 * @author tomohiko
 */
public class RationalBezierMatcher extends TypeSafeMatcher<RationalBezier>{
    
    private final RationalBezier expected;
    
    @Factory public static Matcher<RationalBezier> rationalBezierOf(RationalBezier b){
        return new RationalBezierMatcher(b);
    }
    
    RationalBezierMatcher(RationalBezier b){
        this.expected = b;
    }
    
    @Override protected boolean matchesSafely(RationalBezier item) {
        return expected.getControlPoints().zipWith(item.getControlPoints(),
                (e, a)->{
                    return Vector.equals(e.toVector().toCrisp(), a.toVector().toCrisp(), 1.0e-10)
                            && Precision.equals(e.getR(), a.getR(), 1.0e-10);
                        }).forAll(b->b)
                && expected.getControlPoints().size() == item.getControlPoints().size()
                && expected.getWeights().zipWith(item.getWeights(),
                        (e, a)->Precision.equals(e, a, 1.0e-10)).forAll(b->b)
                && expected.getWeights().size() == item.getWeights().size()
                && Precision.equals(expected.getDomain().getBegin(), item.getDomain().getBegin(), 1.0e-10)
                && Precision.equals(expected.getDomain().getEnd(), item.getDomain().getEnd(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(RationalBezier item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}
