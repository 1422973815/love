package com.tencent.wxcloudrun.feign;

import com.tencent.wxcloudrun.dto.MsgTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(url = "https://api.weixin.qq.com/cgi-bin/message/template",name = "love")
public interface LoveFeignClient {
    @PostMapping("/send?access_token={token}")
    String love(@RequestBody MsgTemplate msgTemplate,@PathVariable("token") String token);

}