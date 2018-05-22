package com.yougou.sso.controller;

import com.alibaba.fastjson.JSONObject;
import com.yougou.common.pojo.YougouResult;
import com.yougou.common.utils.CookieUtils;
import com.yougou.common.utils.HttpClientUtil;
import com.yougou.common.utils.JsonUtils;
import com.yougou.pojo.TbUser;
import com.yougou.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户处理控制层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.sso.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/19 16:45
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class UserController {

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${CART_KEY}")
    private String CART_KEY;//购物车在cookie中保存的key
    @Value("${CART_BASE_URL}")
    private String CART_BASE_URL;//单点登录系统请求地址
    @Value("${CART_INTERFACE_URL}")
    private String CART_INTERFACE_URL;//单点登录系统通过token获取用户信息接口地址

    @Autowired
    private UserService userService;


    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public YougouResult checkUserData(
            @PathVariable String param, @PathVariable Integer type) {
        YougouResult result = userService.checkData(param, type);
        return result;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public YougouResult register(TbUser user) {
        YougouResult result = userService.register(user);
        return result;
    }

    @RequestMapping(value = "/user/logout/{token}",method = RequestMethod.GET)
    public ModelAndView logout(@PathVariable String token){
        YougouResult result = userService.logout(token);
        //跳到登录首页
        return new ModelAndView("redirect:/page/showLogin");
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public YougouResult login(String username,
                              String password,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        YougouResult result = userService.login(username, password);
        if(result.getStatus() == 200 || "ok".equals(result.getMsg())) {
            //把token写入cookie
            CookieUtils.setCookie(request, response, TOKEN_KEY,
                    result.getData().toString());
            //获取本地cookie购物车
            String cookieCartJson = CookieUtils.getCookieValue(request, CART_KEY, true);
            //如果本地cookie中购物车为空,结束程序返回结果
            if ("[]".equals(cookieCartJson) || StringUtils.isBlank(cookieCartJson))
                return result;
            //跨域请求购物车系统，将本地cookie中购物车同步到redis
            Map map = new HashMap();
            map.put("data",cookieCartJson);
            //跨域请求将本地cookie中购物车同步到redis
            String yougouResult = HttpClientUtil.doPost(CART_BASE_URL + CART_INTERFACE_URL + result.getData().toString(), map);
            if (StringUtils.isBlank(yougouResult)) {
                result.build(400, "用户登录异常！");
                return result;
            }
            JSONObject jsonObj = JSONObject.parseObject(yougouResult);
            String status = jsonObj.getString("status");
            if (StringUtils.isBlank(status) || !"200".equals(status)) {
                result.build(400, "用户登录异常！");
                return result;
            }
            //同步成功后,删除本地cookie购物车
            CookieUtils.deleteCookie(request, response,CART_KEY);
        }
        return result;
    }

    //jsonp的第一种方法
    /*@RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//produces指定返回响应数据的content-type
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        YougouResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if ( StringUtils.isNotBlank(callback) ) {
            return callback + "(" + JsonUtils.objectToJson(result) + ");";
        }
        return JsonUtils.objectToJson(result);
    }*/
    //jsonp的第二种实现方法
    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        YougouResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if ( StringUtils.isNotBlank(callback) ) {
            MappingJacksonValue mappingJacksonValue  = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
