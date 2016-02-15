package com.test.kafka.connector;

import kafka.common.OffsetAndMetadata;
import kafka.common.TopicAndPartition;
import kafka.consumer.KafkaStream;
import kafka.consumer.TopicFilter;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.consumer.ConsumerRebalanceListener;
import kafka.serializer.Decoder;

import java.util.List;
import java.util.Map;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/1/19
 * @Description
 */
public class ReadFileConsumerConnector implements ConsumerConnector {

    @Override
    public <K, V> Map<String, List<KafkaStream<K, V>>> createMessageStreams(Map<String, Integer> topicCountMap, Decoder<K> keyDecoder, Decoder<V> valueDecoder) {
        return null;
    }

    @Override
    public Map<String, List<KafkaStream<byte[], byte[]>>> createMessageStreams(Map<String, Integer> topicCountMap) {
        return null;
    }

    @Override
    public <K, V> List<KafkaStream<K, V>> createMessageStreamsByFilter(TopicFilter topicFilter, int numStreams, Decoder<K> keyDecoder, Decoder<V> valueDecoder) {
        return null;
    }

    @Override
    public List<KafkaStream<byte[], byte[]>> createMessageStreamsByFilter(TopicFilter topicFilter, int numStreams) {
        return null;
    }

    @Override
    public List<KafkaStream<byte[], byte[]>> createMessageStreamsByFilter(TopicFilter topicFilter) {
        return null;
    }

    @Override
    public void commitOffsets() {

    }

    @Override
    public void commitOffsets(boolean retryOnFailure) {

    }

    @Override
    public void commitOffsets(Map<TopicAndPartition, OffsetAndMetadata> offsetsToCommit, boolean retryOnFailure) {

    }

    @Override
    public void setConsumerRebalanceListener(ConsumerRebalanceListener listener) {

    }

    @Override
    public void shutdown() {

    }
}
