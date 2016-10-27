package com.test.batch.simple;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

/**
 * Created by admin on 2016/7/29.
 */
public class JobLaunch {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("helloWorldJob");
        try {
            for(int i = 0; i < 1; i++) {
            /* 运行Job */
                JobExecution result = launcher.run(job, new JobParametersBuilder()
                        .addString("test", "value1_" + i)
                        .addString("payWay", "alipay")
                        .toJobParameters());
            /* 处理结束，控制台打印处理结果 */
                System.out.println(result.getExitStatus().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
