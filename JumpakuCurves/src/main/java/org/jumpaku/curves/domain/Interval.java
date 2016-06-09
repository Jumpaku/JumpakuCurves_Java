/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.domain;

/**
 *
 * @author ito tomohiko
 */
public abstract class Interval implements Domain{
    private final Double from;
    private final Double to;

    /**
     * <p>指定された始点と終点から区間を生成します Cnstructs interval with from and to.</p>
     * @param from 始点 start
     * @param to 終点 end
     */
    public Interval(Double from, Double to) {
        this.from = from;
        this.to = to;
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
