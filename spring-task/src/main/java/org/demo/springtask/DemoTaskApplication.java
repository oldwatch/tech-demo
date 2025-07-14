package org.demo.springtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@SpringBootApplication
//@EnableTask
@EnableBatchProcessing(databaseType = "PostgreSQL")
public class DemoTaskApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoTaskApplication.class);

    public static void main(String[] args) {
        var app = new SpringApplication(DemoTaskApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }


    @Bean
    public TaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository, @Qualifier("asyncTaskExecutor") TaskExecutor executor) {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(executor);
        try {
            jobLauncher.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jobLauncher;
    }

    @Bean
    public Job job(JobRepository jobRepository, @Qualifier("firstStep") Step firstStep) {
        return new JobBuilder("demo-job", jobRepository)
                .start(firstStep)
                .build();
    }
    
}
