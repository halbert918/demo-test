package com.test.dubbo.custom.impl;

import com.test.dubbo.custom.TestCustom;
import com.test.dubbo.order.TestOrder;
import com.test.dubbo.provider.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/2/14
 * @Description
 */
@Service
public class TestCustomImpl implements TestCustom {

    @Resource(name = "testService1")
    private TestService testService1;

    @Override
    public String operOrder() {
        TestOrder order = new TestOrder();
        order.setId("1232");
        order.setName("testOrder");
        testService1.operOrder(order);
        return null;
    }
}
