package com.jakcy.example.shiro.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    private ShiroService shiroService;

    public ShiroConfig(ShiroService shiroService) {
        this.shiroService = shiroService;
    }

//    @Bean // 使用shiro-spring-boot-web-starter无需手动配置ShiroFilterFactoryBean
//    protected ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//        System.out.println("SecurityManager:" + securityManager);
//        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
//
//        filterFactoryBean.setSecurityManager(securityManager);
//        filterFactoryBean.setLoginUrl("/login.html");
//
//        return filterFactoryBean;
//    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinitions(shiroService.loadFilterChainDefinitions());
        return chainDefinition;
    }

    /**
     * 配置安全管理器，返回值必须是 SessionsSecurityManager，不能是SecurityManager，否则会报
     * The bean 'securityManager', defined in class path resource [org/apache/shiro/spring/config/web/autoconfigure/ShiroWebAutoConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource [com/jakcy/example/shiro/config/ShiroConfig.class] and overriding is disabled.
     * @param shiroRealm ShiroRealm
     * @return SessionsSecurityManager
     */
    @Bean
    public SessionsSecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);

        securityManager.setCacheManager(cacheManager());
        securityManager.setSessionManager(sessionManager());
        System.out.println("securityManager:" + securityManager);
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        System.out.println("sessionManager:" + sessionManager);
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    @Bean("sessionDAO")
    public SessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        System.out.println("redisSessionDAO:" + redisSessionDAO);
        return redisSessionDAO;
    }

    /**
     * 配置redis缓存管理器
     * @return CacheManager
     */
    @Bean
    protected CacheManager cacheManager() {
        RedisCacheManager cacheManager  = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        System.out.println("cacheManager:" + cacheManager);
        return cacheManager;
    }

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1:6379");
        // 配置过期时间
//        redisManager.setExpire(1800);
        redisManager.setTimeout(5000);
//        redisManager.setPassword(redisConfig.getPassword());

        System.out.println("redisManager:"+ redisManager);

        return redisManager;
    }
}
