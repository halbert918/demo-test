package com.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ZooKeeperOperate {

    private void zkTest() throws Exception{

        //创建zookeeper实例，Watcher：监控节点变化的事件
        ZooKeeper zk = new ZooKeeper("192.168.171.128:2181", 50000, new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                //节点变化时处理逻辑
                System.out.println("节点:" + watchedEvent.getPath() + "发生变化" + watchedEvent.getState().toString());
            }
        });

//        //1、创建root节点 2、初始化节点数据 3、不进行ACL权限控制 4、节点为永久性创建
//        zk.create("/root", "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//
//        zk.create("/root/child_002", "child001".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

//        String paths = zk.create("/root/node1234", "node".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //KeeperErrorCode = ConnectionLoss for /root
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Stat stat = zk.exists("/locks", false);
        if(null != stat) {
            zk.delete("/locks", stat.getVersion());
        }

        List<String> children = zk.getChildren("/locks", true);
        for(String s : children) {
            System.out.println("node = " + s);
        }
        zk.close();
    }

    public static void main(String[] args) {
        try {
            new ZooKeeperOperate().zkTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
