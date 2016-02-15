package com.test;

import com.test.entity.AccountEntity;
import com.test.entity.UserEntity;
import com.test.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

/**
 * Created by min on 2015/1/28.
 */
public class SpringDataJPATest {

    @Test
    public void saveTest() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-demo-cfg.xml");
            AccountService accountService = context.getBean("accountService", AccountService.class);
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setAccountNo("201502041528");
            accountEntity.setName("halbert");
            accountService.save(accountEntity);
            System.out.println("Spring Data JPA Save Test Success!");

            AccountEntity queryAccount = accountService.queryById(3);
            System.out.println(queryAccount.getId() + " | " + queryAccount.getName() + " | " + queryAccount.getAccountNo());
            DataSource dataSource = (DataSource) context.getBean("dataSource");

            System.out.print("===================================");

            UserEntity user = accountService.queryByName("hyb");

            System.out.print("========================" + user);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
