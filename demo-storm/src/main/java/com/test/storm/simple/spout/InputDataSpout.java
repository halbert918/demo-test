package com.test.storm.simple.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * Created by Administrator on 2015/12/22.
 *
 * 数据流入口:集成kafka拉取数据
 *
 */
public class InputDataSpout extends BaseRichSpout {

    /**
     * 数据流的传递器
     */
    private SpoutOutputCollector collector;

    private KafkaConsumer<String, String> consumer = null;

    //kafka host : port
    protected final static String HOST_PORT = "192.168.171.128:9092";
    //订阅kafka的主题
    protected final static String TOPIC = "mykafka";

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //定义域，发送数据时会向指定fieldsGrouping的bolt发送
//        declarer.declare(new Fields("id", "name", "desc"));
        declarer.declare(new Fields("msg"));
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
        consumer = new KafkaConsumer<>(initKafkaProps());
        consumer.subscribe(Arrays.asList(TOPIC));
    }

    @Override
    public void nextTuple() {
//        String[] words = new String[] {"hello", "storm", "test", "hello", "zookeeper", "hello", "kafka"};
//        Random rand = new Random();
//        String word = words[rand.nextInt(words.length)];
//        System.out.println(Thread.currentThread().getName() + " 发送数据：" + word);
        ConsumerRecords<String, String> records = consumer.poll(1000);
        if(!records.isEmpty()) {
            for(ConsumerRecord<String, String> record : records) {
                System.out.println("获取kafka数据record = " + record.value());
                //根据Fields定义参数数量，可发射对应的value
                collector.emit(new Values(record.value()));
            }
        } else {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {}
        }

    }

    @Override
    public void close() {
        super.close();
        consumer.close();
    }

    private Properties initKafkaProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", HOST_PORT);
        //设置定期提交offset，也可以手动调用KafkaConsumer.commitSync()方法提交
        props.put("enable.auto.commit", true);
        props.put("auto.commit.interval.ms", "5000");
        //心跳检测，检测session连接，间隔时间应该小于session-time-out，建议配置不大于1/3 session-time-out
        props.put("heartbeat.interval.ms", "5000");
        props.put("session.timeout.ms", "30000");
        props.put("group.id", "test-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
