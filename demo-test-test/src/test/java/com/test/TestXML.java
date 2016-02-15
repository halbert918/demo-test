package com.test;

import com.framework.job.Job;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by min on 2015/3/19.
 */
public class TestXML {

    @Test
    public void xmlTest() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/test.xml");
            Job job = context.getBean("test", Job.class);
            job.print("hello test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
