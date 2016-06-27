/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javaslang.collection.Array;
import org.jumpaku.curves.interpolation.Average;
import org.jumpaku.curves.interpolation.Centripetal;
import org.jumpaku.curves.interpolation.Chordal;
import org.jumpaku.curves.interpolation.Data;
import org.jumpaku.curves.interpolation.Uniformly;
import org.jumpaku.curves.interpolation.UniformlySpaced;
import org.jumpaku.curves.interpolation.Universal;
import org.jumpaku.curves.vector.Point2D;

/**
 *
 * @author ito tomohiko
 */
public class Sample {
    public static void main(String[] args) {
        Array<Point2D> ps = Array.of(new Point2D(0.0,0.0), new Point2D(1.0,2.0), new Point2D(2.0, 3.0), new Point2D(3.0, 3.0), new Point2D(4.0,2.0),new Point2D(5.0,0.0));
        Array<Data<Point2D>> data = new Uniformly().parameterize(ps);
        new UniformlySpaced().generate(3, data)
                .forEach(k -> System.out.print(k + " "));
        System.out.println();
        new Average().generate(3, data)
                .forEach(k -> System.out.print(k + " "));
        System.out.println();
        
        new Chordal().parameterize(ps)
                .forEach(k -> System.out.print(k.getParam() + " "));
        System.out.println();
        new Centripetal(2.0).parameterize(ps)
                .forEach(k -> System.out.print(k.getParam() + " "));
        System.out.println();
        new Universal(3).parameterize(ps)
                .forEach(k -> System.out.print(k.getParam() + " "));
        System.out.println();
    }
}
