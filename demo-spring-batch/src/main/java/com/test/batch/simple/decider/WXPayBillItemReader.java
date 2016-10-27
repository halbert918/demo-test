package com.test.batch.simple.decider;

import com.test.batch.simple.domian.PayBillItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Date;

/**
 * Created by heyinbo on 2016/8/17.
 * 数据读取的具体实现
 *
 */
public class WXPayBillItemReader extends PayBillItemReader<PayBillItem> {

    private String id;
    /**
     * 下载日期
     */
    private Date loadDate;
    /**
     * 支付渠道
     */
    private String payWay;

    @Override
    public void doDownLoad() {
        //TODO 下载对账单
    }

    @Override
    public PayBillItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return doReader();
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
}
