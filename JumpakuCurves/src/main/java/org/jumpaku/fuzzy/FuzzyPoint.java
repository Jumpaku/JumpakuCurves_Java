/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.fuzzy;

import org.jumpaku.curves.vector.Point;

/**
 *
 * @author Jumpaku
 * @param <F>
 */
public interface FuzzyPoint<F> extends Point{
    F getFuzzyness();
}
