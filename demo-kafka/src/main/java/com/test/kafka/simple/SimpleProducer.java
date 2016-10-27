package com.test.kafka.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/1/19
 * @Description
 */
public class SimpleProducer {

    public void send() {
        Properties props = new Properties();
        //host1:port1,host2:port2
        props.put("bootstrap.servers", "192.168.171.128:9092");
        //每次请求成功提交，保证消息发送成功
        props.put("acks", "all");
        //重试次数
        props.put("retries", 1);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //消息发送到某个分区上，默认org.apache.kafka.clients.producer.internals.DefaultPartitioner
//        props.put("partitioner.class", "com.test.kafka.simple.SimplePartitioner");

        Producer<String, String> producer = new KafkaProducer(props);
        for(int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("mykafka", "mykafka" + Integer.toString(i), "hello kafka " + Integer.toString(i)));

        }
        producer.close();
    }

    public static void main(String[] args) {
        new SimpleProducer().send();
    }

}
