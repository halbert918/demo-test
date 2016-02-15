package com.test.service.impl;

//import com.test.dao.AccountEntityDao;
//import com.test.dao.UserEntityDao;
import com.test.entity.AccountEntity;
import com.test.entity.UserEntity;
import com.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by min on 2015/1/28.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

//    @Autowired
//    private AccountEntityDao springDataJpaDao;
//
//    @Autowired
//    private UserEntityDao userEntityDao;
//
//    @Transactional
//    @Override
//    public void save(AccountEntity account) throws Exception {
//        springDataJpaDao.save(account);
//
//        System.out.print("=======================================");
//    }
//
//    @Override
//    public void deleteById(long id) {
//
//    }
//
//    @Override
//    public AccountEntity queryById(long id) {
//        return springDataJpaDao.findById(id);
//    }
//
//    @Override
//    public UserEntity queryByName(String name) {
//        UserEntity user = userEntityDao.findByName(name);
//        return user;
//    }
}

