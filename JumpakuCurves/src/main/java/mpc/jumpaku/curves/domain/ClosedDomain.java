/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.domain;

/**
 *
 * @author ito
 */
public class ClosedDomain implements Domain{
    private final Double from;
    private final Double to;

    public ClosedDomain(Double from, Double to) {
        this.from = from;
        this.to = to;
    }
    
    @Override
    public Boolean isIn(Double t) {
        return from <= t && t <= to;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }
    
}
