package com.test.batch.simple.decider;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by heyinbo on 2016/8/17.
 * 抽象数据读取
 */
public abstract class PayBillItemReader<T> implements ItemReader<T>, InitializingBean {
    /**
     * 当前数据所处位置
     */
    private volatile int current = 0;
    /**
     * 数据集
     */
    protected volatile List<T> result;

    private Object lock = new Object();

    public T doReader() {
        synchronized (lock) {
            if (null == result) {
                doDownLoad();
            }
        }
        int next = current++;
        if (null != result && next < result.size()) {
            return result.get(next);
        }
        return null;
    }

    public abstract void doDownLoad();

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
