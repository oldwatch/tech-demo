package org.demo.wechat;

import module java.base;
import module org.slf4j;
import module spring.web;
import module tools.jackson.databind;

public class WeChatBusinessService {

    private static final String hostPrimate = "api.weixin.qq.com";
    private static final String hostSecond = "api2.weixin.qq.com";
    private static final String prefix = "/cgi-bin";
    private static final String businessPath = "/get_api_domain_ip";
    private static final String queryDomainPath = "/wxa/getwxadevinfo";
    private final Logger log = LoggerFactory.getLogger(WeChatTokenService.class);
    private final TokenStore store;
    private final RestTemplate template;

    public WeChatBusinessService(TokenStore store, RestTemplate template) {
        this.store = store;
        this.template = template;
    }

    public IpList getIpList() {

        var url = UriComponentsBuilder.newInstance()
                .scheme("https").host(hostPrimate).pathSegment(prefix, businessPath).queryParam("access_token", store.getCurrentToken()).build();

        return template.getForObject(url.toUriString(), IpList.class);

    }


    public DomainQueryResult queryBizDomainInfo() {

        var url = UriComponentsBuilder.newInstance()
                .scheme("https").host(hostPrimate).pathSegment(queryDomainPath).queryParam("access_token", store.getCurrentToken()).build();

        var input = new DomainQueryInput("getbizdomain");
        return template.postForObject(url.toUriString(), input, DomainQueryResult.class);

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record IpList(List<String> ipList,
                         @JsonUnwrapped ErrorInfo err) {

    }

    public record DomainQueryInput(String action) {
    }

    @JsonNaming(PropertyNamingStrategies.LowerCaseStrategy.class)
    public record DomainQueryResult(List<String> requestDomain,
                                    List<String> wsRequestDomain,
                                    List<String> uploadDomain,
                                    List<String> downloadDomain,
                                    List<String> udpDomain,
                                    @JsonUnwrapped ErrorInfo err) {

    }

    /*

{
  "errcode": 0,
  "errmsg": "ok",
  "requestdomain": [
      "https://www.example.com"
  ],
  "wsrequestdomain": [
      "wss://www.qq.com"
  ],
  "uploaddomain": [],
  "downloaddomain": [
      "https://www.qq.com"
  ],
  "udpdomain": [
      "udp://www.example.com"
  ]
}
     */

}
