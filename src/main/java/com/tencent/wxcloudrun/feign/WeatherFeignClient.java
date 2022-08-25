package com.tencent.wxcloudrun.feign;

import com.tencent.wxcloudrun.dto.MsgTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(url = "http://wthrcdn.etouch.cn",name = "love")
public interface WeatherFeignClient {
    @GetMapping("/weather_mini?city=锡林浩特市")
    String getWeather();
}