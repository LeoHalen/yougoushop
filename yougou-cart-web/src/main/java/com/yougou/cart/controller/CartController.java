package com.yougou.cart.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yougou.common.pojo.YougouResult;
import com.yougou.common.utils.CookieUtils;
import com.yougou.common.utils.HttpClientUtil;
import com.yougou.common.utils.JsonUtils;
import com.yougou.jedis.JedisClient;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbUser;
import com.yougou.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车控制层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.cart.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/21 10:21
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class CartController {

    @Value("${CART_KEY}")
    private String CART_KEY;//购物车在cookie中保存的key
    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;//cookie购物车商品的有效期,默认为7天
    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;//单点登录系统请求地址
    @Value("${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;//单点登录系统通过token获取用户信息接口地址
    @Value("${REDIS_CART_KEY}")
    private String REDIS_CART_KEY;//购物车在redis中保存的key
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;//token在cookie中保存的key
    @Value("${REDIS_CART_EXPIRE}")
    private Integer REDIS_CART_EXPIRE;//redis购物车商品的有效期,默认为30天s
    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Autowired
    private ItemService itemService;
    @Autowired
    private JedisClient jedisClient;

    /**
     * 添加 商品到购物车
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId,
                              @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        //跨域请求获取用户信息
        YougouResult result = getUserInfoJsonp(request);
        //取购物车商品列表
        List<TbItem> cartItemList = this.getCartItemList(request, result);
        //判断商品在购物车中是否存在
        boolean flag = false;
        for (TbItem tbItem : cartItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                //如果存在修改数量
                tbItem.setNum(tbItem.getNum() + num);
                flag = true;
                break;
            }
        }
        //如果不存在，添加一个新的商品
        if (!flag) {
            //需要调用服务获取商品信息
            TbItem tbItem = itemService.getItemById(itemId);
            //设置购买的商品数量
            tbItem.setNum(num);
            //取一张图片
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                tbItem.setImage(images[0]);
            }
            //把商品添加到购物车
            cartItemList.add(tbItem);
        }
        //保存购物车列表
        saveCartItemList(request, response, cartItemList, result);
        //返回添加成功页面
        return "cartSuccess";
    }

    /*private List<TbItem> getCartItemList(HttpServletRequest request) {
        //从cookie中取购物车商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            //如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
        return itemList;
    }*/

    /**
     * 获取 购物车所有商品（展示购物车列表）
     * @param request
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request) {
        //跨域请求获取用户信息
        YougouResult result = getUserInfoJsonp(request);
        //获取购物车列表
        List<TbItem> cartItemList = this.getCartItemList(request, result);
        //判断购物车是否为空
        if (cartItemList == null || cartItemList.size() == 0)
            return "empty-cart";
        //把购物车列表传递给jsp
        request.setAttribute("cartList", cartItemList);
        //返回逻辑视图
        return  "cart";
    }

    /**
     * 更新 购物车单个商品数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public YougouResult updateItemNum(@PathVariable Long itemId,
                                      @PathVariable  Integer num,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        //跨域请求获取用户信息
        YougouResult result = getUserInfoJsonp(request);
        //获取购物车列表
        List<TbItem> cartItemList = this.getCartItemList(request, result);
        //查询到对应的商品
        for (TbItem item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //更新商品数量
                item.setNum(num);
                break;
            }
        }
        //保存购物车列表
        saveCartItemList(request, response, cartItemList, result);
        //返回成功
        return YougouResult.ok();
    }

    /**
     * 删除 购物车（单个）
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        //跨域请求获取用户信息
        YougouResult result = getUserInfoJsonp(request);
        //从cookie中获取购物车列表
        List<TbItem> cartItemList = this.getCartItemList(request, result);
        //找到对应的商品
        for (TbItem item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //删除商品
                cartItemList.remove(item);
                break;
            }
        }
        //保存购物车列表
        saveCartItemList(request, response, cartItemList, result);
        //重定向到购物车列表页面
        return "redirect:/cart/cart.html";
    }
    @RequestMapping(value = "/cart/synch/{token}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public YougouResult synchronousCartItem(@PathVariable String token,
                                            @RequestBody String cookieCartJson)
            throws UnsupportedEncodingException {
        cookieCartJson = URLDecoder.decode(cookieCartJson, "utf-8");
        cookieCartJson = StringUtils.substringAfter(cookieCartJson, "data=");
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if ( StringUtils.isBlank(json) ) {
            return YougouResult.build(400, "用户登录异常！");
        }
        //把json转换成User对象
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        //从cookie中获得的购物车商品列表
        List<TbItem> cookieItemList = JsonUtils.jsonToList(cookieCartJson, TbItem.class);
        //查询redis获取购物车商品列表
        String redisCartJson = jedisClient.get(REDIS_CART_KEY + ":" + tbUser.getId() + ":base");
        //从redis中获得的购物车商品列表
        List<TbItem> redisItemList = JsonUtils.jsonToList(redisCartJson, TbItem.class);
        //循环判断cookie中商品是否存在于redis
        int i=0;
        for (i=0;i<cookieItemList.size();i++){
            TbItem item = cookieItemList.get(i);
            Long itemId = item.getId();
            //判断商品在redis购物车中是否存在
            boolean flag = false;
            for (TbItem tbItem : redisItemList) {
                if (tbItem.getId() == itemId.longValue()) {
                    //如果存在覆盖
                    tbItem.setNum(item.getNum());
                    flag = true;
                    break;
                }
            }
            //如果不存在，添加一个新的商品
            if (!flag) {
                //把商品添加到购物车
                redisItemList.add(item);
            }

        }
        //把购物车列表写入redis
        jedisClient.set(REDIS_CART_KEY + ":" + tbUser.getId() + ":base", JsonUtils.objectToJson(redisItemList));
        //设置key的过期时间
        jedisClient.expire(REDIS_CART_KEY + ":" + tbUser.getId() + ":base", REDIS_CART_EXPIRE);
        return YougouResult.ok();
    }

    /**
     * 查询购物车信息
     * description:此方法本应该是service（服务层）的接口，
     * 没有数据库操作，这块为了方便就定义到controller（控制层）。
     * @param request
     * @return
     */
    private List<TbItem> getCartItemList(HttpServletRequest request, YougouResult result) {
        String cartJson = "";
        //未登录去cookies中取购物车,已登录去redis中取购物车
        if (result == null || result.getStatus() != 200) {
            //从cookie中取购物车商品列表
            cartJson = CookieUtils.getCookieValue(request, CART_KEY, true);
        }else {
            //获取用户id
            TbUser tbUser = (TbUser) result.getData();
            //从redis中取购物车
            cartJson = jedisClient.get(REDIS_CART_KEY + ":" + tbUser.getId() + ":base");
        }
        if (StringUtils.isBlank(cartJson)) {
            //如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> itemList = JsonUtils.jsonToList(cartJson, TbItem.class);
        return itemList;
    }

    /**
     * 跨域请求获取用户信息
     * @param request
     * @return
     */
    private YougouResult getUserInfoJsonp(HttpServletRequest request) {
        YougouResult result = null;
        //获取cookies中token信息
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        if (StringUtils.isBlank(token))
            return result;
        String userInfo = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
        if (StringUtils.isBlank(userInfo))
            return result;
        result = YougouResult.formatToPojo(userInfo, TbUser.class);
        return result;
    }

    /**
     * 将购物车写入到cookie或redis
     * @param request
     * @param response
     * @param cartItemList
     * @param result
     */
    private void saveCartItemList(HttpServletRequest request,
                                  HttpServletResponse response,
                                  List<TbItem> cartItemList,
                                  YougouResult result) {
        //未登录写入cookies,已登录写入redis
        if (result == null || result.getStatus() != 200) {
            //把购物车列表写入cookie
            CookieUtils.setCookie(request, response, CART_KEY,
                    JsonUtils.objectToJson(cartItemList), CART_EXPIRE, true);
        }else {
            //获取用户id
            TbUser tbUser = (TbUser) result.getData();
            //把购物车列表写入redis
            jedisClient.set(REDIS_CART_KEY + ":" + tbUser.getId() + ":base", JsonUtils.objectToJson(cartItemList));
            //设置key的过期时间
            jedisClient.expire(REDIS_CART_KEY + ":" + tbUser.getId() + ":base", REDIS_CART_EXPIRE);
        }
    }
}
