/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.test;

import org.apache.commons.math3.util.Precision;
import org.jumpaku.curves.vector.Vec;
import org.jumpaku.curves.vector.Vec1;
import org.jumpaku.curves.vector.Vec2;
import org.jumpaku.curves.vector.Vec3;

/**
 *
 * @author ito tomohiko
 */
public class TestUtils {
    private TestUtils(){
    }
    
    public static Boolean equalsDouble(double a, double b){
        return Precision.equals(a, b, 1.0e-10);
    } 
    
    public static Boolean equalsVec1(Vec1 a, Vec1 b){
        return equalsDouble(a.getX(), b.getX());
    }

    public static Boolean equalsVec2(Vec2 a, Vec2 b){
        return equalsDouble(a.getX(), b.getX()) &&
                equalsDouble(a.getY(), b.getY());
    }

    public static Boolean equalsVec3(Vec3 a, Vec3 b){
        return equalsDouble(a.getX(), b.getX()) &&
                equalsDouble(a.getY(), b.getY()) && 
                equalsDouble(a.getZ(), b.getZ());
    }
    
    public static Boolean equalsVec(Vec a, Vec b){
        for(int i = 0; i < a.getDimention(); ++i){
            if(!equalsDouble(a.get(i), b.get(i))){
                return false;
            }
        }
        return true;
    }
}
