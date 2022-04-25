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
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    @GetMapping("/username/{name}/password/{password}")
//    可以取变量名字作为参数名  也可以通过指定变量来重新给参数命名
    public String getVariable(@PathVariable String name,@PathVariable("password") String ps){
        return "用户名 ： "+ name + " , 密码 : "+ ps;
    }

    @ResponseBody
    @RequestMapping("/getParameter1")
//    @RequestBody 只可以接收POST请求发回来的参数 换句话说 只能从请求的body中查找对应的参数,这也是为什么采用query string的方式会报错的原因
    public String getParameter1(@RequestBody String name , String password){
        return "name + password = "+ name;
    }

    @RequestMapping("/getParameter2")
    @ResponseBody
//    @RequestParam 接收query string的方式传过来的参数
//    加上选项 (required = false) 表示参数不是必须要传递的
//    加上选项 name = ""  是为了将前端参数名字与后端参数的名字建立映射，防止因参数命名不同导致传参失败
//      @RequestParam(name = "前端传过来的参数名称") String password 将前端参数名字与后端参数的名字建立映射
    public String getParameter2(@RequestParam(required = false) String name , @RequestParam(name = "前端传过来的参数名称") String password){
        return "用户名 ： "+ name + " , 密码 : "+ password;
    }

    @RequestMapping("/getParameter3")
    @ResponseBody
    //前面说到，Spring已经内置了Json转字符串的功能，那么这一块就可以用对象作为参数来获取变量了
    public String getParameter3(User user){
        return "用户名 ： "+ user.getUsername() + " , 密码 : "+ user.getPassword();
    }

//    如果要求方法的参数为对象时，要求请求中的参数不能为空  为可以用@RequestBody来进行约束
//    用了@RequestBody 约束参数，那么请求中参数就必须要以Json的格式在body中出现.
    @RequestMapping("/getParameter4")
    @ResponseBody
    public String getParameter4(@RequestBody User user){
        return "用户名 ： "+ user.getUsername() + " , 密码 : "+ user.getPassword();
    }

//    课堂小案例
//    实现登录功能 version_1
    @RequestMapping("/getParameter5")
    @ResponseBody
    public String getParameter5(@RequestBody User user, HttpServletRequest request){
        // 获取session对象
        HttpSession session = request.getSession();

        if (user.getUsername().equals("root") && user.getPassword().equals("root")){
            // 添加sessionId
            session.setAttribute("user",user);
            return "登录成功";
        }else {
            return "登录失败";
        }
    }

    //    课堂小案例
    //    实现登录功能 version_2
    @RequestMapping("/getParameter6")
    @ResponseBody
    public String getParameter6(@RequestBody User user, HttpServletRequest request){
        // 获取session对象
        HttpSession session = request.getSession(true);
        if (session==null){
            if (user.getUsername().equals("root") && user.getPassword().equals("root")){
                // 添加sessionId
                session.setAttribute("user",user);
                return "登录成功";
            }else {
                return "登录失败";
            }
        }
        User user1 = (User)session.getAttribute("user");
        return user1.getUsername()+"已经登录";
    }
}
