/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.affine;

import main.old.curves.vector.Point;

/**
 * <p>アフィン変換のインターフェイス Interface of affine tramsformation.</p>
 * @author Jumpaku
 * @param <P>
 */
public interface Affine <P extends Point> {
    /**
     * 
     * @param p
     * @return 
     */
    P apply(P p);
}
