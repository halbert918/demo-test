package com.test.storm.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.test.storm.simple.bolt.SplitBolt;
import com.test.storm.simple.bolt.WordsBolt;
import com.test.storm.simple.spout.InputDataSpout;
import com.test.storm.trident.bolt.ResultFunction;
import com.test.storm.trident.bolt.SplitAggregator;
import com.test.storm.trident.bolt.SplitFunction;
import com.test.storm.trident.spout.KafkaPartitionedTridentSpout;
import storm.trident.TridentTopology;

/**
 * Created by Administrator on 2015/12/25.
 */
public class SimpleTridentTopology {

    public static void main(String[] args) {
        try {
            TridentTopology tridentTopology = new TridentTopology();
            tridentTopology.newStream("spout", new KafkaPartitionedTridentSpout())
                    //count域会加到split域后面，且split域中的值还会存在，
                    //即发送数据是TridentTuple中会保存隐藏split域的数据，
                    // 若不希望split域继续传递，则可使用project方法，值传递count域
                    .each(new Fields("split"), new SplitFunction(), new Fields("count"))
                    //向下只输出count域
                    .project(new Fields("count"))
                    //对count域分组，则聚合按照分组进行发射数据，若部分组则全部处理完一起发送数据
//                    .groupBy(new Fields("count"))
                    .aggregate(new Fields("count"), new SplitAggregator(), new Fields("result"))
                    .each(new Fields("result"), new ResultFunction(), new Fields("print"))
                    .parallelismHint(1);
            Config config = new Config();
            //debug模式会打印所有日志
            config.setDebug(true);

            //storm环境下启动
            if (args != null && args.length > 0) {
                config.setNumWorkers(1);
                StormSubmitter.submitTopology(args[0], config, tridentTopology.build());
            } else {
                //本地模式下运行的启动代码，设置最大的并发数，用于本地test
                config.setMaxTaskParallelism(5);
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("simple", config, tridentTopology.build());
            }
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
