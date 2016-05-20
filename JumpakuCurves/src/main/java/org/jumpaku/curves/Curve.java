/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 * 曲線のインターフェイス Interface of curve.<br>
 * 実数から平面や空間への写像を表す.<br>
 * Represents mapping from real number to plane or space.
 * @author jumpaku
 * @param <S> 座標空間の種類 Type of the space. {@link Space}のサブクラスでなければばらない. S must be inherited {@link Space}
 * @param <V> {@link Curve#evaluate(java.lang.Double)} の返り値の型. {@link Vector}のサブクラスでなければならない. V must be inherited {@link Vector}
 */
@FunctionalInterface
public interface Curve<S extends Space, V extends Vector<S>>{
    /**
     * パラメータtに対応する評価点のベクトルを返す.
     * @param t パラメータ parameter
     * @return 
     */
    V evaluate(Double t);
}
