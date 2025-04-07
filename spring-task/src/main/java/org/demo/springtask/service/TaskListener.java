//package org.demo.springtask.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.task.listener.TaskExecutionListener;
//import org.springframework.cloud.task.repository.TaskExecution;
//
/// /@Service
//public class TaskListener implements TaskExecutionListener {
//
//    private final static Logger log = LoggerFactory.getLogger(TaskListener.class);
//
//    public void onTaskStartup(TaskExecution taskExecution) {
//        log.info("task {} start up", taskExecution.getTaskName());
//    }
//
//    public void onTaskEnd(TaskExecution taskExecution) {
//        log.info("task {} end", taskExecution.getTaskName());
//    }
//
//    public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
//        log.info(" task fail {} ", taskExecution.getTaskName(), throwable);
//    }
//}
