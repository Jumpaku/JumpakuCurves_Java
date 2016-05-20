/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 * 曲線のインターフェイス. Interface of curve.
 * <p>
 * 実数から平面や空間への写像を表す.</p>
 * <p>
 * {@code S}は{@link Space}のサブクラスでなければならない.
 * {@code V}は{@link Vector}のサブクラスでなければならない.</p>
 * <p>
 * Represents mapping to plane or space from real number.<br>
 * <p>
 * {@code S} must be inherited {@link Space}.
 * {@code V} must be inherited {@link Vector}.</p>
 * @author jumpaku
 * @param <S> 座標空間の種類  Type of the space. 
 * @param <V> {@link Curve#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link Curve#evaluate(java.lang.Double)}.
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
