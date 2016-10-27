package com.test.storm.simple.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/24.
 *
 * 接收数据处理节点Bolt，当前节点也可分解任务将数据发送到后续Bolt节点处理；
 * 直接实现接口IRichBolt、IBolt时，在执行处理完成逻辑任务后需调用
 * 接收成功：collector.ack()或者 失败：collector.fail()
 * BaseBasicBolt：基础Bolt，接受处理成功则不用手动调用ack()或者fail()
 *
 */
public class SplitBolt extends BaseBasicBolt {

    private Map<String, Integer> count = new HashMap<String, Integer>();

    /**
     * 数据接收，逻辑处理
     * BaseBasicBolt通过BasicBoltExecutor执行
     * public void execute(Tuple input) {
     *      _collector.setContext(input);
     *      try {
     *          _bolt.execute(input, _collector);
     *          _collector.getOutputter().ack(input);
     *      } catch(FailedException e) {
     *          if(e instanceof ReportedFailedException) {
     *             _collector.reportError(e);
     *          }
     *          _collector.getOutputter().fail(input);
     *      }
     * }
     * @param input 接收的数据
     * @param collector
     */
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String msg = input.getString(0);
        System.out.println("获取到上游bolt值msg = " + msg);

        if(null != msg) {
            String[] str = msg.split(" ");
            for(String s : str) {
                collector.emit(new Values(s));
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("split"));
    }
}
