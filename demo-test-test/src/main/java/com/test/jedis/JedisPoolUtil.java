package com.test.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by min on 2015/3/4.
 */
public class JedisPoolUtil {

    private static JedisPool jedisPool;

    /**
     * 获取Jedis对象
     * @return
     */
    public static Jedis getJedis() {
        if(null == jedisPool) {
            initPool();
        }
        return jedisPool.getResource();
    }

    /**
     * 初始化连接池
     */
    public static synchronized void initPool() {
        if(null == jedisPool) {
            //TODO
            createPool("192.168.54.105", 6379);
        }
    }

    /**
     * 创建连接池
     * @param host
     * @param port
     */
    public static void createPool(String host, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100); //设置最大连接数
        config.setMaxIdle(10);   //设置空闲连接
        config.setMaxWaitMillis(1000);  // 设置最大阻塞时间，记住是毫秒数milliseconds
        jedisPool = new JedisPool(config, host, port);
    }

    /**
     * 归还Jedis
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }


    //TEST
    public static void main(String[] args) {
        Jedis jedis = getJedis();
        jedis.set("test", "hello Jedis Pool123");
        System.out.print("=================>" + jedis.get("test"));
    }

}
