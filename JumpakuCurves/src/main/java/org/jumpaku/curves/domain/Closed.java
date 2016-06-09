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
public final class Closed extends Interval{

    /**
     * <p>指定された始点と終点から閉区間 [from, to] を生成します Cnstructs closed domain [from, to].</p>
     * @param from 始点 start
     * @param to 終点 end
     */
    public Closed(Double from, Double to) {
        super(from, to);
    }
    
    /**
     * {@inheritDoc }
     * @param t パラメータ parameter
     * @return tが[from, to]に含まれる場合{@code true}, そうでない場合{@code false} {@code true} if t is in [from, to];otherwise {@code false}
     */
    @Override
    public final Boolean isIn(Double t) {
        return getFrom().compareTo(t) <= 0 && getTo().compareTo(t) >= 0;
    }
    

}
