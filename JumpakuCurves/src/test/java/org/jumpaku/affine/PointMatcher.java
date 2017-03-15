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
public class PointMatcher extends TypeSafeMatcher<Point>{
    
    private final Point expected;
    
    @Factory public static Matcher<Point> pointOf(Point p){
        return new PointMatcher(p);
    }
    
    @Factory public static Matcher<Point> pointOf(Double x, Double y, Double z, Double r){
        return new PointMatcher(Point.fuzzy(x, y, z, r));
    }
    
    PointMatcher(Point p){
        this.expected = p;
    }
    
    @Override protected boolean matchesSafely(Point item) {
        return Vector.equals(expected.toVector().toCrisp(), item.toVector().toCrisp(), 1.0e-10) &&
                Precision.equals(expected.getR(), item.getR(), 1.0e-10);
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(Point item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}