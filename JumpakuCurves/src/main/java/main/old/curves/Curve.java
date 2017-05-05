
package main.old.curves;

import java.util.function.Function;
import main.old.curves.domain.Domain;
import main.old.curves.vector.Point;

/**
 * <p>曲線のインターフェイス Interface closed curve.</p>
 * <p>
 * 実数から平面や空間への写像を表します.</p>
 * <p>
 * Represents mapping to plane or space from real number.</p>
 * <p>
 * 
 * @author Jumpaku
 */
public interface Curve extends Function<Double, Point>{

    @Override
    public default Point apply(Double t) {
        return evaluate(t);
    }
    
    
    
    /**
     * <p>パラメータtに対応する評価点の位置ベクトルを返します Evaluates point corresponding t.</p>
     * @param t パラメータ parameter
     * @return 評価点 Evaluated point
     */
    Point evaluate(Double t);
    
    /**
     * <p>曲線が写像される平面や空間の次元を返します Returns dimention closed this curve.</p>
     * @return 
     */
    Integer getDimention();
    
    /**
     * <p>定義域を取得します Returns domain.</p>
     * @return 定義域 domain
     */
    Domain getDomain();
}
