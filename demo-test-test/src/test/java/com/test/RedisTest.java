package com.test;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by min on 2015/3/4.
 */
public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.54.105", 6379);
        jedis.set("test", "Hello Redis1");
        System.out.print("测试redis缓存:" + jedis.get("test"));

    }

}
