package org.demo.wechat;

import org.demo.wechat.aop.ApiLoaderConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApiLoaderConfig.class)
@TestPropertySource("classpath:application-unittest.properties")
public class TestAopService {

    private final Logger log = LoggerFactory.getLogger(TestAopService.class);

    //    @Lazy
    @Autowired
    private WechatBizApiService service;

    @Test
    public void testService() {


        var list = service.getIpList();

        log.info("ip list:{}", list);
    }
}
