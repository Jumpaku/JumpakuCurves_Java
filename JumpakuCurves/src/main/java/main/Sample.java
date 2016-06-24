/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javaslang.collection.Array;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.jumpaku.curves.interpolation.Universal;
import org.jumpaku.curves.utils.GoldenSectionSearch;

/**
 *
 * @author ito tomohiko
 */
public class Sample {
    public static void main(String[] args) {
        Double p = GoldenSectionSearch.whereMinimum(x -> (x-2)*(x-2) + 5, -50.0, 100.0);
        //System.out.println(p);        
        
        new Universal(4).parameterize(Array.range(0, 7).map(e->Vector2D.ZERO))
                .forEach(k -> System.out.println(k.getParam() + " "));
    }
}
