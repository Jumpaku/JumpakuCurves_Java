/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.domain;

/**
 * <p>左閉右開区間を表します Represents left-closed, right-open interval.</p>
 * @author Jumpaku
 */
public class ClosedOpen extends Interval{
    /**
     * <p>指定された始点と終点から区間 [from, to) を生成します Cnstructs domain [from, to).</p>
     * @param from 始点 start
     * @param to 終点 end
     */
    public ClosedOpen(Double from, Double to) {
        super(from, to);
    }
    
    /**
     * {@inheritDoc }
     * @param t パラメータ parameter
     * @return tが[from,to)に含まれる場合{@code true}, そうでない場合{@code false} {@code true} if t is in [from,to);otherwise {@code false}
     */
    @Override
    public final Boolean isIn(Double t) {
        return getFrom().compareTo(t) <= 0 && getTo().compareTo(t) > 0;
    }
}
