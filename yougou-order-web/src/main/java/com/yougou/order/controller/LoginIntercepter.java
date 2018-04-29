package com.yougou.order.controller;

import com.yougou.common.pojo.YougouResult;
import com.yougou.common.utils.CookieUtils;
import com.yougou.pojo.TbUser;
import com.yougou.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断用户是否登录拦截器接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.order.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/21 17:44
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class LoginIntercepter implements HandlerInterceptor {

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${SSO_URL}")
    private String SSO_URL;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object o) throws Exception {
        //执行handler之前先执行此方法
        //1、从cookie中取token信息
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        //2、如果取不到token，跳转到sso的登录页面，需要把当前请求的url作为参数传递给sso,sso登录成功之后再跳转回请求页
        if (StringUtils.isBlank(token)) {
            //取当前请求url
            String url = request.getRequestURL().toString();
            //跳转到登录页面
            response.sendRedirect(SSO_URL + "/page/showLogin?url=" + url);
            //拦截
            return false;
        }
        //3、拿到token，调用sso系统的服务判断用户是否登录
        YougouResult result = userService.getUserByToken(token);
        //4、如果用户未登录，即没取到用户信息，跳转sso登录页面
        if (result.getStatus() != 200) {
            //取当前请求url
            String url = request.getRequestURL().toString();
            //跳转到登录页面
            response.sendRedirect(SSO_URL + "/page/showLogin?url=" + url);
            //拦截
            return false;
        }
        //5、如果取到用户信息，放行
        //把用户信息放到request中
        TbUser user = (TbUser) result.getData();
        request.setAttribute("user", user);
        //返回值true：放行,返回false：拦截
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o,
                           ModelAndView modelAndView) throws Exception {
        //handler执行之后，modelAndView返回之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o,
                                Exception e) throws Exception {
        //在ModelAndView返回之后，异常处理
    }
}
