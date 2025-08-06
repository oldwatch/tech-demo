package com.demo.dubbo.client;

import com.demo.dubbo.service.DemoService;
import com.demo.dubbo.service.entity.DemoEntity;
import com.demo.dubbo.service.entity.DemoInput;
import com.demo.dubbo.service.entity.Pager;
import org.apache.dubbo.common.constants.ClusterRules;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DemoClient {

    private Logger log = LoggerFactory.getLogger(DemoClient.class);

    @DubboReference(cluster = ClusterRules.FAIL_BACK, retries = 3)
    private DemoService service;


    public DemoEntity addEntity(String name, int val) {
        var input = new DemoInput(name, LocalDateTime.now(), val, val * 0.01f);


        try {
            return service.addData(input);
        } catch (RpcException e) {
            log.error(" call add data fail ", e);
            throw new ServiceException(e);
        }
    }

    public List<DemoEntity> getList() {
        try {
            return service.getDataListByPage(new Pager("", 0, 0));
        } catch (RpcException e) {
            log.error(" call add data fail ", e);
            throw new ServiceException(e);
        }
    }

    public static class ServiceException extends RuntimeException {
        public ServiceException(Exception e) {
            super(e);
        }
    }
}
