package com.test.kafka.simple;


import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/1/19
 * @Description
 */
public class SimplePartitioner implements Partitioner {

    public SimplePartitioner (VerifiableProperties props) {

    }

    @Override
    public int partition(Object o, int i) {
        int partition = 0;
        String stringKey = (String) o;
        int offset = stringKey.lastIndexOf('.');
        if (offset > 0) {
            partition = Integer.parseInt( stringKey.substring(offset+1)) % i;
        }
        return partition;
    }
}