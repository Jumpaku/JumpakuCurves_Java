/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.vector;

/**
 *
 * @author Jumpaku
 */
public class Point3D implements Point{
    
    private final Vec3 vec;
    
    public Point3D(Double x, Double y, Double z){
        this(new Vec3(x, y, z));
    }
    
    public Point3D(Vec3 vector) {
        this.vec = vector;
    }
    
    public Point3D(Point p){
        if(3 != p.getDimention())
            throw new IllegalArgumentException("dimention miss match");

        this.vec = new Vec3(p.getVec());
    }

    public static Vec3 normal(Point3D p1, Point3D p2, Point3D p3){
        return p1.from(p2).cross(p2.from(p3)).normalize();
    }
    
    @Override
    public Vec3 from(Point p) {
        return new Vec3(Point.super.from(p));
    }

    @Override
    public Vec3 to(Point p) {
        return new Vec3(Point.super.to(p)); //To change body closed generated methods, choose Tools | Templates.
    }

    @Override
    public Point3D divide(Double t, Point p) {
        return new Point3D(Point.super.divide(t, p));
    }

    @Override
    public Point3D move(Vec v) {
        return new Point3D(Point.super.move(v));
    }

    @Override
    public Vec3 getVec() {
        return vec;
    }
    
    public Double getX(){
        return get(0);
    }
        
    public Double getY(){
        return get(1);
    }
    
    public Double getZ(){
        return get(2);
    }
}
