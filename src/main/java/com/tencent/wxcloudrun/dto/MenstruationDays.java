package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenstruationDays implements Serializable {
    // 月经
    private String value;
    private String color;
}
