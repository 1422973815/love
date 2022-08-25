package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MsgTemplate implements Serializable {
    private String touser;
    private String template_id;
    private com.tencent.wxcloudrun.dto.Data data;
}
