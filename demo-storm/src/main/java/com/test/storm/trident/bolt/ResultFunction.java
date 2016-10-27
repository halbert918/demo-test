package com.test.storm.trident.bolt;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.util.Map;

/**
 * Created by admin on 2016/10/27.
 */
public class ResultFunction extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        Map<String, Integer> result = (Map<String, Integer>) tuple.get(0);
        for (Map.Entry entry : result.entrySet()) {
            System.out.println("key = " + entry.getKey() + "，value = " + entry.getValue());
        }
    }
}
