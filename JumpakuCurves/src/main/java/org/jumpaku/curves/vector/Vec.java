/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.vector;

import java.text.NumberFormat;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.geometry.Point;
import org.apache.commons.math3.geometry.Space;
import org.apache.commons.math3.geometry.Vector;

/**
 *
 * @author jumpaku
 * @param <S>
 */
public interface Vec<S extends Space> extends Vector<S>{

    /** {@inheritDoc } */
    @Override
    public Vector<S> add(Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public Vector<S> add(double factor, Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public double distance(Point<S> p);

    /** {@inheritDoc } */
    @Override
    public Vector<S> normalize() throws MathArithmeticException;

    /** {@inheritDoc } */
    @Override
    public String toString(NumberFormat format);

    /** {@inheritDoc } */
    @Override
    public Vector<S> subtract(double factor, Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public Vector<S> subtract(Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public Vector<S> scalarMultiply(double a);

    /** {@inheritDoc } */
    @Override
    public Vector<S> negate();

    /** {@inheritDoc } */
    @Override
    public boolean isNaN();

    /** {@inheritDoc } */
    @Override
    public boolean isInfinite();

    /** {@inheritDoc } */
    @Override
    public Vector<S> getZero();

    /** {@inheritDoc } */
    @Override
    public Space getSpace();

    /** {@inheritDoc } */
    @Override
    public double getNormSq();

    /** {@inheritDoc } */
    @Override
    public double getNormInf();

    /** {@inheritDoc } */
    @Override
    public double getNorm1();

    /** {@inheritDoc } */
    @Override
    public double getNorm();

    /** {@inheritDoc } */
    @Override
    public double dotProduct(Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public double distanceSq(Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public double distanceInf(Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public double distance1(Vector<S> v);

    /** {@inheritDoc } */
    @Override
    public double distance(Vector<S> v);
}
