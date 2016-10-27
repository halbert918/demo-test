package com.test.batch.simple.decider;

import com.test.batch.simple.domian.PayBillItem;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by admin on 2016/7/29.
 * 单个数据的逻辑处理
 */
public class WXPayBillItemProcesser implements ItemProcessor<PayBillItem, PayBillItem> {

    @Override
    public PayBillItem process(PayBillItem item) throws Exception {
        //TODO processer item
        return item;
    }
}
