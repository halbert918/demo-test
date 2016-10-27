package com.test.storm.batch;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.test.storm.batch.bolt.SplitFunction;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

/**
 * Created by Administrator on 2015/12/25.
 */
public class BatchTopology {

    public static void main(String[] args) {
        try {
            TridentTopology topology = new TridentTopology();
            //Testing
            FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 2,
                    new Values("just do it"),
                    new Values("test storm by kafka"),
                    new Values("test storm by zk"));
//            spout.setCycle(true);
            topology.newStream("spout", spout)
                            .each(new Fields("sentence"), new SplitFunction(), new Fields("word"))
                    .parallelismHint(6);
            Config config = new Config();
            //debug模式会打印所有日志
            config.setDebug(true);

            //storm环境下启动
            if (args != null && args.length > 0) {
                config.setNumWorkers(1);
                StormSubmitter.submitTopology(args[0], config, topology.build());
            } else {
                //本地模式下运行的启动代码，设置最大的并发数，用于本地test
                config.setMaxTaskParallelism(3);
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("simple", config, topology.build());
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
