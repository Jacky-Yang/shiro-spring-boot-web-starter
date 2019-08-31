package com.jakcy.example.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component("authorizer") // "authorizer" is important
public class ShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = token.getPrincipal().toString();
        String pwd = token.getCredentials().toString();
        System.out.println(pwd);
        String password = new String((char[]) token.getCredentials());
        if (!"admin".equals(name) || !"admin".equals(password)) {
            throw new UnknownAccountException();
        }
        String realmName = getName();
        System.out.println("realmName:" + realmName);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(name, password, realmName);
        return authenticationInfo;
    }
}
