/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import org.jumpaku.curves.Curve;
import org.jumpaku.curves.vector.Point;

/**
 *
 * @author Jumpaku
 * @param <P>
 * @param <C>
 */
public interface Interpolater<P extends Point, C extends Curve> {
    C interpolate();
}
