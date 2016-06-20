/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.curves.Curve;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.utils.GeomUtils;

/**
 * <p>
 * Bezier曲線のインターフェイス Interface of Bezier Curve.</p>
 * <p>
 * Bezier曲線の様々な演算のインターフェースを定義します.</p> * 
 * <p>
 * Defines interface of operations for Bezier Curve.</p>
 * 
 * @author Jumpaku
 * @param <S> 座標空間の種類  Type of the space. 
 * @param <V> {@link BezierCurve#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link BezierCurve#evaluate(java.lang.Double)}.
 */
public abstract interface BezierCurve<S extends Space, V extends Vector<S>> extends Curve<S, V>{
    
    /**
     * Bezier曲線オブジェクトを生成する Creates Bezier Curve.
     * <p>
     * 引数のcontrolPointsはnullであってはいけない. またnullを含んでもいけない. さらに空であってもいけない.</p>
     * <p>
     * The argument controlPoints cannot be null, contain null, and be empty.</p>
     * @param <S> 座標空間の種類  Type of the space. 
     * @param <V> {@link Curve#evaluate(java.lang.Double)} の返り値の型. Type of returned value of {@link Curve#evaluate(java.lang.Double)}.
     * @param controlPoints 制御点リスト
     * @return 引数の制御点リストで定義されるBezier曲線. Bezier curve defined given control points.
     */
    public static <S extends Space, V extends Vector<S>> BezierCurve<S, V> create(Array<V> controlPoints){
        if(controlPoints == null)
            throw new IllegalArgumentException("control points are null");
        
        if(controlPoints.isEmpty())
            throw new IllegalArgumentException("control points are empty");
        
        if(controlPoints.exists(p -> p == null))
            throw new IllegalArgumentException("control points contain null");
        
        switch (controlPoints.size()) {
            case 1:
                return new AbstractBezierCurve<S, V>(controlPoints) {
                    @Override
                    public V evaluate(Double t) {
                        return getControlPoints().get(0);
                    }
                };
            case 2:
                return new AbstractBezierCurve<S, V>(controlPoints) {
                    @Override
                    public V evaluate(Double t) {
                        Array<V> cp = getControlPoints();
                        return GeomUtils.internallyDivide(t, cp.get(0), cp.get(1));
                    }
                };
            case 3:
                return new AbstractBezierCurve<S, V>(controlPoints) {
                    @Override
                    public V evaluate(Double t) {
                        Array<V> cp = getControlPoints();
                        return (V) cp.get(0).scalarMultiply((1-t)*(1-t)).add(2*t*(1-t), cp.get(1)).add(t*t, cp.get(2));
                    }
                };
            case 4:
                return new AbstractBezierCurve<S, V>(controlPoints) {
                    @Override
                    public V evaluate(Double t) {
                        Array<V> cp = getControlPoints();
                        return (V) cp.get(0).scalarMultiply((1-t)*(1-t)*(1-t)).add(3*t*(1-t)*(1-t), cp.get(1)).add(3*t*t*(1-t), cp.get(2)).add(t*t*t, cp.get(3));
                    }
                };
            default:
                return new BezierCurveBernstein<>(controlPoints);
        }
    }
    
    /**
     * <p>組み合わせnCiを計算します Computes combinations nCi.</p>
     * <p>
     * 0からnまでのiに対して組み合わせnCiを計算して結果を配列にして返します.</p>
     * <p>
     * For all i in {0, 1, ..., n}, computes combinations nCi, and returns the result as an array.</p> 
     * @param n iの最大値 maximum value of i
     * @return 組み合わせの配列 array of combinations 
     */
    public static Array<Double> combinations(Integer n){
        return Stream.rangeClosed(0, n).map(i -> CombinatoricsUtils.binomialCoefficientDouble(n, i)).toArray();
    }
    
    
    /**
     * 定義域を取得します Returns domain
     * @return 定義域 domain
     */
    Interval getDomain();
    
    /**
     * 不変な制御点リストを返します Returns unmodifiable list of control points.
     * @return 制御点リスト list of control points
     */
    Array<V> getControlPoints();
    
    /**
     * 次数を取得します Returns degree.
     * @return 次数degree
     */
    Integer getDegree();
    
    /**
     * 次数を1つ上げたBezier曲線を生成して返します Creates degree elevated bezier curve.
     * <p>
     * 次数が上がってもBezier曲線は形を変えません.</p>
     * <p>
     * Shape of elevated Bezier Curve is same as original Bezier Cueve.</p> 
     * @return 次数が1高いBezier曲線 Elevated Bezier Curve
     */
    BezierCurve<S, V> elevate();
    
    /**
     * 次数の1つ低いBezier曲線を生成して返します Creates degree reduced bezier curve.
     * <p>
     * 次数が0のBezier曲線に対してこのメソッドを呼んではいけません.</p>
     * <p>
     * 次数の下がったBezier曲線は元のBezier曲線が次数上げされている場合, 形を変えません.
     * 元のBezier曲線が次数上げされていない場合, 近似曲線となります.</p>
     * <p>
     * Don't call this method for 0 degree Bezier Curve.</p>
     * <p>
     * If original Bezier Curve was elevated, shape of reduced Bezier Curve is same as original.
     * Otherwise, reduced Bezier Curve becomes approximate curve of original.</p>
     * @return 次数が1低いBezier曲線, またはその近似曲線. Reduced Bezier curve, or approximate curve of original.
     */
    BezierCurve<S, V> reduce();
    
    /**
     * Bezier曲線を分割して2つのBezier曲線を返します Creates divided 2 Bezier Curves.
     * <p>
     * パラメータt(tは区間[0,1]に含まれる)の地点でBezier曲線を分割し,
     * それら2つを要素とする不変なリストを返します.</p>
     * <p>
     * The parameter t in [0,1] specifies where curve is divided.
     * Returned list contains 2 divided curves are contained and the list unmodifiable.</p>
     * 
     * @param t 分割地点のパラメータ where curve is divided
     * @return 分割された2曲線を要素とするリスト list contains 2 divided curves
     */
    Array<? extends BezierCurve<S, V>> divide(Double t);
    
    /**
     * 指定された変換を適用したBezier曲線を返す Creates Bezier Curve applied specified transfomation.
     * @param transform 変換 Transform
     * @return 変換されたBezier曲線 transformed Bezier Curve
     */
    BezierCurve<S, V> transform(Transform<S, V> transform);
    
    /**
     * Bezier曲線を反転して返す Creates reversed Bezier Curve.
     * <p>
     * 制御点の並び方を反転させると曲線の形はそのままに, 評価点の軌跡が逆向きに進みます.</p>
     * <p>
     * When order of control points the shape of the curve is not changed, but evaluated point traces reversed path.</p>
     * @return 反転したBezier曲線 Reversed Bezier Curve.
     */
    BezierCurve<S, V> reverse();
    
    /**
     * Bezier曲線の接線を計算します Computes tangent vector at specified parameter.
     * @param t パラメータ parameter
     * @return 接線ベクトル tangent vector
     */
    V computeTangent(Double t);
    
    /**
     * Bezier曲線の評価点を計算します Evaluates Bezier Curve point for the parameter.
     * <p>
     * Bezier曲線の定義域は[0,1]なので{@code t}はその中になくてはいけません.</p>
     * <p>
     * The parameter t must be in [0,1] because it is domain of Bezier Curve.</p>
     * @param t [0,1]に含まれるパラメータ parameter in [0,1]
     * @return 評価点 evaluated point
     */
    @Override
    V evaluate(Double t);
}
