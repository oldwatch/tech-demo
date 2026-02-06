package wechat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = org.demo.wechat.ApiLoaderConfig.class)
@TestPropertySource("classpath:application-unittest.properties")
public class TestWeChatApi {


    private static final String TOKEN = "94_6fzJeR9ctCySeqCcq3urySxjwmgtcPGZ1uYyOY68kbK7_PFchnkavbE0lO-F_9yZQHy5feq9eCUdIwT1ziexe_6pCFMVh3fhAApReR0e7Rcs3VPOMQ3NuHF6jFEDOUjAEAPAG";
    private Logger log = LoggerFactory.getLogger(TestWeChatApi.class);
    @Autowired
    private WeChatBusinessService service;
    @Autowired
    private WeChatTokenService tokenService;
    @Autowired
    private TokenStore tokenStore;

    @Test
    public void testService() {


        var list = service.getIpList();

        log.info("ip list:{}", list);
    }

    @Test
    public void testServiceWithMockToken() {

        log.debug("start testing");

        tokenStore.settingToken(TOKEN);

        var list = service.getIpList();

        log.info("ip list:{}", list);

    }

    @Test
    public void testServicePostWithMockToken() {

        log.debug("start testing");

        tokenStore.settingToken(TOKEN);

        var list = service.queryBizDomainInfo();

        log.info("ip list:{}", list);

    }
}
