package com.test.storm.trident.spout;

import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import com.test.storm.kafka.KafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import storm.trident.operation.TridentCollector;
import storm.trident.spout.IPartitionedTridentSpout;
import storm.trident.topology.TransactionAttempt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/17.
 */
public class KafkaPartitionedTridentSpout implements IPartitionedTridentSpout {

    @Override
    public Coordinator<List<Partition>> getCoordinator(Map conf, TopologyContext context) {
        return new Coordinator<List<Partition>>() {
            /**
             * Return the partitions currently in the source of data. The idea is
             * is that if a new partition is added and a prior transaction is replayed, it doesn't
             * emit tuples for the new partition because it knows what partitions were in
             * that transaction.
             * 返回当前所有的Partitions，当增加了Partition时，重播时不会发射到新增的partition上
             * @return
             */
            @Override
            public List<Partition> getPartitionsForBatch() {
//                return Arrays.asList("kafka");
                return KafkaUtil.getTopics();
            }

            @Override
            public boolean isReady(long txid) {
                return true;
            }

            @Override
            public void close() {

            }
        };
    }

    @Override
    public Emitter<List<Partition>, Partition, PartitionMeta> getEmitter(Map conf, TopologyContext context) {
        return new Emitter<List<Partition>, Partition, PartitionMeta>() {
            @Override
            public List<Partition> getOrderedPartitions(List<Partition> allPartitionInfo) {
                return allPartitionInfo;
            }

            /**
             * MasterBatchCoordinator中初始化txid=INIT_TXID = 1L
             *
             * PartitionedTridentSpoutExecutor.Emitter.emitBatch():
             * 根据partition/txid在zk上创建唯一节点，数据发射处理成功后移除该节点，确保该节点数据恰好只发送一次
             * 若zk上存在该节点，则调用emitPartitionBatch重新发送，若不存在，则调用emitPartitionBatchNew方法
             * 根据lastPartitionMeta新的偏移量拉取数据并发送出去
             * @param tx
             * @param collector
             * @param partition
             * @param lastPartitionMeta
             * @return
             */
            @Override
            public PartitionMeta emitPartitionBatchNew(TransactionAttempt tx, TridentCollector collector, Partition partition, PartitionMeta lastPartitionMeta) {
                if(null == lastPartitionMeta) {
                    lastPartitionMeta = new PartitionMeta(partition.getTopic(), partition.getPartition());
                    lastPartitionMeta.setOffset(0L);
                    lastPartitionMeta.setNextOffset(0L);
                }
                //调用emitPartitionBatch，获取kafka上lastPartitionMeta节点数据
                emitPartitionBatch(tx, collector, partition, lastPartitionMeta);
                //构造新的PartitionMeta
                lastPartitionMeta.setOffset(lastPartitionMeta.getNextOffset());
                lastPartitionMeta.setNextOffset(KafkaUtil.getLastOffset(partition));
                return lastPartitionMeta;
            }

            /**
             * Emit a batch of tuples for a partition/transaction that has been emitted before, using
             * the metadata created when it was first emitted.
             * 重新发送partitionMeta偏移量下的数据
             * @param tx
             * @param collector
             * @param partition
             * @param partitionMeta
             */
            @Override
            public void emitPartitionBatch(TransactionAttempt tx, TridentCollector collector, Partition partition, PartitionMeta partitionMeta) {
                ConsumerRecords<String, String> records = KafkaUtil.poll(partitionMeta);
                for (ConsumerRecord record : records) {
                    List<Object> values = new ArrayList<>();
                    values.add(record.value());
                    collector.emit(values);
                }
            }

            /**
             * 刷新Partition
             * @param partitionResponsibilities
             */
            @Override
            public void refreshPartitions(List<Partition> partitionResponsibilities) {
                partitionResponsibilities.clear();
                partitionResponsibilities.addAll(KafkaUtil.getTopics());
            }

            @Override
            public void close() {
                KafkaUtil.close();
            }
        };
    }

    @Override
    public Map getComponentConfiguration() {
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("split");
    }
}
