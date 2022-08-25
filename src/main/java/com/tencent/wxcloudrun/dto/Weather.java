package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Weather implements Serializable {
    private String value;
    private String color;
}
