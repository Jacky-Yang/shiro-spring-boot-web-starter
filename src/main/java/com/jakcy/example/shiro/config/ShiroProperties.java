package com.jakcy.example.shiro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @project: spring-boot-shiro-redis
 * @since: v1.0
 * @author: yangjie
 * @date: 2019/8/30 17:24
 */
@ConfigurationProperties(prefix = "system.shiro")
@Component
public class ShiroProperties {

    private List<String> whiteList;

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
