package com.test.storm.kafka;

import com.test.storm.trident.spout.Partition;
import com.test.storm.trident.spout.PartitionMeta;
import kafka.api.FetchRequest;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by admin on 2016/10/24.
 */
public class KafkaUtil {

    private static Consumer consumer = null;

    static {
        consumer = new KafkaConsumer<>(initKafkaProps());
    }

    /**
     * 获取kafka-topic
     * @return
     */
    public static List<Partition> getTopics() {
        Map<String, List<PartitionInfo>> map = consumer.listTopics();
        List<Partition> partitions = new ArrayList<>();
        for(Map.Entry<String, List<PartitionInfo>> entry : map.entrySet()) {
//            if("connect-configs".equals(entry.getKey()) || "page_visits".equals(entry.getKey())
//                    || "connect-offsets".equals(entry.getKey()) || "__consumer_offsets".equals(entry.getKey())) {
            if(!"mykafka".equals(entry.getKey())) {
                continue;
            }
            List<PartitionInfo> partitionInfos = entry.getValue();
            for (PartitionInfo partitionInfo : partitionInfos) {
                Partition partition = new Partition();
                partition.setTopic(partitionInfo.topic());
                partition.setPartition(partitionInfo.partition());
                partitions.add(partition);
            }
        }
        return partitions;
    }

    /**
     * 获取最后kafka-topic-partition最后一个offset
     * @param partition
     * @return
     */
    public static long getLastOffset(Partition partition) {
        List<TopicPartition> partitions = new ArrayList<TopicPartition>() {
            {
                add(new TopicPartition(partition.getTopic(), partition.getPartition()));
            }
        };
        consumer.assign(partitions);
        return consumer.position(new TopicPartition(partition.getTopic(), partition.getPartition()));
    }

    /**
     * 根据partition拉取数据
     * @param partitionMeta
     * @return
     */
    public static ConsumerRecords<String, String> poll(PartitionMeta partitionMeta) {
        //        consumer.subscribe(Arrays.asList(topics));
        List<TopicPartition> partitions = new ArrayList<TopicPartition>() {
            {
                add(new TopicPartition(partitionMeta.getTopic(), partitionMeta.getPartition()));
            }
        };

        consumer.assign(partitions);
        ConsumerRecords<String, String> records = consumer.poll(3000);
//        commit(partitionMeta);  //无法提交？
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        TopicPartition topicPartition = new TopicPartition(partitionMeta.getTopic(), partitionMeta.getPartition());
        OffsetAndMetadata metadata = consumer.committed(topicPartition);
        offsets.put(topicPartition, metadata);
        consumer.commitAsync(offsets, new OffsetCommitCallback() {
            @Override
            public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                if(null != exception) {
                    exception.printStackTrace();
                }
                return;
            }
        });
//        consumer.commitAsync();
        return records;
    }

    /**
     *
     * @param partitionMeta
     */
    private static void commit(PartitionMeta partitionMeta) {
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        TopicPartition topicPartition = new TopicPartition(partitionMeta.getTopic(), partitionMeta.getPartition());
        OffsetAndMetadata metadata = consumer.committed(topicPartition);
        offsets.put(topicPartition, metadata);
        consumer.commitAsync(offsets, new OffsetCommitCallback() {
            @Override
            public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                if(null != exception) {
                    exception.printStackTrace();
                }
                return;
            }
        });
    }

    public static void close() {
        try {
            consumer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties initKafkaProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.171.128:9092");
        //设置定期提交offset，也可以手动调用KafkaConsumer.commitSync()方法提交
        props.put("enable.auto.commit", false);
        props.put("auto.commit.interval.ms", "5000");
        //心跳检测，检测session连接，间隔时间应该小于session-time-out，建议配置不大于1/3 session-time-out
        props.put("heartbeat.interval.ms", "5000");
        props.put("session.timeout.ms", "30000");
        props.put("group.id", "test-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    public static void main(String[] args) {
        PartitionMeta partitionMeta = new PartitionMeta("mykafka", 0);
        int count = 0;
        while (count == 0) {
            ConsumerRecords<String, String> records = poll(partitionMeta);
            count = records.count();
        }
    }
}
