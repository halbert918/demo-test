package com.test.ddd.facade;

import java.math.BigDecimal;

/**
 * facade层，对外提供接口层
 * Created by admin on 2016/9/14.
 */
public interface AccountFacade {

    /**
     * 提现
     * @param userId
     * @param withdrawAmont
     * @return
     */
    SimpleResult withdraw(String userId, BigDecimal withdrawAmont);

}
