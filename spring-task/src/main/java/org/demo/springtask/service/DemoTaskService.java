package org.demo.springtask.service;

import module org.slf4j;
import org.springframework.stereotype.Component;

@Component
public class DemoTaskService {

    private Logger log = LoggerFactory.getLogger(DemoTaskService.class);

    public void doFirstTask() {
        log.info("execute task first");

    }


    public void doSecondTask() {
        log.info("execute task first");

    }
}
