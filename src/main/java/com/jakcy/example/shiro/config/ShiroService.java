package com.jakcy.example.shiro.config;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @project: spring-boot-shiro-redis
 * @since: v1.0
 * @author: yangjie
 * @date: 2019/8/30 17:27
 */
@Service
public class ShiroService {

    private ShiroProperties properties;

    public ShiroService(ShiroProperties properties) {
        this.properties = properties;
    }
    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //从配置文件中读取白名单
        final List<String> whiteList = properties.getWhiteList();

        for (String url : whiteList) {
            filterChainDefinitionMap.put(url, "anon");
        }

        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");

        // 所有请求通过我们自己的JWT Filter
//        filterChainDefinitionMap.put("/**", "jwt");

        System.out.println("filterChainDefinitionMap:" + filterChainDefinitionMap);

        return filterChainDefinitionMap;
    }

}
