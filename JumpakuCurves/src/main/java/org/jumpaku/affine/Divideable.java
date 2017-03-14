/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.affine;

/**
 *
 * @author tomohiko
 */
public interface Divideable<P>{
    Divideable<P> divide(Double t, P p);
}
