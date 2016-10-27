package com.test.ddd.domain.service;

import com.test.ddd.domain.model.Account;
import com.test.ddd.infrastructure.persistence.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by admin
 * 领域服务层
 */
@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class)
    @Autowired
    public Account withdraw(String userId, BigDecimal withdrawAmount) {
        //获取账户信息
        Account account = accountRepository.findByUserId(userId);
        if(null == account) {
            throw new IllegalArgumentException("账户信息不存在");
        }
        //账户提现
        account.withdraw(withdrawAmount);
        //更新数据
        accountRepository.update(account);
        return account;
    }

}
