package com.test.batch.simple.decider;

import com.test.batch.simple.DemoException;
import com.test.batch.simple.domian.PayBillItem;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by admin on 2016/8/17.
 * 数据批量写入
 *
 */
public class PayBillItemWriter implements ItemWriter<PayBillItem> {

    @Override
    public void write(List items) throws Exception {
        //TODO write database

        //测试事务
//        throw new DemoException("database rollback");
    }
}
