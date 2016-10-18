
package org.jumpaku.curves.bezier;

import java.util.LinkedList;
import java.util.List;
import javaslang.collection.Array;
import org.jumpaku.curves.domain.Closed;
import org.jumpaku.curves.vector.Point;
import org.jumpaku.curves.vector.Vec;

/**
 * <p>Bezier曲線の基底クラス Basic class of Bezier Curve.</p>
 * <p>
 * {@link AbstractBezier#evaluate(java.lang.Double) }をオーバーライドしてください.</p>
 * <p>
 * Override {@link AbstractBezier#evaluate(java.lang.Double) }.</p>
 * 
 * @author Jumpaku
 */
public abstract class AbstractBezier implements Bezier{
    
    private final Array<Point> controlPoints;
    
    private final Integer dimention;
    
    /**
     * <p>指定された制御点からBezie曲線を構築します Constructs Bezier Curve with specified control points.</p>
     * <p>
     * 制御点列をベクトルのArrayとして制御点を渡します.<br>
     * 制御点列は{@code null}であってはいけません.<br>
     * また{@code null}を含んだり, 空であってもいけません.</p>
     * <p>
     * Give control points as Array of vector<br>
     * controlPoints munt be not {@code null}, not contain {@code null}, not be empty.</p>
     * @param controlPoints 制御点列 control points
     * @param dimention 空間の次元 dimention of space
     * @throws IllegalArgumentException controlPointsが{@code null}の時, {@code null}を含んでいる時, または空である時 When controlPoints is {@code null}, contains {@code null}, or is empty.
     */
    public AbstractBezier(Iterable<? extends Point> controlPoints, Integer dimention) {
        Array<? extends Point> cps = Array.ofAll(controlPoints);
        if(cps == null)
            throw new IllegalArgumentException("control points are null");
                
        if(cps.isEmpty())
            throw new IllegalArgumentException("Control points is empty.");
        
        if(cps.exists(p -> p == null))
            throw new IllegalArgumentException("Control points contains null.");

        if(cps.exists(p -> p.getDimention() != dimention))
            throw new IllegalArgumentException("control points dimention is wrong");

        this.controlPoints = cps.map(cp -> cp);
        this.dimention = dimention;
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
    public Bezier elevate(){
        return Bezier.create(createElevatedControlPoints(), getDimention());
    }
    
    private Array<? extends Point> createElevatedControlPoints() {
        Integer n = getControlPoints().size() - 1;
        List<Point> result = new LinkedList<>();
        result.add(getControlPoints().get(0));        
        for(int i = 1; i <= n; ++i){
            result.add(getControlPoints().get(i).divide(i/(double)(n+1), getControlPoints().get(i-1)));
        }
        result.add(getControlPoints().get(n));
        return Array.ofAll(result);
    }
    
    /**{@inheritDoc}*/
    @Override
    public Bezier reduce(){
        if(getDegree() < 1)
            throw new IllegalArgumentException("degree is too small");
        
        return Bezier.create(createReducedControlPoints(), getDimention());
    }
    
    private Array<? extends Point> createReducedControlPoints() {
        Integer n = getControlPoints().size() - 1;
        Integer m = n + 1;
        if(m < 2)
            throw new IllegalArgumentException("degree is too small");
            
        Point[] cps = new Point[n];
        if(m == 2){
            cps[0] = getControlPoints().get(0).divide(0.5, getControlPoints().get(1));
        }
        else if(m%2==0){
            Integer r = (m-2)/2;
            cps[0] = getControlPoints().get(0);
            for(int i = 1; i <= r-1; ++i){
                Double a = i/(m-1.0);
                cps[i] = getControlPoints().get(i).divide(-a/(1.0-a), cps[i-1]);
            }
            cps[n-1] = getControlPoints().get(n);
            for(int i = m-3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = getControlPoints().get(i+1).divide((-1.0+a)/a, cps[i+1]);
            }
            Double al = r/(m-1.0);
            Point left = getControlPoints().get(r).divide(-al/(1.0-al), cps[r-1]);
            Double ar = (r+1)/(m-1.0);
            Point right = getControlPoints().get(r+1).divide((-1.0+ar)/ar, cps[r+1]);
            cps[r] = left.divide(0.5, right);
        }
        else{
            cps[0] = getControlPoints().get(0);
            Integer r = (m-3)/2;
            for(int i = 1; i <= r; ++i){
                Double a = i/(m-1.0);
                cps[i] = getControlPoints().get(i).divide(-a/(1.0-a), cps[i-1]);
            }
            cps[n-1] = getControlPoints().get(n);
            for(int i = m - 3; i >= r+1; --i){
                Double a = (i+1.0)/(m-1.0);
                cps[i] = getControlPoints().get(i+1).divide((-1.0+a)/a, cps[i+1]);
            }
        }
        
        return Array.of(cps);
    }

    /**{@inheritDoc}*/
    @Override
    public final Array<Bezier> subdivide(Double t){
        Array<Array<? extends Point>> cpsArray = createDividedControlPointsArray(t);
        return Array.of(Bezier.create(cpsArray.get(0), getDimention()),
                Bezier.create(cpsArray.get(1), getDimention()));
    }
    
    private Array<Array<? extends Point>> createDividedControlPointsArray(Double t) {
        Point[] cp = getControlPoints().toJavaArray(Point.class);
        LinkedList<Point> first = new LinkedList<>();
        LinkedList<Point> second = new LinkedList<>();
        int n = cp.length - 1;
        first.addLast(cp[0]);
        second.addFirst(cp[n]);
        while(n > 0){
            for(int i = 0; i < n; ++i){
                cp[i] = cp[i].divide(t, cp[i+1]);
            }
            first.addLast(cp[0]);
            second.addFirst(cp[--n]);
        }
        
        return Array.of(Array.ofAll(first), Array.ofAll(second));
    }
    
    /**{@inheritDoc}*/
    @Override
    public final Bezier reverse(){
        return Bezier.create(getControlPoints().reverse(), getDimention());
    }
    
    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException tが[0,1]に含まれていない時 When t is not in [0,1]
     * @throws IllegalStateException 次数が0の時 When degree is 0
     */
    @Override
    public final Vec computeTangent(Double t){
        if(!DOMAIN.contains(t))
            throw new IllegalArgumentException("t must be in [0, 1]");
        
        return differentiate().evaluate(t).getVec();
    }

    @Override
    public Bezier differentiate() {
        return Bezier.create(createControlPointsDifferences().map(Point::of), getDimention());
    }
    
    private Array<? extends Vec> createControlPointsDifferences(){
        return getControlPoints().tail().zip(getControlPoints())
                .map(pair -> pair._1().from(pair._2()));
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
