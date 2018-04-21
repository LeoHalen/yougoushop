package com.yougou.order.controller;

import com.yougou.common.pojo.YougouResult;
import com.yougou.common.utils.CookieUtils;
import com.yougou.common.utils.JsonUtils;
import com.yougou.order.pojo.OrderInfo;
import com.yougou.order.service.OrderService;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单确认页面处理控制层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.order.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/21 17:30
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class OrderCartController {

    @Value("${CART_KEY}")
    private String CART_KEY;
    @Autowired
    private OrderService orderService;

    /**
     * 展示订单确认页面
     * @param request
     * @return
     */
    @RequestMapping("order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        //用户必须是登录状态
        //取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        //根据用户信息获取地址列表，使用静态数据
        //把收货地址列表获取传递给页面
        //从cookie中获取购物车商品列表展示到页面
        List<TbItem> cartItemList = this.getCartItemList(request);
        request.setAttribute("cartList", cartItemList);
        //返回逻辑视图
        return "order-cart";
    }

    private List<TbItem> getCartItemList(HttpServletRequest request) {
        //从cookie中取购物车商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            //如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
        return itemList;
    }

    /**
     * 生成订单
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model) {
        //生成订单
        YougouResult result = orderService.createOrder(orderInfo);
        //返回逻辑视图
        model.addAttribute("orderId", result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());
        //预计送达时间，预计三天送达
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
        return "success";
    }

}
