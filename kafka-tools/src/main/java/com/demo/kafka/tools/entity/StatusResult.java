package com.demo.kafka.tools.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record StatusResult(String text, DataEntity.Status status, int id) {

    private final static Logger log = LoggerFactory.getLogger(StatusResult.class);

    public StatusResult(DataEntity entity1, DataEntity entity2) {

        log.info("construct Status result:{} {}", entity1, entity2);

        var status = DataEntity.Status.Operate;
        if (entity2 == null) {
            status = DataEntity.Status.Error;
        } else {
            status = entity2.status();
        }

        var text = "";
        var seq = -1;
        if (entity1 != null) {
            text = entity1.text();
            seq = entity1.seq();
        }
        this(text, status, seq);
    }
}
