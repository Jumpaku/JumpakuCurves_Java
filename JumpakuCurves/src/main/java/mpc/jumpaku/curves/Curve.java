/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves;

import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 * 曲線のインターフェイス Interface of curve.
 * 実数から平面や空間への写像を表し, {@link Curve<V>#evaluate(java.lang.Double)  }はパラメータtに対応する評価点のベクトルを返す.
 * @author ito
 * @param <V> {@link Curve<V>#evaluate(java.lang.Double) の返り値の型. {@link Vector}のサブクラスでなければならない.
 */
@FunctionalInterface
public interface Curve<S extends Space>{
    Vector<S> evaluate(Double t);
}
