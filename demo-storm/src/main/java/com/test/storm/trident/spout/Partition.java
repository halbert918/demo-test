package com.test.storm.trident.spout;

import storm.trident.spout.ISpoutPartition;

import java.io.Serializable;

/**
 * Created by admin on 2016/10/24.
 */
public class Partition implements ISpoutPartition, Serializable {

    /**
     * kafka-brokerId
     */
    private int brokerId;
    /**
     * kafka-broker-topic
     */
    private String topic;
    /**
     * partition分区
     */
    private int partition;

    public Partition(){}

    public Partition(String topic, int partition) {
        this.topic = topic;
        this.partition = partition;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    /**
     * 构造Partition唯一值
     * @return
     */
    @Override
    public String getId() {
        return topic + "/" + partition;
    }

}
