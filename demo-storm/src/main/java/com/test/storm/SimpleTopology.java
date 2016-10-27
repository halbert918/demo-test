package com.test.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.test.storm.bolt.WordsBolt;
import com.test.storm.spout.InputDataSpout;

/**
 * Created by Administrator on 2015/12/25.
 */
public class SimpleTopology {

    public static void main(String[] args) {
        try {
            //初始化Topology的构造器
            TopologyBuilder builder = new TopologyBuilder();
            //设置spout id | 具体实例spout | 执行当前spout的并发数
            builder.setSpout("spout", new InputDataSpout(), 2);

            //shuffleGrouping:向bolt随机分组分发数据流
//            builder.setBolt("wordBolt", new WordsBolt(), 3).shuffleGrouping("spout");

            //fieldsGrouping:向bolt按照域分组分发数据流
            builder.setBolt("wordBolt", new WordsBolt(), 5).fieldsGrouping("spout", new Fields("word"));

//            builder.setBolt("testBolt", new TestBolt(), 3).shuffleGrouping("spout");

            Config config = new Config();
            config.setDebug(true);

            //storm环境下启动
            if (args != null && args.length > 0) {
                config.setNumWorkers(1);
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } else {
                //本地模式下运行的启动代码，设置最大的并发数，用于本地test
                config.setMaxTaskParallelism(5);
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("simple", config, builder.createTopology());
            }
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}