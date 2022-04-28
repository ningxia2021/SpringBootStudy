package com.example.demo.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * [用户拦截器] 实现用户的授权 (登录的用户才能正常访问，否则只能先去登录或注册)
 *  1.创建一个拦截器的类，实现 HandlerInterceptor 接口，重写 preHandle方法(其中 用户自定义session 判断方法)  [怎样拦截]
 *  2.业务代码实现后，再去SpringMVC 的配置类中设置拦截规则（new实例，拦截谁）
 */
// 拦截器的业务代码
public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session!=null && session.getAttribute("user")!=null){
//            用户已经登陆
            return true;
        }
        return false;
    }
}
