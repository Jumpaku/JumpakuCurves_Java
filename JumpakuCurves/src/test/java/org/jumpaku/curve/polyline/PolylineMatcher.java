/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve.polyline;

import org.apache.commons.math3.util.Precision;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jumpaku.affine.Point;

/**
 *
 * @author Jumpaku
 */
public class PolylineMatcher extends TypeSafeMatcher<Polyline>{
    
    private final Polyline expected;
    
    @Factory public static Matcher<Polyline> polylineOf(Polyline b){
        return new PolylineMatcher(b);
    }
    
    PolylineMatcher(Polyline b){
        this.expected = b;
    }
    
    @Override protected boolean matchesSafely(Polyline item) {
        return expected.getPoints().zipWith(item.getPoints(), (e, a)->Point.equals(e, a, 1.0e-10)).forAll(b->b)
                && expected.getPoints().size() == item.getPoints().size();
    }

    @Override public void describeTo(Description description) {
        description.appendValue(this.expected);
    }

    @Override protected void describeMismatchSafely(Polyline item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }
}