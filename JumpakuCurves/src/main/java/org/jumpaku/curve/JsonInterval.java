/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jumpaku.curve;

import org.jumpaku.json.Converter;

/**
 *
 * @author Jumpaku
 */
public class JsonInterval implements Converter<Interval>{

    public static class Data{

        private final Double start;

        private final Double end;

        public Data(Double start, Double end) {
            this.start = start;
            this.end = end;
        }
    }

    @Override
    public String toJson(Interval i) {
        return GSON.toJson(new Data(i.getbegin(), i.getEnd()), Data.class);
    }

    @Override
    public Interval fromJson(String json) {
        Data i = GSON.fromJson(json, Data.class);
        return Interval.closed(i.start, i.end);
    }
}
