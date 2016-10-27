package com.test.batch.simple.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by admin on 2016/7/29.
 */
public class FirstTasklet implements Tasklet {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
            throws Exception {
        System.out.println(message + ",first tasklet");
        return RepeatStatus.FINISHED;
    }
}
