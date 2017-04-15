/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.bezier;

import org.apache.commons.math3.util.Precision;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jumpaku.affine.Vector;

/**
 *
 * @author Jumpaku
 */
public class BezierMatcher extends TypeSafeMatcher<Bezier>{
    
    private final Bezier expected;
    
    @Factory public static Matcher<Bezier> bezierOf(Bezier b){
        return new BezierMatcher(b);
    }
    
    BezierMatcher(Bezier b){
        this.expected = b;
    }
    
    @Override protected boolean matchesSafely(Bezier item) {
        return expected.getControlPoints().zipWith(item.getControlPoints(),
                (e, a)-> Vector.equals(e.toVector().toCrisp(), a.toVector().toCrisp(), 1.0e-10)
                        && Precision.equals(e.getR(), a.getR(), 1.0e-10)).forAll(b->b)
                        && expected.getControlPoints().size() == item.getControlPoints().size()
                        && Precision.equals(expected.getDomain().getBegin(), item.getDomain().getBegin(), 1.0e-10)
                        && Precision.equals(expected.getDomain().getEnd(), item.getDomain().getEnd(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(Bezier item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}
