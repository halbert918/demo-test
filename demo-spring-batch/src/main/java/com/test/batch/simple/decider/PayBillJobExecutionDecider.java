package com.test.batch.simple.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * Created by heyinbo on 2016/8/17.
 * 根据JobParameters传入的值决策流程执行分支
 */
public class PayBillJobExecutionDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String payWay = jobExecution.getJobParameters().getString("payWay");
        return new FlowExecutionStatus(payWay);
    }
}
