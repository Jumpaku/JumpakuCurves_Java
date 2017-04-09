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
import org.jumpaku.affine.Point;
import org.jumpaku.affine.Vector;

/**
 *
 * @author tomohiko
 */
public class WeightedPointMatcher extends TypeSafeMatcher<WeightedPoint>{
    
    private final WeightedPoint expected;
    
    @Factory public static Matcher<WeightedPoint> weightedPointOf(Point p, Double weight){
        return new WeightedPointMatcher(new WeightedPoint(p, weight));
    }
    
    @Factory public static Matcher<WeightedPoint> weightedPointOf(WeightedPoint wp){
        return new WeightedPointMatcher(wp);
    }
    
    WeightedPointMatcher(WeightedPoint p){
        this.expected = p;
    }
    
    @Override protected boolean matchesSafely(WeightedPoint item) {
        return Vector.equals(expected.getPoint().toVector().toCrisp(), item.getPoint().toVector().toCrisp(), 1.0e-10) &&
                Precision.equals(expected.getPoint().getR(), item.getPoint().getR(), 1.0e-10) &&
                Precision.equals(expected.getWeight(), item.getWeight(), 1.0e-10)
                ;
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(WeightedPoint item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}