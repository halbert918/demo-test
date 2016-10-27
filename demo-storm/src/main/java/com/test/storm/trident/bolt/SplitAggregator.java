package com.test.storm.trident.bolt;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/10/27.
 */
public class SplitAggregator extends BaseAggregator<Map<String, Integer>> {

    @Override
    public Map<String, Integer> init(Object batchId, TridentCollector collector) {
        return new HashMap<>();
    }

    @Override
    public void aggregate(Map<String, Integer> val, TridentTuple tuple, TridentCollector collector) {
        Integer count = val.get(tuple.getString(0));
        val.put(tuple.getString(0), null == count ? 1 : ++count);
    }

    @Override
    public void complete(Map<String, Integer> val, TridentCollector collector) {
        if(!val.isEmpty()) {
            collector.emit(new Values(val));
//            for (Map.Entry entry : val.entrySet()) {
//                System.out.println("key = " + entry.getKey() + "，value = " + entry.getValue());
//            }
        }
    }
}
