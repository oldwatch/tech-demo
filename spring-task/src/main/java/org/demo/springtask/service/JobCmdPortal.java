package org.demo.springtask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

@Component
public class JobCmdPortal implements CommandLineRunner, ApplicationContextAware {


    private final JobLauncher jobLauncher;

    private final Job job;

    private final Logger log = LoggerFactory.getLogger(JobCmdPortal.class);

    private ApplicationContext applicationContext;

    public JobCmdPortal(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    private static String getCleanInput(BufferedReader reader) throws IOException {
        var input = reader.readLine();
        if (input == null || input.isBlank()) {
            return null;
        }
        input = input.trim().toLowerCase(Locale.ENGLISH);
        return input;
    }

    @Override
    public void run(String... args) throws Exception {

        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println(">>>>>>input command,or help:");

            try {
                var input = getCleanInput(reader);
                if (input == null) continue;
                if (input.equals("exit")) {
                    break;
                }

                var params = StringUtils.split(input, " ");
                if (params == null || params.length < 1) {
                    continue;
                }
                var job = params[0];
                log.info(" job {} ", job);
                handle(params);

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

        }
        reader.close();
        System.exit(SpringApplication.exit(applicationContext, () -> 0)); // Exit with code

    }

    public void handle(String[] params) throws Exception {
        jobLauncher.run(job, new JobParameters());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
