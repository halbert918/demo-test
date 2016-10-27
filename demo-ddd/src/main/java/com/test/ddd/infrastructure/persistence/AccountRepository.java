package com.test.ddd.infrastructure.persistence;

import com.test.ddd.domain.model.Account;

/**
 * Created by admin on 2016/9/14.
 */
public interface AccountRepository {

    /**
     * 根据用户ID查询账户信息
     * @param userId
     * @return
     */
    Account findByUserId(String userId);

    /**
     * 更新账户信息
     * @param account
     */
    void update(Account account);

}
