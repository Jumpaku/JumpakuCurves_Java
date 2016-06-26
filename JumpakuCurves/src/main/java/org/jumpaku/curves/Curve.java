
package org.jumpaku.curves;

import org.jumpaku.curves.vector.Vec;

/**
 * <p>曲線のインターフェイス Interface of curve.</p>
 * <p>
 * 実数から平面や空間への写像を表します.</p>
 * <p>
 * {@code V}は{@link Vec}のサブクラスでなければならなりません.</p>
 * <p>
 * Represents mapping to plane or space from real number.</p>
 * <p>
 * {@code V} must be inherited {@link Vec}.</p>
 * 
 * @author jumpaku
 * @param <V> {@link Curve#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link Curve#evaluate(java.lang.Double)}.
 */
@FunctionalInterface
public interface Curve<V extends Vec>{
    /**
     * <p>パラメータtに対応する評価点のベクトルを返す.</p>
     * @param t パラメータ parameter
     * @return 評価点 Evaluated point
     */
    V evaluate(Double t);
}
