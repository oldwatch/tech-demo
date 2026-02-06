package wechat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringJUnitConfig
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = org.demo.wechat.WeChatApiConfig.class)
public class TestRestClient {

    private Logger log = LoggerFactory.getLogger(TestRestClient.class);
    @Autowired
    private RestTemplate template;

    @Test
    public void testToken() {

        var reqParam = new WeChatTokenService.TokenReq("apiKey", "apiSecret");

        var url = UriComponentsBuilder.newInstance().scheme("https").host("httpbin.org")
                .path("/anything")
                .queryParam("access_token", "_mock_token_").build();


        String response = template
                .postForObject(url.toUri().toString(), reqParam, String.class);


        log.info("body :{}", response);
    }

    @Configuration
    static class Config {

        // this bean will be injected into the OrderServiceTest class
        @Bean
        RestTemplate restTemplate() {

            var template = new RestTemplate();
            var factory = new HttpComponentsClientHttpRequestFactory();
            template.setRequestFactory(factory);
            return template;
        }
    }


}
