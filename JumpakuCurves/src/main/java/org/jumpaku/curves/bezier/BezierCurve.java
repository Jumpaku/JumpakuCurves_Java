
package org.jumpaku.curves.bezier;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.curves.Curve;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Vec;

/**
 * <p>Bezier曲線のインターフェイス Interface of Bezier Curve.</p>
 * <p>
 * Bezier曲線の様々な演算のインターフェースを定義します.</p> * 
 * <p>
 * Defines interface of operations for Bezier Curve.</p>
 * 
 * @author Jumpaku
 */
public abstract interface BezierCurve extends Curve{
    
    /**
     * <p>Bezier曲線オブジェクトを生成します Creates Bezier Curve.</p>
     * <p>
     * 制御点が1個の場合0次のBezier曲線を生成します.この曲線の評価点は常に制御点と一致します.<br>
     * 制御点が2個の場合1次のBezier曲線を生成します.この曲線の評価点は2つの制御点の内分点となります<br>
     * 制御点が3個または4個の場合それぞれ2次,3次のBezier曲線を生成します.これらの曲線の評価点は展開された式で評価されます.<br>
     * 制御点がn個場合(n&gt;4)n+1次のBezier曲線を生成します.この曲線の評価点は</p>
     * <p>
     * 引数のcontrolPointsはnullであってはいけない.またnullを含んでもいけない.さらに空であってもいけない.</p>
     * <p>
     * The argument controlPoints cannot be null, contain null, and be empty.</p>
     * @param controlPoints 制御点列 control points
     * @return 引数の制御点リストで定義されるBezier曲線. Bezier curve defined given control points
     * @throws IllegalArgumentException controlPointsが{@code null}の時, {@code null}を含んでいる時, または空である時 When controlPoints is {@code null}, contains {@code null}, or is empty.
     */
    public static BezierCurve create(Array<Point> controlPoints, Integer dimention){
        if(controlPoints == null)
            throw new IllegalArgumentException("control points are null");
        
        if(controlPoints.isEmpty())
            throw new IllegalArgumentException("control points are empty");
        
        if(controlPoints.exists(p -> p == null))
            throw new IllegalArgumentException("control points contain null");
        
        if(controlPoints.exists(p -> p.getDimention() != dimention))
            throw new IllegalArgumentException("control points have different dimention");
        
        switch (controlPoints.size()) {
            case 1:
                return new AbstractBezierCurve(controlPoints, dimention) {
                    @Override
                    public Point evaluate(Double t) {
                        return getControlPoints().get(0);
                    }
                };
            case 2:
                return new AbstractBezierCurve(controlPoints, dimention) {
                    @Override
                    public Point evaluate(Double t) {
                        Array<Point> cp = getControlPoints();
                        return cp.get(0).divide(t, cp.get(1));
                    }
                };
            case 3:
                return new AbstractBezierCurve(controlPoints, dimention) {
                    @Override
                    public Point evaluate(Double t) {
                        Array<Vec> cp = getControlPoints().map(p -> p.getVector());
                        return Point.create(cp.get(0).scale((1-t)*(1-t)).add(2*t*(1-t), cp.get(1)).add(t*t, cp.get(2)));
                    }
                };
            case 4:
                return new AbstractBezierCurve(controlPoints, dimention) {
                    @Override
                    public Point evaluate(Double t) {
                        Array<Vec> cp = getControlPoints().map(p -> p.getVector());
                        return Point.create(cp.get(0).scale((1-t)*(1-t)*(1-t)).add(3*t*(1-t)*(1-t), cp.get(1)).add(3*t*t*(1-t), cp.get(2)).add(t*t*t, cp.get(3)));
                    }
                };
            default:
                return new BezierCurveBernstein(controlPoints, dimention);
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

    /**{@inheritDoc }*/
    @Override
    public Integer getDimention();
    
    /**
     * 定義域を取得します Returns domain
     * @return 定義域 domain
     */
    @Override
    Interval getDomain();
    
    /**
     * 不変な制御点リストを返します Returns unmodifiable list of control points.
     * @return 制御点リスト list of control points
     */
    Array<? extends Point> getControlPoints();
    
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
    BezierCurve elevate();
    
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
    BezierCurve reduce();
    
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
    Array<? extends BezierCurve> divide(Double t);
    
    /**
     * 指定された変換を適用したBezier曲線を返す Creates Bezier Curve applied specified transfomation.
     * @param transform 変換 Transform
     * @return 変換されたBezier曲線 transformed Bezier Curve
     */
    //BezierCurve<V> transform(Transform<V> transform);
    
    /**
     * Bezier曲線を反転して返す Creates reversed Bezier Curve.
     * <p>
     * 制御点の並び方を反転させると曲線の形はそのままに, 評価点の軌跡が逆向きに進みます.</p>
     * <p>
     * When order of control points the shape of the curve is not changed, but evaluated point traces reversed path.</p>
     * @return 反転したBezier曲線 Reversed Bezier Curve.
     */
    BezierCurve reverse();
    
    /**
     * Bezier曲線の接線を計算します Computes tangent vector at specified parameter.
     * @param t パラメータ parameter
     * @return 接線ベクトル tangent vector
     */
    Vec computeTangent(Double t);
    
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
    Point evaluate(Double t);
}
