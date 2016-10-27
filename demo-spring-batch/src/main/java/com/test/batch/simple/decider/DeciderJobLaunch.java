package com.test.batch.simple.decider;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by admin on 2016/7/29.
 */
public class DeciderJobLaunch {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch-decider.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("deciderJob");
        try {
            /* 运行Job */
            JobExecution result = launcher.run(job, new JobParametersBuilder()
                    .addString("id", "10010")
                    .addString("payWay", "weixin")
                    .addDate("loadDate", new Date())
                    .toJobParameters());
            /* 处理结束，控制台打印处理结果 */
            System.out.println(result.getExitStatus().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
