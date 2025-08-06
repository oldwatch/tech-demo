package org.demo.wechat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = org.demo.wechat.WeChatApiConfig.class)
//@SpringJUnitConfig
public class TestWeChatApi {


    private Logger log = LoggerFactory.getLogger(TestWeChatApi.class);

    @Autowired
    private WeChatBusinessService service;

    @Test
    public void testService() {

        var list = service.getIpList();

        log.info("ip list:{}", list);

    }
}
