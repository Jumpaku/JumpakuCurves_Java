/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.interpolation;

import org.jumpaku.curves.vector.Point;

/**
 *
 * @author Jumpaku
 * @param <P>
 */
public class Data<P extends Point> {
    public Data(P point, Double param) {
        this.point = point;
        this.param = param;
    }

    private final P point;

    private final Double param;

    public P getPoint() {
        return point;
    }

    public Double getParam() {
        return param;
    }
}

