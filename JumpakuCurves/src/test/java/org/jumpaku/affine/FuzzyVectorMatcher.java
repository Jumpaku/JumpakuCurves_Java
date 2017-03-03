/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

import org.apache.commons.math3.util.Precision;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author tomohiko
 */
public class FuzzyVectorMatcher extends TypeSafeMatcher<FuzzyVector>{
    
    private final FuzzyVector expected;
    
    @Factory public static Matcher<FuzzyVector> fuzzyVectorOf(FuzzyVector v){
        return new FuzzyVectorMatcher(v);
    }

    @Factory public static Matcher<FuzzyVector> fuzzyVectorOf(Double x, Double y, Double z, Double r){
        return new FuzzyVectorMatcher(FuzzyVector.of(x, y, z, r));
    }
    
    FuzzyVectorMatcher(FuzzyVector v){
        this.expected = v;
    }
    
    @Override protected boolean matchesSafely(FuzzyVector item) {
        return Vector.equals(expected, item, 1.0e-10) &&
                Precision.equals(expected.getR(), item.getR(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(FuzzyVector item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}
