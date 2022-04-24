package com.example.demo.controller;
/**
 * 2022.04.24
 * 一些常用注解的使用
 */

import com.example.demo.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/user")
public class userController {

    /**
     * Spring 框架可以将返回的对象自动转换为Json对象
     *  这就省去了对Jackson的操作
     */
    @RequestMapping("/getuserjson")
    @ResponseBody
    public User getUserJson(){
        User user = new User();
        user.setPassword("8513246");
        user.setUsername("亭朝廷");
        return user;
    }

    /**
     * 注解 @ResponseBody 返回类型为自定义格式的数据，而非视图。一般就是为了返回json格式的数据
     */
    @Autowired //注入bean
    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/index1")
    @ResponseBody
    public String getIndex() throws JsonProcessingException {
        User user = new User();
        user.setUsername("张三");
        user.setPassword("123456");
//        对象转Json字符串
        String s = objectMapper.writeValueAsString(user);
//        打印至控制台
        log.info(s);
//        可以看到返回的是字符串 而不是网页内容
        return "/demo1.html";
    }

    /**
     * 不加 @ResponseBody 返回html页面
     */
    @RequestMapping("/index2")
    public String getIndex2(){
//        返回的是html内容
        return "/新建事项.html";
    }

    /**
     * @PathVariable
     * 提取url中的变量映射到参数中
     */
    @ResponseBody
    @RequestMapping("/username/{name}/password/{password}")
//    可以取变量名字作为参数名  也可以通过指定变量来重新给参数命名
    public String getVariable(@PathVariable String name,@PathVariable("password") String ps){
        return "用户名 ： "+ name + " , 密码 : "+ ps;
    }
}
