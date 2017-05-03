/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import java.util.function.Function;

/**
 * this is a function from [a, b].
 * satisfies this.reverse().apply(t) equals this.apply(b-(t-a))
 * @author jumpaku
 */
public interface Reversible<C extends Function> {
    C reverse();
}
