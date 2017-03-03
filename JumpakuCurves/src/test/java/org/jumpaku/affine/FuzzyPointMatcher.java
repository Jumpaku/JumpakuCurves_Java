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
public class FuzzyPointMatcher extends TypeSafeMatcher<FuzzyPoint>{
    
    private final FuzzyPoint expected;
    
    @Factory public static Matcher<FuzzyPoint> fuzzyPointOf(FuzzyPoint p){
        return new FuzzyPointMatcher(p);
    }
    
    @Factory public static Matcher<FuzzyPoint> fuzzyPointOf(Double x, Double y, Double z, Double r){
        return new FuzzyPointMatcher(FuzzyPoint.of(x, y, z, r));
    }
    
    FuzzyPointMatcher(FuzzyPoint p){
        this.expected = p;
    }
    
    @Override protected boolean matchesSafely(FuzzyPoint item) {
        return Point.equals(expected, item, 1.0e-10) &&
                Precision.equals(expected.getR(), item.getR(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(FuzzyPoint item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}