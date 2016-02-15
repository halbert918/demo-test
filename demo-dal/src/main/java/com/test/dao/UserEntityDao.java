package com.test.dao;

import com.test.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Created by min on 2015/1/23.
 */
//public interface UserEntityDao extends Repository<UserEntity, Long> {
//
//
////    String sql = "select u.id, u.name, u.age, u.sex, a.account_no from user u, account a where u.name = a.name and u.name = :name";
////    @Query("select u from UserEntity u where u.name = :name")
//    public UserEntity findByName(@Param("name")String name);
//
//}
