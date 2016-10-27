package com.test.dubbo.provider.impl;

import com.test.dubbo.order.TestOrder;
import com.test.dubbo.provider.TestService;
import org.springframework.stereotype.Component;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/2/3
 * @Description
 */
@Component("testService")
public class TestServiceImpl implements TestService {

    @Override
    public String operOrder(TestOrder order) {
        return "success";
    }
}
