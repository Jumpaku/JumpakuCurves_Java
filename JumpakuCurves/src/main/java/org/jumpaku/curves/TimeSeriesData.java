/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves;

/**
 *
 * @author Jumpaku
 * @param <D>
 */
public class TimeSeriesData<D> {
    private final D data;
    private final Double time;
    private final Double weight;

    public static <D> TimeSeriesData<D> currentData(D d){
        return currentData(d, 1.0);
    }
    
    public static <D> TimeSeriesData<D> currentData(D d, Double weight){
        return new TimeSeriesData<>(d, weight, System.nanoTime()*1.0e-9);
    }

    public TimeSeriesData(D data, Double time) {
        this(data, 1.0, time);
    }
    public TimeSeriesData(D data, Double weight, Double time) {
        this.data = data;
        this.weight = weight;
        this.time = time;
    }

    public D getData() {
        return data;
    }

    public Double getTime() {
        return time;
    }

    public Double getWeight() {
        return weight;
    }
    
}
