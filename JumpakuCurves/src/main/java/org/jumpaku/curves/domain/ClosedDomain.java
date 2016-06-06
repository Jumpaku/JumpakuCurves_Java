/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.domain;

/**
 * <p>閉区間を表します Represents closed doamin.</p>
 * @author Jumpaku
 */
public class ClosedDomain implements Domain{
    private final Double from;
    private final Double to;

    /**
     * <p>指定された始点と終点から閉区間を生成します Cnstructs closed domain [from, to].
     * @param from 始点 start
     * @param to 終点 end
     */
    public ClosedDomain(Double from, Double to) {
        this.from = from;
        this.to = to;
    }
    /**
     * {@inheritDoc }
     * @param t パラメータ parameter
     * @return tが[from,to]に含まれる場合{@code true}, そうでない場合{@code false} {@code true} if t is in [from,to];otherwise {@code false}
     */
    @Override
    public final Boolean isIn(Double t) {
        return from.compareTo(t) <= 0 && to.compareTo(t) >= 0;
    }
    
    /**
     * <p>始点を返す Returns start.</p>
     * @return 始点 start
     */
    public final Double getFrom(){
        return from;
    }
    
    /**
     * <p>終点を返す Returns end</p>
     * @return 終点 end
     */
    public final Double getTo(){
        return to;
    }
}
