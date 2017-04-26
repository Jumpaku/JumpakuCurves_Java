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

/**
 *
 * @author Jumpaku
 */
public class QuadraticByRepresentPointsMatcher extends TypeSafeMatcher<QuadraticByRepresentPoints>{
    
    private final QuadraticByRepresentPoints expected;
    
    @Factory public static Matcher<QuadraticByRepresentPoints> quadraticByRepresentPointOf(QuadraticByRepresentPoints b){
        return new QuadraticByRepresentPointsMatcher(b);
    }
    
    QuadraticByRepresentPointsMatcher(QuadraticByRepresentPoints b){
        this.expected = b;
    }
    
    @Override protected boolean matchesSafely(QuadraticByRepresentPoints item) {
        return expected.getRepresentPoints().zipWith(item.getRepresentPoints(),
                (e, a)-> Vector.equals(e.toVector().toCrisp(), a.toVector().toCrisp(), 1.0e-10)
                        && Precision.equals(e.getR(), a.getR(), 1.0e-10)).forAll(b->b)
                        && expected.getRepresentPoints().size() == item.getRepresentPoints().size()
                        && Precision.equals(expected.getWeight(), item.getWeight(), 1.0e-10)
                        && Precision.equals(expected.getDomain().getBegin(), item.getDomain().getBegin(), 1.0e-10)
                        && Precision.equals(expected.getDomain().getEnd(), item.getDomain().getEnd(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(QuadraticByRepresentPoints item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}
