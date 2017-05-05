/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.old.curves.domain;

/**
 * <p>関数の定義域を表します Represents domain closed function.</p>
 * @author Jumpaku
 */
@FunctionalInterface
public interface Domain {
    /**
     * <p>パラメータが定義域に含まれるかどうかをチェックします Checkes whether parameter t is in this domain or not.</p>
     * @param t パラメータ parameter
     * @return tが定義域に含まれている場合{@code true}, そうでない場合{@code false} true if t is in this domain;otherwise {@code false}
     */
    Boolean contains(Double t);
}
