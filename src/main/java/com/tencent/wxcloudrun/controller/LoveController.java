package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.feign.LoveFeignClient;
import com.tencent.wxcloudrun.feign.TokenFeignClient;
import com.tencent.wxcloudrun.feign.WeatherFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@RestController
public class LoveController {
    @Autowired
    private LoveFeignClient loveFeignClient;
    @Autowired
    private WeatherFeignClient weatherFeignClient;
    @Autowired
    private TokenFeignClient tokenFeignClient;

    @Scheduled(cron = "0 0 9 * * ?")
    public void love() throws Exception {
        // 准备
        MsgTemplate msgTemplate = new MsgTemplate();
        msgTemplate.setTouser("oBmu45qCAhlMxYGds5x3QLzQXCaA");
        msgTemplate.setTemplate_id("UDBDFGsX3Whae-p7NLzRTlaCZ4QdIS1Cu5In7nI8HAk");

        Data data = new Data();
        District district = new District();// 地区
        Weather weather = new Weather();// 天气
        MinTemperature minTemperature = new MinTemperature();// 最低气温
        MaxTemperature maxTemperature = new MaxTemperature();// 最高气温
        NowTemperature nowTemperature = new NowTemperature();// 当前气温
        GanmaoTemperature ganmaoTemperature = new GanmaoTemperature();// 小提示
        LoveDays loveDays = new LoveDays();// 恋爱天数
        GirlBirthday girlBirthday = new GirlBirthday();// 女方生日
        BoyBirthday boyBirthday = new BoyBirthday();// 男方生日
        GraduateDays graduateDays = new GraduateDays();// 考研倒计时
        MenstruationDays menstruationDays = new MenstruationDays();// 月经倒计时
        MenstruationMsg menstruationMsg = new MenstruationMsg();// 月经提示
        User user = new User();// 名字

        // 核心代码
        // 地区
        district.setValue("锡林浩特市");
        district.setColor("#017f8b");
        // 获取天气信息
        String weatherJson = weatherFeignClient.getWeather();
        JSONObject jsonObject = JSON.parseObject(weatherJson);
        JSONObject dataa = jsonObject.getJSONObject("data");
        JSONArray forecast = dataa.getJSONArray("forecast");
        // 天气
        weather.setValue(forecast.getJSONObject(0).getString("type"));
        weather.setColor("#017f8b");
        // 最低气温
        minTemperature.setValue(forecast.getJSONObject(0).getString("low"));
        minTemperature.setColor("#017f8b");
        // 最高气温
        maxTemperature.setValue(forecast.getJSONObject(0).getString("high"));
        maxTemperature.setColor("#017f8b");
        // 当前气温
        nowTemperature.setValue(dataa.getString("wendu"));
        nowTemperature.setColor("#017f8b");
        // 小提示
        ganmaoTemperature.setValue(dataa.getString("ganmao"));
        ganmaoTemperature.setColor("#6469e1");
        // 恋爱天数
        loveDays.setValue(loveDays()+"");
        loveDays.setColor("#b31e1a");
        // 女方生日
        girlBirthday.setValue(getBirthDay("1999-05-18")+"");
        girlBirthday.setColor("#b31e1a");
        // 男方生日
        boyBirthday.setValue(getBirthDay("1999-12-23")+"");
        boyBirthday.setColor("#4491e1");
        // 考研倒计时
        graduateDays.setValue(getBirthDay("2022-12-24")+"");
        graduateDays.setColor("#fe724f");
        // 月经倒计时
        int num = getMenstruation(14);
        if (num==0) {
            menstruationMsg.setValue("今天愿望通通实现！！");
            menstruationMsg.setColor("#ff7faf");
        }
        menstruationMsg.setValue("宝贝，多喝热水");
        menstruationMsg.setColor("#ff7faf");
        menstruationDays.setValue(num+"");
        menstruationDays.setColor("#ff7faf");
        // 名字
        user.setValue("萱萱宝贝");
        user.setColor("#b31e1a");

        // 封装
        data.setDistrict(district);
        data.setWeather(weather);
        data.setMinTemperature(minTemperature);
        data.setMaxTemperature(maxTemperature);
        data.setLoveDays(loveDays);
        data.setGirlBirthday(girlBirthday);
        data.setBoyBirthday(boyBirthday);
        data.setGraduateDays(graduateDays);
        data.setMenstruationDays(menstruationDays);
        data.setUser(user);
        data.setNowTemperature(nowTemperature);
        data.setGanmaoTemperature(ganmaoTemperature);
        data.setMenstruationMsg(menstruationMsg);
        msgTemplate.setData(data);
        // 获取token
        String token = tokenFeignClient.getToken();
        JSONObject tokenJson = JSON.parseObject(token);
        String accessToken = tokenJson.get("access_token").toString();
        // 发送
        loveFeignClient.love(msgTemplate, accessToken);

//        msgTemplate.setTouser("oBmu45mPF_v6l1uyY-sZRcBPrZxk");
//        String love = loveFeignClient.love(msgTemplate, accessToken);
//        JSONObject loveJson = JSON.parseObject(love);
//        System.out.println("errcode："+loveJson.get("errcode").toString());
//        System.out.println("errmsg："+loveJson.get("errmsg").toString());
    }

    /**
     * 恋爱天数
     * @return
     * @throws ParseException
     */
    public static int loveDays() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse("20220817"));
        long start = calendar.getTimeInMillis();
        calendar.setTime(sdf.parse(sdf.format(date)));
        long end = calendar.getTimeInMillis();
        long betweendays=(end-start)/(1000*3600*24);
        int days = Integer.parseInt(String.valueOf(betweendays))+1;
        return days;
    }

    /**
     * 计算日期倒计时
     * @param addtime
     * @return
     */
    public static int getBirthDay(String addtime) {
        int days = 0;
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String clidate = addtime;
            Calendar cToday = Calendar.getInstance(); // 存今天
            Calendar cBirth = Calendar.getInstance(); // 存生日
            cBirth.setTime(myFormatter.parse(clidate)); // 设置生日
            cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR)); // 修改为本年
            if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
                // 生日已经过了，要算明年的了
                days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
                days += cBirth.get(Calendar.DAY_OF_YEAR);
            } else {
                // 生日还没过
                days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 月经倒计时
     * @return
     */
    public int getMenstruation(int day) {
        int days = 0;
        Calendar calendar = Calendar.getInstance();
        int maxDate = calendar.getActualMaximum(Calendar.DATE);
        int date = calendar.get(Calendar.DATE);
        if (day==date) {
            days = 0;
            return days;
        }else if (day>date) {
            days = day-date;
            return days;
        }else if (day<date) {
            days = maxDate-date+day;
            return days;
        }
        return days;
    }
}
