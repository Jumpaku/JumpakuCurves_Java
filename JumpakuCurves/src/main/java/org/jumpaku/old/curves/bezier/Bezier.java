
package org.jumpaku.old.curves.bezier;

import javaslang.collection.Array;
import javaslang.collection.Stream;
import org.jumpaku.old.curves.Curve;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.domain.Interval;
import org.jumpaku.old.curves.vector.Point;
import org.jumpaku.old.curves.vector.Point1D;
import org.jumpaku.old.curves.vector.Point2D;
import org.jumpaku.old.curves.vector.Point3D;
import org.jumpaku.old.curves.vector.Vec;

/**
 * <p>Bezier曲線のインターフェイス Interface of Bezier Curve.</p>
 * <p>
 * Bezier曲線の様々な演算のインターフェースを定義します.</p> * 
 * <p>
 * Defines interface of operations for Bezier Curve.</p>
 * 
 * @author Jumpaku
 */
public abstract interface Bezier extends Curve{
    
    /**
     * <p>2次Bezier曲線 Quadratic Bezier Curve.</p>
     */
    public static class Quadratic extends AbstractBezier{

        /**
         * <p>2次Bezier曲線オブジェクトを構築します Constructs Quadratic Bezier Curve.</p>
         * <p>
         * controlPointsの要素数は3でなければいけません.</p>
         * <p>
         * Size of controlPoints must be 3.</p>
         * @param controlPoints 要素数3の制御点列 control points of 3 points
         * @param dimention 次元 dimention
         */
        public Quadratic(Array<? extends Point> controlPoints, Integer dimention) {
            super(controlPoints, dimention);
        }

        /** {@inheritDoc } */
        @Override
        public final Point evaluate(Double t) {
            Array<Vec> cp = getControlPoints().map(p -> p.getVec());
            return Point.of(cp.get(0).scale((1-t)*(1-t)).add(2*t*(1-t), cp.get(1)).add(t*t, cp.get(2)));
        }
    }
    
    /**
     * <p>3次Bezier曲線 Cubic Bezier Curve.</p>
     */
    public static class Cubic extends AbstractBezier{

        /**
         * <p>2次Bezier曲線オブジェクトを構築します Constructs Quadratic Bezier Curve.</p>
         * <p>
         * controlPointsの要素数は4でなければいけません.</p>
         * <p>
         * Size of controlPoints must be 4.</p>
         * @param controlPoints 要素数4の制御点列 control points of 4 points
         * @param dimention 次元 dimention
         */
        public Cubic(Array<? extends Point> controlPoints, Integer dimention) {
            super(controlPoints, dimention);
        }

        /** {@inheritDoc } */
        @Override
        final public Point evaluate(Double t) {
            Array<Vec> cp = getControlPoints().map(p -> p.getVec());
            return Point.of(cp.get(0).scale((1-t)*(1-t)*(1-t)).add(3*t*(1-t)*(1-t), cp.get(1)).add(3*t*t*(1-t), cp.get(2)).add(t*t*t, cp.get(3)));
        }
    }
    
    /**
     * <p>Bezier曲線の定義域 Domain of Bezier Curve.</p>
     */
    static Closed DOMAIN = new Closed(0.0, 1.0);
    
    /**
     * <p>Bezier曲線オブジェクトを生成します Creates Bezier Curve.</p>
     * <p>
     * 制御点が1個の場合0次のBezier曲線を生成します.この曲線の評価点は常に制御点と一致します.<br>
     * 制御点が2個の場合1次のBezier曲線を生成します.この曲線の評価点は2つの制御点の内分点となります.<br>
     * 制御点が3個または4個の場合それぞれ2次,3次のBezier曲線を生成します.これらの曲線の評価点は展開された式で評価されます.<br>
     * 制御点がn個(n&gt;4)場合n-1次のBezier曲線を生成します.この曲線の評価点はbernstein多項式で評価されます.</p>
     * <p>
     * 引数のcontrolPointsはnullであってはいけない.またnullを含んでもいけない.さらに空であってもいけない.</p>
     * <p>
     * The argument controlPoints cannot be null, contain null, and be empty.</p>
     * @param controlPoints 制御点列 control points
     * @param dimention
     * @return 引数の制御点リストで定義されるBezier曲線. Bezier curve defined given control points
     * @throws IllegalArgumentException controlPointsが{@code null}の時, {@code null}を含んでいる時, または空である時 When controlPoints is {@code null}, contains {@code null}, or is empty.
     */
    public static Bezier create(Array<? extends Point> controlPoints, Integer dimention){
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
                return new AbstractBezier(controlPoints, dimention) {
                    @Override
                    public Point evaluate(Double t) {
                        return getControlPoints().get(0);
                    }
                };
            case 2:
                return new AbstractBezier(controlPoints, dimention) {
                    @Override
                    public Point evaluate(Double t) {
                        Array<Point> cp = getControlPoints();
                        return cp.get(0).divide(t, cp.get(1));
                    }
                };
            case 3:
                return new Quadratic(controlPoints, dimention);
            case 4:
                return new Cubic(controlPoints, dimention);
            default:
                return new BezierBernstein(controlPoints, dimention);
        }
    }

    /**
     * <p>Bezier曲線オブジェクトを生成します Creates Bezier Curve.</p>
     * <p>
     * 制御点がn個(n&gt;4)場合n-1次のBezier曲線を生成します.この曲線の評価点はDe Casteljauのアルゴリズムで評価されます.</p>
     * <p>
     * 引数のcontrolPointsはnullであってはいけない.またnullを含んでもいけない.さらに空であってもいけない.</p>
     * <p>
     * The argument controlPoints cannot be null, contain null, and be empty.</p>
     * @param controlPoints 制御点列 control points
     * @param dimention
     * @return 引数の制御点リストで定義されるBezier曲線. Bezier curve defined given control points
     * @throws IllegalArgumentException controlPointsが{@code null}の時, {@code null}を含んでいる時, または空である時 When controlPoints is {@code null}, contains {@code null}, or is empty.
      */
    public static Bezier decasteljau(Array<? extends Point> controlPoints, Integer dimention){
        return new BezierDeCasteljau(controlPoints, dimention);
    }
    
    /**
     * <p>数直線上の2次Bezier曲線を生成します Creates Quadratic Bezier Curve on number line.</p>
     * @param p0 1つ目の制御点 first control point
     * @param p1 2つ目の制御点 second control point
     * @param p2 3つ目の制御点 third control point
     * @return 数直線上のBezier曲線 Bezier Curve on number line
     */
    public static Bezier1D quadratic1D(Point1D p0, Point1D p1, Point1D p2){
        return Bezier1D.create(Array.of(p0, p1, p2));
    }
    
    /**
     * <p>平面上の2次Bezier曲線を生成します Creates Quadratic Bezier Curve on plane.</p>
     * @param p0 1つ目の制御点 first control point
     * @param p1 2つ目の制御点 second control point
     * @param p2 3つ目の制御点 third control point
     * @return 平面上のBezier曲線 Bezier Curve on plane
     */
    public static Bezier2D quadratic2D(Point2D p0, Point2D p1, Point2D p2){
        return Bezier2D.create(Array.of(p0, p1, p2));
    }
    
    /**
     * <p>空間上の2次Bezier曲線を生成します Creates Quadratic Bezier Curve on space.</p>
     * @param p0 1つ目の制御点 first control point
     * @param p1 2つ目の制御点 second control point
     * @param p2 3つ目の制御点 third control point
     * @return 空間上のBezier曲線 Bezier Curve on space
     */
    public static Bezier3D quadratic3D(Point3D p0, Point3D p1, Point3D p2){
        return Bezier3D.create(Array.of(p0, p1, p2));
    }
    
    /**
     * <p>数直線上の3次Bezier曲線を生成します Creates Cubic Bezier Curve on number line.</p>
     * @param p0 1つ目の制御点 first control point
     * @param p1 2つ目の制御点 second control point
     * @param p2 3つ目の制御点 third control point
     * @param p3 4つ目の制御点 fourth control point
     * @return 数直線上のBezier曲線 Bezier Curve on number line
     */
    public static Bezier1D cubic1D(Point1D p0, Point1D p1, Point1D p2, Point1D p3){
        return Bezier1D.create(Array.of(p0, p1, p2, p3));
    }

    /**
     * <p>平面上の3次Bezier曲線を生成します Creates Cubic Bezier Curve on plane.</p>
     * @param p0 1つ目の制御点 first control point
     * @param p1 2つ目の制御点 second control point
     * @param p2 3つ目の制御点 third control point
     * @param p3 4つ目の制御点 fourth control point
     * @return 平面上のBezier曲線 Bezier Curve on plane
     */
    public static Bezier2D cubic2D(Point2D p0, Point2D p1, Point2D p2, Point2D p3){
        return Bezier2D.create(Array.of(p0, p1, p2, p3));
    }

    /**
     * <p>空間上の3次Bezier曲線を生成します Creates Cubic Bezier Curve on space.</p>
     * @param p0 1つ目の制御点 first control point
     * @param p1 2つ目の制御点 second control point
     * @param p2 3つ目の制御点 third control point
     * @param p3 4つ目の制御点 fourth control point
     * @return 空間上のBezier曲線 Bezier Curve on space
     */
    public static Bezier3D cubic3D(Point3D p0, Point3D p1, Point3D p2, Point3D p3){
        return Bezier3D.create(Array.of(p0, p1, p2, p3));
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
    default Interval getDomain(){
        return DOMAIN;
    }
    
    /**
     * 不変な制御点リストを返します Returns unmodifiable list of control points.
     * @return 制御点リスト list of control points
     */
    Array<? extends Point> getControlPoints();
    
    /**
     * 次数を取得します Returns degree.
     * @return 次数degree
     */
    default Integer getDegree(){
        return getControlPoints().size() - 1;
    }
    
    /**
     * 次数を1つ上げたBezier曲線を生成して返します Creates degree elevated bezier curve.
     * <p>
     * 次数が上がってもBezier曲線は形を変えません.</p>
     * <p>
     * Shape of elevated Bezier Curve is same as original Bezier Cueve.</p> 
     * @return 次数が1高いBezier曲線 Elevated Bezier Curve
     */
    Bezier elevate();
    
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
    Bezier reduce();
    
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
    Array<? extends Bezier> subdivide(Double t);
    
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
    Bezier reverse();
    
    /**
     * Bezier曲線の接線を計算します Computes tangent vector at specified parameter.
     * @param t パラメータ parameter
     * @return 接線ベクトル tangent vector
     */
    Vec computeTangent(Double t);
    
    /**
     * <p>Bezier曲線を微分します differentiate Bezier Curve.</p>
     * @return Bezier曲線の導関数 derivative of Bezier Curve 
     */
    Bezier differentiate();
    
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
