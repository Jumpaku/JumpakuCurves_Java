
package org.jumpaku.curves.bezier;

import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Array;
import java.util.function.Function;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Vec;

/**
 * <p>Bezier曲線の基底クラス Basic class of Bezier Curve.</p>
 * <p>
 * {@link AbstractBezierCurve#evaluate(java.lang.Double) }をオーバーライドしてください.</p>
 * <p>
 * Override {@link AbstractBezierCurve#evaluate(java.lang.Double) }.</p>
 * 
 * @author Jumpaku
 */
public abstract class AbstractBezierCurve implements BezierCurve{
    
    private final Array<Point> controlPoints;
    
    private final Integer dimention;
    
    private static final Closed DOMAIN = new Closed(0.0, 1.0);
    
    /**
     * <p>指定された制御点からBezie曲線を構築します Constructs Bezier Curve with specified control points.</p>
     * <p>
     * 制御点列をベクトルのArrayとして制御点を渡します.<br>
     * 制御点列は{@code null}であってはいけません.<br>
     * また{@code null}を含んだり, 空であってもいけません.</p>
     * <p>
     * Give control points as Array of vector<br>
     * controlPoints munt be not {@code null}, not contain {@code null}, not be empty.</p>
     * @param controlPoints 制御点 control points
     * @param dimention
     * @throws IllegalArgumentException controlPointsが{@code null}の時, {@code null}を含んでいる時, または空である時 When controlPoints is {@code null}, contains {@code null}, or is empty.
     */
    public AbstractBezierCurve(Array<Point> controlPoints, Integer dimention) {
        if(controlPoints.isEmpty())
            throw new IllegalArgumentException("Control points is empty.");
        
        if(controlPoints.exists(p -> p == null))
            throw new IllegalArgumentException("Control points contains null.");

        if(controlPoints.exists(p -> p.getDimention() != dimention))
            throw new IllegalArgumentException("control points have different dimention");

        this.controlPoints = controlPoints;
        this.dimention = dimention;
    }
        
    private static Array<Point> createElevatedControlPonts(Array<Point> controlPoints){
        Integer n = controlPoints.size() - 1;
        List<Point> result = new LinkedList<>();
        result.add(controlPoints.get(0));        
        for(int i = 1; i <= n; ++i){
            Double a = i/(double)(n+1);
            Double b = 1-a;
            result.add(Point.create(Vec.add(b, controlPoints.get(i).getVec(), a, controlPoints.get(i-1).getVec())));
        }
        result.add(controlPoints.get(n));
        return Array.ofAll(result);
    }
    
    private static Array<Point> createReducedControlPonts(Array<Point> originalcps){
        Integer n = originalcps.size() - 1;
        Integer m = n + 1;
        if(m < 2)
            throw new IllegalArgumentException("degree is too small");
            
        Point[] cps = new Point[n];
        if(m == 2){
            cps[0] = originalcps.get(0).divide(0.5, originalcps.get(1));
        }
        else if(m%2==0){
            Integer r = (m-2)/2;
            cps[0] = originalcps.get(0);
            for(int i = 1; i <= r-1; ++i){
                Double a = i/(m-1.0);
                cps[i] = Point.create(originalcps.get(i).getVec().add(-a, cps[i-1].getVec()).scale(1.0/(1.0-a)));
            }
            cps[n-1] = originalcps.get(n);
            for(int i = m-3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = Point.create(originalcps.get(i+1).getVec().add(-1.0+a, cps[i+1].getVec()).scale(1.0/a));
            }
            Double al = r/(m-1.0);
            Double ar = (r+1)/(m-1.0);
            Point left = Point.create(originalcps.get(r).getVec().add(-al, cps[r-1].getVec()).scale(1.0/(1.0-al)));
            Point right = Point.create(originalcps.get(r+1).getVec().add(-1.0+ar, cps[r+1].getVec()).scale(1.0/ar));
            cps[r] = Point.create(left.getVec().add(right.getVec()).scale(0.5));
        }
        else{
            cps[0] = originalcps.get(0);
            Integer r = (m-3)/2;
            for(int i = 1; i <= r; ++i){
                Double a = i/(m-1.0);
                cps[i] = Point.create(originalcps.get(i).getVec().add(-a, cps[i-1].getVec()).scale(1.0/(1.0-a)));
            }
            cps[n-1] = originalcps.get(n);
            for(int i = m - 3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = Point.create(originalcps.get(i+1).getVec().add(-1.0+a, cps[i+1].getVec()).scale(1.0/a));
            }
        }
        
        return Array.of(cps);//.collect(Collectors.toList());
    }
    
    private static class DegreeElevated extends AbstractBezierCurve{
        private final Integer elevated;
        private final AbstractBezierCurve reduced;
        public DegreeElevated(Integer elevated, Array<Point> cps, AbstractBezierCurve reduced) {
            super(cps, cps.head().getDimention());
            this.elevated = elevated;
            this.reduced = reduced;
        }        
        
        @Override
        public Point evaluate(Double t) {
            return reduced.evaluate(t);
        }
        
        @Override
        public final BezierCurve elevate(){
            return new DegreeElevated(elevated+1, createElevatedControlPonts(getControlPoints()), this);
        }
        
        @Override
        public final BezierCurve reduce(){
            return elevated >= 1 ? reduced : BezierCurve.create(createReducedControlPonts(getControlPoints()), getDimention());
        }
    }
    
    private static Array<Array<Point>> createDividedControlPoints(Object[] cp, Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("The parameter t is out of domain [0,1], t = " + t);
        
        LinkedList<Point> first = new LinkedList<>();
        LinkedList<Point> second = new LinkedList<>();
        int n = cp.length - 1;
        first.addLast((Point)cp[0]);
        second.addFirst((Point)cp[n]);
        while(n > 0){
            for(int i = 0; i < n; ++i){
                cp[i] = Point.create(((Point)cp[i]).getVec().scale(1-t).add(t, ((Point)cp[i+1]).getVec()));
            }
            first.addLast((Point)cp[0]);
            second.addFirst((Point)cp[--n]);
        }
        
        return Array.of(Array.ofAll(first), Array.ofAll(second));
    }
    
    private static Array<BezierCurve> divide(Double t, Array<Point> controlPoints, Function<Double, Point> evaluator){
        Array<Array<Point>> cps = createDividedControlPoints(controlPoints.toJavaArray(), t);
        return Array.of(new AbstractBezierCurve(cps.head(), cps.head().head().getDimention()){
            @Override public Point evaluate(Double s) {
                return evaluator.apply(s*t);
            }            
        },
        new AbstractBezierCurve(cps.get(1), cps.head().head().getDimention()){
            @Override public Point evaluate(Double s) {
                return evaluator.apply(t + s*(1-t));
            }
        });
    }
    
    /*private static <V extends Vec> BezierCurve<V> transform(Transform<V> transform, Array<V> controlPoints, Function<Double, V> evaluator){
        Array<V> transformedControlPoints = controlPoints
                .map(cp -> transform.apply(cp));
                //collect(Collectors.toList());
        return new AbstractBezierCurve<V>(transformedControlPoints) {
            @Override
            public V evaluate(Double t) {
                return transform.apply(evaluator.apply(t));
            }
        };
    }*/
    
    private static BezierCurve reverse(Array<Point> controlPoints, Function<Double, Point> evaluator){
        return new AbstractBezierCurve(controlPoints.reverse(), controlPoints.head().getDimention()) {
            @Override
            public Point evaluate(Double t) {
                return evaluator.apply(1-t);
            }
        };
    }
    
    /**{@inheritDoc }*/
    @Override    
    public Integer getDimention(){
        return dimention;
    }

    /**{@inheritDoc}*/
    @Override
    public final Closed getDomain() {
        return DOMAIN;
    }
    
    /**{@inheritDoc}*/
    @Override
    public final Array<Point> getControlPoints() {
        return controlPoints;
    }
    
    /**{@inheritDoc}*/
    @Override
    public final Integer getDegree(){
        return controlPoints.size() - 1;
    }

    /**{@inheritDoc}*/
    @Override
    public BezierCurve elevate(){
        return new DegreeElevated(1, createElevatedControlPonts(getControlPoints()), this);
    }
    
    /**{@inheritDoc}*/
    @Override
    public BezierCurve reduce(){
        if(getDegree() < 1)
            throw new IllegalArgumentException("degree is too small");
        
        return BezierCurve.create(createReducedControlPonts(getControlPoints()), getDimention());
    }
    
    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException パラメータtが[0,1]に含まれない時 When t is not in [0,1]
     */
    @Override
    public final Array<BezierCurve> divide(Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("The parameter t is out of domain [0,1], t = " + t);
        
        return divide(t, getControlPoints(), this::evaluate);
    }
    
    /**{@inheritDoc}*/
    /*@Override
    public final BezierCurve<V> transform(Transform<V> transform){
        return transform(transform, getControlPoints(), this::evaluate);
    }*/
    
    /**{@inheritDoc}*/
    @Override
    public final BezierCurve reverse(){
        return reverse(getControlPoints(), this::evaluate);
    }
    
    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException tが[0,1]に含まれていない時 When t is not in [0,1]
     * @throws IllegalStateException 次数が0の時 When degree is 0
     */
    @Override
    public final Vec computeTangent(Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("t must be in [0, 1]");
        
        Integer n = getDegree();
        if(n == 0)
            throw new IllegalStateException("degree is 0");
        
        Array<Point> cp = getControlPoints();
        Array<Double> combinations = BezierCurve.combinations(n-1);
        Vec result = Vec.zero(cp.get(0).getDimention());
        
        Double c = Math.pow(1.0-t, n-1);
        for(int i = 0; i < n; ++i){
            Vec delta = (cp.get(i+1).getVec().sub(cp.get(i).getVec())).scale(combinations.get(i) * c);
            c *= t/(1.0-t);
            result = result.add(delta);
        }
        
        return result.scale(n.doubleValue());
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Bezier曲線の評価処理を実装してください.</p>
     * <p>
     * Implement evaluation execution of Bezier Curve.</p>
     * @param t [0,1]に含まれるパラメータ parameter in [0,1]
     * @return 評価点 evaluated point
     */
    @Override
    public abstract Point evaluate(Double t);
}
