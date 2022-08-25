package com.tencent.wxcloudrun.dto;

import java.io.Serializable;

@lombok.Data
public class Data implements Serializable {
    private User user;
    private BoyBirthday boyBirthday;
    private District district;
    private GirlBirthday girlBirthday;
    private GraduateDays graduateDays;
    private LoveDays loveDays;
    private MaxTemperature maxTemperature;
    private MenstruationDays menstruationDays;
    private MinTemperature minTemperature;
    private Weather weather;
    private NowTemperature nowTemperature;
    private GanmaoTemperature ganmaoTemperature;
    private MenstruationMsg menstruationMsg;
}
