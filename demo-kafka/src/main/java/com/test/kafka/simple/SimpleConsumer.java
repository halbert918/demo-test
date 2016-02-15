package com.test.kafka.simple;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/1/15
 * @Description
 */
public class SimpleConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    public void poll() {
        KafkaConsumer<String, String> consumer = null;
        try {
            Properties props = new Properties();
            props.put("bootstrap.servers", "192.168.52.128:9092");
            //设置定期提交offset，也可以手动调用KafkaConsumer.commitSync()方法提交
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "5000");
            //心跳检测，检测session连接，间隔时间应该小于session-time-out，建议配置不大于1/3 session-time-out
            props.put("heartbeat.interval.ms", "5000");
            props.put("session.timeout.ms", "30000");
            props.put("group.id", "test-consumer-group");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumer = new KafkaConsumer<String, String>(props);

            Map<String, List<PartitionInfo>> topics = consumer.listTopics();
            consumer.subscribe(Arrays.asList("mykafka"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {

                    System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                    System.out.println();
                }

            }
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        new SimpleConsumer().poll();
    }

}
