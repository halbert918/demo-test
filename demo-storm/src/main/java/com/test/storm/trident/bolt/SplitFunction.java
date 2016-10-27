package com.test.storm.trident.bolt;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Created by admin on 2016/10/27.
 */
public class SplitFunction extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String data = tuple.getString(0);
        if(null != data) {
            String[] ss = data.split(" ");
            for (String s : ss) {
                collector.emit(new Values(s));
            }
        }
    }
}
