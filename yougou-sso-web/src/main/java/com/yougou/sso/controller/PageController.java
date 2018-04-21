package com.yougou.sso.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户登录和注册页面的控制层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.sso.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/20 14:32
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class PageController {

    @RequestMapping("/page/showRegister")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/page/showLogin")
    public String showLogin() {
        return "login";
    }

}
