/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.transform;

import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * <p>2次元ベクトルのアフィン変換のインターフェイスを定義します Defines interface of affine transformation for 2D vector.</p>
 * @author Jumpaku
 */
public interface Affine2D extends Transform<Euclidean2D, Vector2D>{
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
     * @return 拡大縮小変換を追加したアフィン変換 Affine concatenated Scaling
     */
    Affine2D scale(Double x, Double y);
    
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
     * @param radian 角度 angle
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    Affine2D rotate(Double radian);
    
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
    Affine2D translate(Vector2D v);
    
    /**
     * <p>剪断変換(シャーリング) Shearing(Skewing).</p>
     * <p>
     * このAffine変換に更に剪断変換(シャーリング)を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される剪断変換(シャーリング)は原点を軸にして, x軸方向, y軸方向にそれぞれ指定されたパラメータで変換します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Pivot of shearing is the origin, parameters for x-axis and y-axis are given.</p>
     * @param x x-軸方向の剪断パラメータ parameter for x-axis
     * @param y y-軸方向の剪断パラメータ parameter for y-axis
     * @return 剪断変換(シャーリング)を追加したアフィン変換 Affine concatenated Shearing(Skewing)
     */
    Affine2D shear(Double x, Double y);
    
    /**
     * <p>拡大縮小変換 Scaling.</p>
     * <p>
     * このAffine変換に拡大縮小変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される拡大縮小変換は原点を中心にして, x軸方向, y軸方向に共に指定された同じ拡大率で拡大縮小します.</p>
     * <p>
     * This method concatenates scaling transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Center of scaling is the origin, magnification rate for x-axis and y-axis are the same value which is specified by the argument.</p>
     * @param scale 拡大率(0であってはいけない) magnification rate (not 0)
     * @return 拡大縮小変換を追加したアフィン変換 Affine concatenated Scaling
     */
    default Affine2D scale(Double scale){
        return scale(scale, scale);
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
     * @return 拡大縮小変換を追加したアフィン変換 Affine concatenated Scaling
     */
    default Affine2D scaleAt(Vector2D center, Double x, Double y){
        return translate(center.negate()).scale(x, y).translate(center);
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
    default Affine2D scaleAt(Vector2D center, Double scale){
        return scaleAt(center, scale, scale);
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は指定された点を軸にして, 指定された角度で回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation and angle are given.</p>
     * @param axis 回転軸 point of axis
     * @param radian 回転角 angle
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine2D rotateAt(Vector2D axis, Double radian){
        return translate(axis.negate()).rotate(radian).translate(axis);
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は原点を軸にして1つ目のベクトルと2つ目のベクトルの間の角度だけ回転します.<br>
     * </p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is the origin, angle of rotation is between first vector and second vector.</p>
     * @param from 1つ目のベクトル first vector
     * @param to 2つ目のベクトル second vector
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine2D rotate(Vector2D from, Vector2D to){
        return rotateAt(Vector2D.ZERO, from, to);
    }
    
    /**
     * <p>回転変換 Rotation.</p>
     * <p>
     * このAffine変換に更に回転変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される回転変換は指定された点を軸にして1つ目のベクトルと2つ目のベクトルの間の角度だけ回転します.</p>
     * <p>
     * This method concatenates rotation transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Axis of rotation is specified, angle of rotation is between first vector and second vector.</p>
     * @param axis 回転軸 axis
     * @param from first vector
     * @param to second vector
     * @return 回転変換を追加したアフィン変換 Affine concatenated Rotation
     */
    default Affine2D rotateAt(Vector2D axis, Vector2D from, Vector2D to){
        Double cross = to.crossProduct(axis, from);
        Double radian = Vector2D.angle(from, to);
        return rotateAt(axis, cross > 0 ? radian : -radian);
    }
    
    /**
     * <p>剪断変換(シャーリング) Shearing(Skewing).</p>
     * <p>
     * このAffine変換に更に剪断変換(シャーリング)を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される剪断変換(シャーリング)は原点を軸にして, x軸方向に指定されたパラメータで変換します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Pivot of shearing is the origin, parameter for x-axis is given.</p>
     * @param x x軸方向のパラメータ parameter for x-axis
     * @return 剪断変換(シャーリング)を追加したアフィン変換 Affine concatenated Shearing(Skewing)
     */
    default Affine2D shearX(Double x){
        return shear(x, 0.0);
    }
    
    /**
     * <p>剪断変換(シャーリング) Shearing(Skewing).</p>
     * <p>
     * このAffine変換に更に剪断変換(シャーリング)を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される剪断変換(シャーリング)は原点を軸にして, y軸方向に指定されたパラメータで変換します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Pivot of shearing is the origin, parameter for y-axis is given.</p>
     * @param y y軸方向のパラメータ parameter for y-axis
     * @return 剪断変換(シャーリング)を追加したアフィン変換 Affine concatenated Shearing(Skewing)
     */
    default Affine2D shearY(Double y){
        return shear(0.0, y);
    }
    
    /**
     * <p>剪断変換(シャーリング) Shearing(Skewing).</p>
     * <p>
     * このAffine変換に更に剪断変換(シャーリング)を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される剪断変換(シャーリング)は指定された点を軸にして, x軸方向, y軸方向にそれぞれ指定されたパラメータで変換します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Pivot of shearing and parameters for x-axis and y-axis are given.</p>
     * @param pivot
     * @param x x軸方向のパラメータ parameter for x-axis
     * @param y y軸方向のパラメータ parameter for y-axis
     * @return 剪断変換(シャーリング)を追加したアフィン変換 Affine concatenated Shearing(Skewing)
     */
    default Affine2D shearAt(Vector2D pivot, Double x, Double y){
        return translate(pivot.negate()).shear(x, y).translate(pivot);
    }
    
    /**
     * <p>剪断変換(シャーリング) Shearing(Skewing).</p>
     * <p>
     * このAffine変換に更に剪断変換(シャーリング)を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される剪断変換(シャーリング)は指定された点を軸にして, x軸方向の指定されたパラメータで変換します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Pivot of shearing and parameter for x-axis are given.</p>
     * @param pivot 剪断変換(シャーリング)の軸 pivot of shearing
     * @param x x軸方向のパラメータ parameter for x-axis
     * @return 剪断変換(シャーリング)を追加したアフィン変換 Affine concatenated Shearing(Skewing)
     */
    default Affine2D shearXAt(Vector2D pivot, Double x){
        return shearAt(pivot, x, 0.0);
    }
    
    /**
     * <p>剪断変換(シャーリング) Shearing(Skewing).</p>
     * <p>
     * このAffine変換に更に剪断変換(シャーリング)を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される剪断変換(シャーリング)は指定された点を軸にして, y軸方向の指定されたパラメータで変換します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Pivot of shearing and parameter for y-axis are given.</p>
     * @param pivot 剪断変換(シャーリング)の軸 pivot of shearing
     * @param y y軸方向のパラメータ parameter for y-axis
     * @return 剪断変換(シャーリング)を追加したアフィン変換 Affine concatenated Shearing(Skewing)
     */
    default Affine2D shearYAt(Vector2D pivot, Double y){
        return shearAt(pivot, 0.0, y);
    }
    
    /**
     * <p>スクイーズ変換 Squeezing.</p>
     * <p>
     * このAffine変換に更にスクイーズ変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加されるスクイーズ変換は原点を中心にして, 指定されたパラメータkに対してx軸方向にk倍, y軸方向に1/k倍に拡大縮小します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Center of squeezeing is origin.<br>
     * Let k be given parameter, squeezing scales k times for x-axis and 1/k times for y-axis.</p>
     * @param k パラメータ parameter
     * @return スクイーズ変換を追加したアフィン変換 Affine concatenated squeezing
     */
    default Affine2D squeeze(Double k){
        return scale(k, 1/k);
    }
    
    /**
     * <p>スクイーズ変換 Squeezing.</p>
     * <p>
     * このAffine変換に更にスクイーズ変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加されるスクイーズ変換は原点を中心にして, 指定されたパラメータkに対してx軸方向にk倍, y軸方向に1/k倍に拡大縮小します.</p>
     * <p>
     * This method concatenates shearing transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * Center of squeezeing is specified.<br>
     * Let k be given parameter, squeezing scales k times for x-axis and 1/k times for y-axis.</p>
     * @param center 中心 center
     * @param k パラメータ parameter
     * @return スクイーズ変換を追加したアフィン変換 Affine concatenated squeezing
     */
    default Affine2D squeezeAt(Vector2D center, Double k){
        return translate(center.negate()).squeeze(k).translate(center);
    }
    
    /**
     * <p>鏡映変換 Refrection</p>
     * <p>
     * このAffine変換に更に鏡映変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される鏡映変換は原点を中心に点対称に変換します.</p>
     * <p>
     * This method concatenates refrection transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * This refrection is point-symmetrical transformation about the origin.</p>
     * @return 鏡映変換を追加したアフィン変換 Affine concatenated refrection
     */
    default Affine2D refrectOrigin(){
        return scale(-1.0);
    }
    
    /**
     * <p>鏡映変換 Refrection</p>
     * <p>
     * このAffine変換に更に鏡映変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される鏡映変換は指定された点を中心に点対称に変換します.</p>
     * <p>
     * This method concatenates refrection transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * This refrection is point-symmetrical transformation about specified center.</p>
     * @param center 点対称の中心 center of refrection
     * @return 鏡映変換を追加したアフィン変換 Affine concatenated refrection
     */
    default Affine2D refrectAt(Vector2D center){
        return scaleAt(center, -1.0);
    }
    
    /**
     * <p>鏡映変換 Refrection</p>
     * <p>
     * このAffine変換に更に鏡映変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される鏡映変換はx軸で線対称に変換します.</p>
     * <p>
     * This method concatenates refrection transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * This refrection is line-symmetrical transformation about x-axis.</p>
     * @return 鏡映変換を追加したアフィン変換 Affine concatenated refrection
     */
    default Affine2D refrectXAxis(){
        return scale(1.0, -1.0);
    }
    
    /**
     * <p>鏡映変換 Refrection</p>
     * <p>
     * このAffine変換に更に鏡映変換を追加してできる新たなAffine変換オブジェクトを返します.<br>
     * このオブジェクト自体は変更されません.<br>
     * 追加される鏡映変換は指定された点を中心に点対称に変換します.</p>
     * <p>
     * This method concatenates refrection transformation to this, and returns concatenated object.<br>
     * This method doesn't change original object.<br>
     * This refrection is line-symmetrical transformation about y-axis.</p>
     * @return 鏡映変換を追加したアフィン変換 Affine concatenated refrection
     */
    default Affine2D refrectYAxis(){
        return scale(-1.0, 1.0);
    }
    
    /**
     * <p>逆変換 Inversion.</p>
     * @return 元の変換の逆変換 Inverted affine
     */
    Affine2D invert();
    
    /**
     * <p>合成 concatenation.</p>
     * @param a 追加するアフィン変換 Affine to be added
     * @return 合成後のアフィン変換 Affine concatenated a
     */
    Affine2D concatenate(Affine2D a);
    
    /**
     * <p>ベクトルの変換 Apply transform to a vector.</p>
     * @param v 変換されるベクトル vector to be transformed
     * @return 変換されたベクトル transformed vector 
     */
    @Override
    Vector2D apply(Vector2D v);
}
