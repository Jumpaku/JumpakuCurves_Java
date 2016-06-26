
package org.jumpaku.curves.bezier;

import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Array;
import java.util.function.Function;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.Vector;
import org.jumpaku.curves.transform.Transform;
import org.apache.commons.math3.geometry.Space;
import org.jumpaku.curves.vector.Vec;

/**
 * <p>Bezier曲線の基底クラス Basic class of Bezier Curve.</p>
 * <p>
 * {@link AbstractBezierCurve#evaluate(java.lang.Double) }をオーバーライドしてください.</p>
 * <p>
 * Override {@link AbstractBezierCurve#evaluate(java.lang.Double) }.</p>
 * 
 * @author Jumpaku
 * @param <V> ベクトルの型 Type of vector
 */
public abstract class AbstractBezierCurve<V extends Vec> implements BezierCurve<V>{
    
    private final Array<V> controlPoints;
    
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
     * @throws IllegalArgumentException controlPointsが{@code null}の時, {@code null}を含んでいる時, または空である時 When controlPoints is {@code null}, contains {@code null}, or is empty.
     */
    public AbstractBezierCurve(Array<V> controlPoints) {
        if(controlPoints.isEmpty())
            throw new IllegalArgumentException("Control points is empty.");
        
        if(controlPoints.exists(p -> p == null))
            throw new IllegalArgumentException("Control points contains null.");

        this.controlPoints = controlPoints;
    }
        
    private static <V extends Vec> Array<V> createElevatedControlPonts(Array<? extends V> controlPoints){
        Integer n = controlPoints.size() - 1;
        List<V> result = new LinkedList<>();
        result.add(controlPoints.get(0));        
        for(int i = 1; i <= n; ++i){
            Double a = i/(double)(n+1);
            Double b = 1-a;
            result.add((V)Vec.add(b, controlPoints.get(i), a, controlPoints.get(i-1)));
        }
        result.add(controlPoints.get(n));
        return Array.ofAll(result);
    }
    
    private static <V extends Vec> Array<V> createReducedControlPonts(Array<? extends V> originalcps){
        Integer n = originalcps.size() - 1;
        Integer m = n + 1;
        if(m < 2)
            throw new IllegalArgumentException("degree is too small");
            
        Object[] cps = new Object[n];
        if(m == 2){
            cps[0] = originalcps.get(0).add(originalcps.get(1)).scale(0.5);
        }
        else if(m%2==0){
            Integer r = (m-2)/2;
            cps[0] = originalcps.get(0);
            for(int i = 1; i <= r-1; ++i){
                Double a = i/(m-1.0);
                cps[i] = originalcps.get(i).add(-a, (Vec)cps[i-1]).scale(1.0/(1.0-a));
            }
            cps[n-1] = originalcps.get(n);
            for(int i = m-3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = originalcps.get(i+1).add(-1.0+a, (Vec)cps[i+1]).scale(1.0/a);
            }
            Double al = r/(m-1.0);
            Double ar = (r+1)/(m-1.0);
            Vec left = originalcps.get(r).add(-al, (Vec)cps[r-1]).scale(1.0/(1.0-al));
            Vec right = originalcps.get(r+1).add(-1.0+ar, (Vec)cps[r+1]).scale(1.0/ar);
            cps[r] = left.add(right).scale(0.5);
        }
        else{
            cps[0] = originalcps.get(0);
            Integer r = (m-3)/2;
            for(int i = 1; i <= r; ++i){
                Double a = i/(m-1.0);
                cps[i] = originalcps.get(i).add(-a, (Vec)cps[i-1]).scale(1.0/(1.0-a));
            }
            cps[n-1] = originalcps.get(n);
            for(int i = m - 3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = originalcps.get(i+1).add(-1.0+a, (Vec)cps[i+1]).scale(1.0/a);
            }
        }
        
        return Array.of(cps).map(o -> (V)o);//.collect(Collectors.toList());
    }
    
    private static class DegreeElevated<V extends Vec> extends AbstractBezierCurve<V>{
        private final Integer elevated;
        private final AbstractBezierCurve<V> reduced;
        public DegreeElevated(Integer elevated, Array<V> cps, AbstractBezierCurve<V> reduced) {
            super(cps);
            this.elevated = elevated;
            this.reduced = reduced;
        }        
        
        @Override
        public V evaluate(Double t) {
            return reduced.evaluate(t);
        }
        
        @Override
        public final BezierCurve<V> elevate(){
            return new DegreeElevated<>(elevated+1, createElevatedControlPonts(getControlPoints()), this);
        }
        
        @Override
        public final BezierCurve<V> reduce(){
            return elevated >= 1 ? reduced : BezierCurve.create(createReducedControlPonts(getControlPoints()));
        }
    }
    
    private static <V extends Vec> Array<Array<V>> createDividedControlPoints(Object[] cp, Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("The parameter t is out of domain [0,1], t = " + t);
        
        LinkedList<V> first = new LinkedList<>();
        LinkedList<V> second = new LinkedList<>();
        int n = cp.length - 1;
        first.addLast((V)cp[0]);
        second.addFirst((V)cp[n]);
        while(n > 0){
            for(int i = 0; i < n; ++i){
                cp[i] = ((Vec)cp[i]).scale(1-t).add(t, ((Vec)cp[i+1]));
            }
            first.addLast((V)cp[0]);
            second.addFirst((V)cp[--n]);
        }
        
        return Array.of(Array.ofAll(first), Array.ofAll(second));
    }
    
    private static <V extends Vec> Array<BezierCurve<V>> divide(Double t, Array<V> controlPoints, Function<Double, V> evaluator){
        Array<Array<V>> cps = createDividedControlPoints(controlPoints.toJavaArray(), t);
        return Array.of(new AbstractBezierCurve<V>(cps.get(0)){
            @Override public V evaluate(Double s) {
                return evaluator.apply(s*t);
            }            
        },
        new AbstractBezierCurve<V>(cps.get(1)){
            @Override public V evaluate(Double s) {
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
    
    private static <V extends Vec> BezierCurve<V> reverse(Array<V> controlPoints, Function<Double, V> evaluator){
        return new AbstractBezierCurve<V>(controlPoints.reverse()) {
            @Override
            public V evaluate(Double t) {
                return evaluator.apply(1-t);
            }
        };
    }
    
    /**{@inheritDoc}*/
    @Override
    public final Closed getDomain() {
        return DOMAIN;
    }
    
    /**{@inheritDoc}*/
    @Override
    public final Array<V> getControlPoints() {
        return controlPoints;
    }
    
    /**{@inheritDoc}*/
    @Override
    public final Integer getDegree(){
        return controlPoints.size() - 1;
    }

    /**{@inheritDoc}*/
    @Override
    public BezierCurve<V> elevate(){
        return new DegreeElevated<>(1, createElevatedControlPonts(getControlPoints()), this);
    }
    
    /**{@inheritDoc}*/
    @Override
    public BezierCurve<V> reduce(){
        if(getDegree() < 1)
            throw new IllegalArgumentException("degree is too small");
        
        return BezierCurve.create(createReducedControlPonts(getControlPoints()));
    }
    
    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException パラメータtが[0,1]に含まれない時 When t is not in [0,1]
     */
    @Override
    public final Array<BezierCurve<V>> divide(Double t){
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
    public final BezierCurve<V> reverse(){
        return reverse(getControlPoints(), this::evaluate);
    }
    
    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException tが[0,1]に含まれていない時 When t is not in [0,1]
     * @throws IllegalStateException 次数が0の時 When degree is 0
     */
    @Override
    public final V computeTangent(Double t){
        if(!DOMAIN.isIn(t))
            throw new IllegalArgumentException("t must be in [0, 1]");
        
        Integer n = getDegree();
        if(n == 0)
            throw new IllegalStateException("degree is 0");
        
        Array<V> cp = getControlPoints();
        Array<Double> combinations = BezierCurve.combinations(n-1);
        Vec result = Vec.zero(cp.get(0).getDimention());
        
        Double c = Math.pow(1.0-t, n-1);
        for(int i = 0; i < n; ++i){
            Vec delta = (cp.get(i+1).sub(cp.get(i))).scale(combinations.get(i) * c);
            c *= t/(1.0-t);
            result = result.add(delta);
        }
        
        return (V)result.scale(n.doubleValue());
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
    public abstract V evaluate(Double t);
}
