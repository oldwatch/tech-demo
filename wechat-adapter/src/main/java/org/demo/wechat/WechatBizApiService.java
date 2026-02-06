package org.demo.wechat;

import org.demo.wechat.aop.ApiService;
import org.demo.wechat.aop.HttpMethodTypes;
import org.demo.wechat.aop.WeChatAPI;

public interface WechatBizApiService extends ApiService {

    @WeChatAPI(method = HttpMethodTypes.GET, path = "/cgi-bin/get_api_domain_ip")
    public WeChatBusinessService.IpList getIpList();

    @WeChatAPI(method = HttpMethodTypes.GET, path = "/wxa/getwxadevinfo")
    public WeChatBusinessService.DomainQueryResult queryBizDomainInfo(WeChatBusinessService.DomainQueryInput input);
}
