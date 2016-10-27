package com.test.storm.trident.spout;

/**
 * Created by admin on 2016/10/19.
 * zk中存储的元数据信息
 */
public class PartitionMeta extends Partition {
    /**
     * 在zk中的偏移量
     */
    public long  offset;
    /**
     * 下一偏移量
     */
    public long  nextOffset;

    public PartitionMeta(String topic, int partition) {
        super(topic, partition);
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(long nextOffset) {
        this.nextOffset = nextOffset;
    }

}
