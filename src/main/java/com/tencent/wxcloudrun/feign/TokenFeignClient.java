package com.tencent.wxcloudrun.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(url = "https://api.weixin.qq.com/cgi-bin",name = "love")
public interface TokenFeignClient {
    @GetMapping("/token?grant_type=client_credential&appid=wx784a0719607b6b0e&secret=1002240ccc4ae1ea338fc4d711a820b9")
    String getToken();
}