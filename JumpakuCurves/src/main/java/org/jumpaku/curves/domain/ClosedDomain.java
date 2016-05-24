/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.domain;

/**
 *
 * @author Jumpaku
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
        return from.compareTo(t) <= 0 && to.compareTo(t) >= 0;
    }
}
