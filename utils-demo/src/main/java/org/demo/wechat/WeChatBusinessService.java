package org.demo.wechat;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class WeChatBusinessService {
    private static final String hostPrimate = "https://api.weixin.qq.com";
    private static final String hostSecond = "https://api2.weixin.qq.com";
    private static final String prefix = "/cgi-bin";
    private static final String businessPath = "/get_api_domain_ip";
    private final TokenStore store;
    private final RestTemplate template;

    public WeChatBusinessService(TokenStore store, RestTemplate template) {
        this.store = store;
        this.template = template;
    }

    public IpList getIpList() {
        var params = new HashMap<>();
        params.put("access_token", store.getCurrentToken());
        var resp = template.getForEntity(prefix + businessPath, IpList.class, params);
        return resp.getBody();

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record IpList(String[] ipList,
                         @JsonUnwrapped ErrorInfo err) {

    }

}
