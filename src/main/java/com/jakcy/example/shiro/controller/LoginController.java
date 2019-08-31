package com.jakcy.example.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @PostMapping("/login")
    public String login(String username, String password) {
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        try {
            final Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            subject.getSession().setAttribute("username", username);
        } catch (Exception e) {
            e.printStackTrace();
            return "500.html";
        }
        return "/index";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();

        return "/login";
    }
}
