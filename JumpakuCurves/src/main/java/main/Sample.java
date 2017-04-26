/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javaslang.collection.Array;
import main.old.curves.interpolation.Average;
import main.old.curves.interpolation.Centripetal;
import main.old.curves.interpolation.Chordal;
import main.old.curves.interpolation.Data;
import main.old.curves.interpolation.Uniformly;
import main.old.curves.interpolation.UniformlySpaced;
import main.old.curves.interpolation.Universal;
import main.old.curves.vector.Point2D;

/**
 *
 * @author ito Jumpaku
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
