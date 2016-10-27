package com.test.ddd.domain.service;

import com.test.ddd.domain.model.Account;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/9/14.
 */
public interface AccountService {
    /**
     *
     * @param userId
     * @param withdrawAmount
     * @return
     */
    Account withdraw(String userId, BigDecimal withdrawAmount);

}
