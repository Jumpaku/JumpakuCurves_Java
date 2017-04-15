/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.interpolation;

import main.old.curves.Curve;

/**
 *
 * @author Jumpaku
 * @param <C>
 */
public interface Interpolater<C extends Curve> {
    C interpolate();
}
