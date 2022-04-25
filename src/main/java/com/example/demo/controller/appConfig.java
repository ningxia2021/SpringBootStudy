package com.example.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
public class appConfig implements WebMvcConfigurer {

    /**
     * 2022.04.25 自定义拦截器
     * 自定义后端路径映射
     * 实现configurePathMatch方法
     * 添加统一的服务路径前缀
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api",c->true);
    }

//    配置拦截器的拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        注册拦截器实例.设置路由配置规则
        /**
         * addPathPatterns("/**")                     拦截所有路由
         * excludePathPatterns("/api/user/index1")     除了index1
         * 效果：只有index1可以被访问，其他地址均无响应
         */
        registry.addInterceptor(new loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/api/user/index1");   // '/**'表示拦截所有路径
    }
}
