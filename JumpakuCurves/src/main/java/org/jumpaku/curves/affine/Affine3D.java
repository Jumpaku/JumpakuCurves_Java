/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.affine;

import org.jumpaku.curves.vector.Point3D;
import org.jumpaku.curves.vector.Vec3;

/**
 * <p>3次のアフィン変換のインターフェイスを定義します Defines interface of affine transformation in 3D space.</p>
 * @author Jumpaku
 */
public interface Affine3D extends Affine<Point3D>{
    /**
     * <p>拡大縮小変換 Scaling.</p>
     * <p>
     * このAffine変換に拡大縮小変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される拡大縮小変換は原点を中心にして, x軸方向, y軸方向にそれぞれ指定された拡大率で拡大縮小します.</p>
     * <p>
     * This method concatenates scaling transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Center of scaling is the origin, magnification rates for x-axis and y-axis are given.</p>
     * @param x x-軸方向の拡大率(0であってはいけない) magnification rate for x-axis(not 0)
     * @param y y-軸方向の拡大率(0であってはいけない) magnification rate for y-axis(not 0)
     * @param z z-軸方向の拡大率(0であってはいけない) magnification rate for z-axis(not 0)
     * @return 拡大縮小変換を追加したアフィン変換 Affine concatenated Scaling
     */
    Affine3D scale(Double x, Double y, Double z);
    
    /**
     * <p>拡大縮小変換 Scaling.</p>
     * <p>
     * このAffine変換に拡大縮小変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される拡大縮小変換は原点を中心にして, x軸方向, y軸方向, z軸方向に共に指定された同じ拡大率で拡大縮小します.</p>
     * <p>
     * This method concatenates scaling transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Center of scaling is the origin, magnification rate for x-axis, y-axis, and z-axis are the same value which is specified by the argument.</p>
     * @param scale 拡大率(0であってはいけない) magnification rate (not 0)
     * @return 拡大縮小変換を追加したアフィン変換 Affine concatenated Scaling
     */
    default Affine3D scale(Double scale){
        return scale(scale, scale, scale);
    }
    
    /**
     * <p>拡大縮小変換 Scaling.</p>
     * <p>
     * このAffine変換に拡大縮小変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される拡大縮小変換は指定された点を中心にして, x軸方向, y軸方向にそれぞれ指定された拡大率で拡大縮小します.</p>
     * <p>
     * This method concatenates scaling transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Center of scaling is specified, magnification rates for x-axis and y-axis are given.</p>
     * @param center 拡大縮小の中心 center of Scaling
     * @param x x-軸方向の拡大率(0であってはいけない) scaling rate for x-axis(not 0)
     * @param y y-軸方向の拡大率(0であってはいけない) scaling rate for y-axis(not 0)
     * @param z 
     * @return 拡大縮小変換を追加したアフィン変換 Affine concatenated Scaling
     */
    default Affine3D scaleAt(Point3D center, Double x, Double y, Double z){
        return translate(center.getVec().negate()).scale(x, y, z).translate(center.getVec());
    }
    
    /**
     * <p>拡大縮小変換 Scaling.</p>
     * <p>
     * このAffine変換に拡大縮小変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される拡大縮小変換は指定された点を中心にして, x軸方向, y軸方向にそれぞれ指定された拡大率で拡大縮小します.</p>
     * <p>
     * This method concatenates scaling transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Center of scaling is the origin, magnification rates for x-axis and y-axis are given.</p>
     * @param center 拡大縮小の中心 center of scaling
     * @param scale 拡大率(0であってはいけない) magnification rate
     * @return 拡大縮小変換を追加したアフィン変換 Affine concatenated Scaling
     */
    default Affine3D scaleAt(Point3D center, Double scale){
        return scaleAt(center, scale, scale, scale);
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle is given.</p>
     * @param axis
     * @param radian 角度 angle
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    Affine3D rotate(Vec3 axis, Double radian);

    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle is given.</p>
     * @param axisInitial
     * @param axisTerminal
     * @param radian 角度 angle
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine3D rotate(Point3D axisInitial, Point3D axisTerminal, Double radian){
        return rotate(axisTerminal.from(axisInitial), radian);
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle is given.</p>
     * @param axisInitial
     * @param axis
     * @param radian 角度 angle
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine3D rotateAt(Point3D axisInitial, Vec3 axis, Double radian){
        Vec3 v = axisInitial.getVec();
        return translate(v.negate()).rotate(axis, radian).translate(v);
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle is given.</p>
     * @param from
     * @param to
     * @param radian 角度 angle
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine3D rotate(Vec3 from, Vec3 to, Double radian){
        return rotate(from.cross(to), radian);
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle is given.</p>
     * @param from
     * @param to
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine3D rotate(Vec3 from, Vec3 to){
        return rotate(from, to, from.angle(to));
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle is given.</p>
     * @param axisInitial
     * @param from
     * @param to
     * @param radian 角度 angle
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine3D rotateAt(Point3D axisInitial, Vec3 from, Vec3 to, Double radian){
        Vec3 v = axisInitial.getVec();
        return translate(v.negate()).rotate(from, to, radian).translate(v);
    }    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle is given.</p>
     * @param axisInitial
     * @param from
     * @param to
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine3D rotateAt(Point3D axisInitial, Vec3 from, Vec3 to){
        Vec3 v = axisInitial.getVec();
        return translate(v.negate()).rotate(from, to).translate(v);
    }

    /**
     * <p>平行移動変換 Translation.</p>
     * <p>
     * このAffine変換に更に平行移動変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される平行移動変換は指定されたベクトルの分だけ平行移動します.</p>
     * <p>
     * This method concatenates translation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Movement vector is given.</p>
     * @param v 平行移動ベクトル translation vector
     * @return 平行移動変換を追加したアフィン変換 Affin concatenated Transfomation
     */
    Affine3D translate(Vec3 v);
    
    /**
     * <p>平行移動変換 Translation.</p>
     * <p>
     * このAffine変換に更に平行移動変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される平行移動変換は指定されたベクトルの分だけ平行移動します.</p>
     * <p>
     * This method concatenates translation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Movement vector is given.</p>
     * @param x
     * @param y
     * @param z
     * @return 平行移動変換を追加したアフィン変換 Affin concatenated Transfomation
     */
    default Affine3D translate(Double x, Double y, Double z){
        return translate(new Vec3(x, y, z));
    }

    /**
     * <p>逆変換 Inversion.</p>
     * @return 元の変換の逆変換 Inverted affine
     */
    Affine3D invert();
    
    /**
     * <p>合成 concatenation.</p>
     * @param a 追加するアフィン変換 Affine to be added
     * @return 合成後のアフィン変換 Affine concatenated a
     */
    Affine3D concatenate(Affine3D a);
    
    /**
     * <p>ベクトルの変換 Apply transform to a vector.</p>
     * @param p 変換されるベクトル vector to be transformed
     * @return 変換されたベクトル transformed vector 
     */
    @Override
    Point3D apply(Point3D p);
}
