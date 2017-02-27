/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import java.lang.reflect.Type;
import org.jumpaku.json.Converter;

/**
 *
 * @author Jumpaku
 */
public final class JsonInterval implements Converter<Interval>{

    static final JsonInterval CONVERTER = new JsonInterval();

    @Override public Type getTemporaryType() {
        return Data.class;
    }

    @Override public Temporary<Interval> toTemporary(Interval i) {
        return new Data(i);
    }

    public static final class Data implements Temporary<Interval>{

        private final Double begin;
        
        private final Double end;

        public Data(Interval i) {
            this.begin = i.getBegin();
            this.end = i.getEnd();
        }

        @Override
        public Interval newInstance() {
            return Interval.of(begin, end);
        }
    }
}
