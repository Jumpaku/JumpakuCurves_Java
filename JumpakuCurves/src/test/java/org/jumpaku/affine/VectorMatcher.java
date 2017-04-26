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
 * @author jumpaku
 */
public class VectorMatcher extends TypeSafeMatcher<Vector>{
    
    private final Vector expected;
    
    @Factory public static Matcher<Vector> vectorOf(Vector v){
        return new VectorMatcher(v);
    }

    @Factory public static Matcher<Vector> vectorOf(Double x, Double y, Double z, Double r){
        return new VectorMatcher(Vector.fuzzy(x, y, z, r));
    }
    
    VectorMatcher(Vector v){
        this.expected = v;
    }
    
    @Override protected boolean matchesSafely(Vector item) {
        return Vector.equals(expected.toCrisp(), item.toCrisp(), 1.0e-10) &&
                Precision.equals(expected.getR(), item.getR(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(Vector item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}
