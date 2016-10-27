package com.zk.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于zookeeper实现的分布式锁
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/1/6
 * @Description
 */
public class DistributedLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLock.class);

    private ZooKeeper zooKeeper = null;
    /**
     * 锁根节点目录
     */
    private final String LOCK_ROOT = "/locks";
    /**
     * 当前节点
     */
    private String currentNode;
    /**
     * 获取需要监控的前一个节点
     */
    private String prevNode;
    /**
     * 当前已获得锁节点
     */
    private String lockedBy;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 线程阻塞，直到计数器减为0
     */
    private CountDownLatch latch;

    public DistributedLock() {
        this("/lock");
    }

    public DistributedLock(String nodeName) {
        this("192.168.52.128:2181", 60000, nodeName);
    }

    public DistributedLock(String connectString, int sessionTimeout, String nodeName) {
        this.nodeName = nodeName;
        initZooKeeper(connectString, sessionTimeout);
        latch = new CountDownLatch(1);
    }

    /**
     * 初始化zookeeper
     * @param connectString
     * @param sessionTimeout
     */
    public void initZooKeeper(String connectString, int sessionTimeout) {
        try {
            //创建zookeeper实例，Watcher：监控节点变化的事件
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, new ZKWatcher());
            //判断锁对应的根节点是否存在
            Stat stat = zooKeeper.exists(LOCK_ROOT, false);
            if(null == stat) {
                zooKeeper.create(LOCK_ROOT, LOCK_ROOT.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void lock() {
        //尝试获取锁
        if(!tryLock()) {
            //阻塞
            await();
        }
        logger.info("线程{}获取锁{}", Thread.currentThread().getName(), currentNode);
    }

    /**
     * 阻塞线程，直到latch计数countDown=0
     */
    private void await() {
        try {
            Stat stat = zooKeeper.exists(prevNode, true);
            if(null != stat) {
                logger.info("线程{}被阻塞，当前锁{}", Thread.currentThread().getName(), lockedBy);
                latch.await();
            }
            //前一个节点不存在，则当前节点可获取锁
            lockedBy = currentNode;
        } catch (InterruptedException e) {
            logger.error("thread is interrupted{}", e);
        } catch (KeeperException e) {
            logger.error("KeeperException{}", e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock() {
        if(hasLocked()) {
            return true;
        }

        try {
            if (null == currentNode) {
                currentNode = zooKeeper.create(LOCK_ROOT + nodeName, Thread.currentThread().getName().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                logger.info("线程{}创建节点{}完成", Thread.currentThread().getName(), currentNode);
            }
            //如果当前最小的节点与当前创建的节点相同，则获取到锁
            if(isLockNode(currentNode)) {
                return true;
            }
        } catch (InterruptedException e) {
            logger.error("create zooKeeper node failed...", e);
            e.printStackTrace();
        } catch (KeeperException e) {
            logger.error("create zooKeeper node failed...", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /**
     * 判断当前节点是否是需要枷锁的节点（最小的节点）
     * @param currentNode
     * @return
     */
    private boolean isLockNode(String currentNode) {
        List<String> nodes = getChildren();
        Collections.sort(nodes, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

        lockedBy = LOCK_ROOT + "/" + nodes.get(0);

        if(currentNode.equals(lockedBy)) {
            return true;
        }

        //获取当前节点的前一个节点数据（监控前一个节点数据的变化）
        String nodeName = currentNode.substring(LOCK_ROOT.length() + 1);
        for(int i = 1; i < nodes.size(); i++) {
            if(nodeName.equals(nodes.get(i))) {
                prevNode = LOCK_ROOT + "/" + nodes.get(i - 1);
                break;
            }
        }
        return false;
    }

    /**
     * 获取所有节点
     * @return
     */
    private List<String> getChildren() {
        try {
            return zooKeeper.getChildren(LOCK_ROOT, false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断当前是否已经获取到锁
     * @return
     */
    private boolean hasLocked() {
        return null != currentNode && null != lockedBy && currentNode.equals(lockedBy);
    }

    @Override
    public void unlock() {
        try {
            Stat stat = zooKeeper.exists(currentNode, true);
            if(null != stat) {
                logger.info("线程{}释放锁{}", Thread.currentThread().getName(), currentNode);
                zooKeeper.delete(currentNode, stat.getVersion());
            }
        } catch (KeeperException e) {
            e.printStackTrace();
            logger.info("线程{}释放锁{}失败", Thread.currentThread().getName(), currentNode);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("线程{}释放锁{}失败", Thread.currentThread().getName(), currentNode);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }


    /**
     * 监控节点变化
     */
    private class ZKWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            //判断是否是删除节点事件，并且判断删除的节点是否是当前节点的前一个节点
            if(event.getType() == Event.EventType.NodeDeleted) {
                //阻塞线程计数器减1
                latch.countDown();
            }
        }
    }
}
