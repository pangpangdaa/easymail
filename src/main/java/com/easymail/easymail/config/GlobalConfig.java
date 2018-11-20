package com.easymail.easymail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
public class GlobalConfig {

    @Value("${filepath}")
    private String folder;

    //存储邮件有没有更新过的状态
 //   private ConcurrentHashMap<String,Boolean> changedMap = new ConcurrentHashMap<String,Boolean>();
}
