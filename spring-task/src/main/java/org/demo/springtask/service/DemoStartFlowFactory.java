package org.demo.springtask.service;

import module spring.batch.core;
import module spring.beans;
import module spring.context;
import org.springframework.transaction.PlatformTransactionManager;

//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DemoStartFlowFactory {

    private final DemoTaskService task;

    public DemoStartFlowFactory(DemoTaskService task) {
        this.task = task;
    }


    @Bean
    public Job demoJob(@Qualifier("firstStep") Step step, JobRepository jobRepository) {
        return new JobBuilder("sampleJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sampleStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
//                .reader(itemReader())
//                .writer(itemWriter())
                .build();

    }
}

