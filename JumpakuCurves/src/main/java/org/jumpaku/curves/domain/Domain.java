/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curves.domain;

/**
 *
 * @author Jumpaku
 */
@FunctionalInterface
public interface Domain {
    Boolean isIn(Double t);
}
