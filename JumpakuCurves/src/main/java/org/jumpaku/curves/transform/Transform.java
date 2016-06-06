/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.transform;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 * <p>ベクトルの変換のインターフェイス Interface of Tramsformation of vector.</p>
 * @author Jumpaku
 * @param <S> 座標空間の種類  Type of the space. 
 * @param <V> {@link Transform#apply(org.apache.commons.math3.geometry.Vector) } の返り値の型. Type of returned value of {@link Transform#apply(org.apache.commons.math3.geometry.Vector) }.
 */
public interface Transform <S extends Space, V extends Vector> {
    V apply(V v);    
}
