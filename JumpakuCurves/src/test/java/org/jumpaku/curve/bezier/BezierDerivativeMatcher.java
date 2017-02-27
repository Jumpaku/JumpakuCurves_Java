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
 * @author  Jumpaku 
 */
public class BezierDerivativeMatcher extends TypeSafeMatcher<BezierDerivative>{
    
    private final BezierDerivative expected;
    
    @Factory public static Matcher<BezierDerivative> bezierDerivativeOf(BezierDerivative b){
        return new BezierDerivativeMatcher(b);
    }
    
    BezierDerivativeMatcher(BezierDerivative b){
        this.expected = b;
    }
    
    @Override protected boolean matchesSafely(BezierDerivative item) {
        return expected.getControlVectors().zipWith(item.getControlVectors(), (e, a)->Vector.equals(e, a, 1.0e-10)).forAll(b->b)
                && expected.getControlVectors().size() == item.getControlVectors().size()
                && Precision.equals(expected.getDomain().getBegin(), item.getDomain().getBegin(), 1.0e-10)
                && Precision.equals(expected.getDomain().getEnd(), item.getDomain().getEnd(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(BezierDerivative item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}