package com.test.storm.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/12/22.
 *
 * 数据流入口
 *
 */
public class InputDataSpout extends BaseRichSpout {

    /**
     * 数据流的传递器
     */
    private SpoutOutputCollector collector;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //定义域，发送数据时会向指定fieldsGrouping的bolt发送
        declarer.declare(new Fields("word"));
    }

    /**
     * 初始化任务执行spout所需的环境
     * @param conf 当前topology提供在集群环境的配置信息
     * @param context topology上下文
     * @param collector 数据流发射器
     */
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        String[] words = new String[] {"hello", "storm", "test", "hello", "zookeeper", "hello", "kafka"};
        Random rand = new Random();
        String word = words[rand.nextInt(words.length)];

        System.out.println(Thread.currentThread().getName() + " 发送数据：" + word);

        collector.emit(new Values(word));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
