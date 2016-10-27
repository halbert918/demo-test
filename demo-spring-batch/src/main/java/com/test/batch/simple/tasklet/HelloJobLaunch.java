package com.test.batch.simple.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by admin on 2016/7/29.
 */
public class HelloJobLaunch {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch-simple.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("helloJob");
        try {
            /* 运行Job */
            JobExecution result = launcher.run(job, new JobParametersBuilder()
                    .toJobParameters());
            /* 处理结束，控制台打印处理结果 */
            System.out.println(result.getExitStatus().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
