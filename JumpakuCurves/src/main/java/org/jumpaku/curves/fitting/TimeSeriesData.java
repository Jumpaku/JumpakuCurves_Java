/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.fitting;

/**
 *
 * @author Jumpaku
 * @param <D>
 */
public class TimeSeriesData<D> {
    private final D data;
    private final Double time;

    public TimeSeriesData(D data, Double time) {
        this.data = data;
        this.time = time;
    }

    public D getData() {
        return data;
    }

    public Double getTime() {
        return time;
    }
    
}
