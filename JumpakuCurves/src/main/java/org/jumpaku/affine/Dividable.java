/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

/**
 *
 * @author jumpaku
 */
public interface Dividable<P>{
    Dividable<P> divide(Double t, P p);
}