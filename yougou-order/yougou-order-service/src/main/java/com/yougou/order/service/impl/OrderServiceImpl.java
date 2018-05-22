package com.yougou.order.service.impl;

import com.yougou.common.pojo.YougouResult;
import com.yougou.common.utils.JsonUtils;
import com.yougou.jedis.JedisClient;
import com.yougou.mapper.TbOrderItemMapper;
import com.yougou.mapper.TbOrderMapper;
import com.yougou.mapper.TbOrderShippingMapper;
import com.yougou.order.pojo.OrderInfo;
import com.yougou.order.service.OrderService;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbOrderItem;
import com.yougou.pojo.TbOrderShipping;
import com.yougou.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.order.service.impl
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/21 20:06
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_GEGIN_VALUE}")
    private String ORDER_ID_GEGIN_VALUE;
    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;
    @Value("${REDIS_CART_KEY}")
    private String REDIS_CART_KEY;//购物车在redis中保存的key

    @Override
    public YougouResult createOrder(OrderInfo orderInfo) {
        //生成订单号，可以使用redis的incr生成
        if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
            //设置初始值
            jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_GEGIN_VALUE);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        //向订单表插入数据，需要补全pojo的属性
        orderInfo.setOrderId(orderId);
        //免邮费
        orderInfo.setPostFee("0");
        //状态：1、未付款2、已付款3、未发货4、已发货5、交易成功6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //向订单表插入数据
        tbOrderMapper.insert(orderInfo);
        //向订单明细表插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem : orderItems) {
            //获得明细主键
            String detailId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            tbOrderItem.setId(detailId);
            tbOrderItem.setOrderId(orderId);
            //插入明细数据
            tbOrderItemMapper.insert(tbOrderItem);
        }
        //向订单物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        tbOrderShippingMapper.insert(orderShipping);
        //返回订单号
        return YougouResult.ok(orderId);
    }

    @Override
    public List<TbItem> getCartItemList(TbUser tbUser) {
        //从redis中取购物车
        String cartJson = jedisClient.get(REDIS_CART_KEY + ":" + tbUser.getId() + ":base");
        if (StringUtils.isBlank(cartJson))
            return new ArrayList<>();
        List<TbItem> itemList = JsonUtils.jsonToList(cartJson, TbItem.class);
        //清空购物车
        jedisClient.expire(REDIS_CART_KEY + ":" + tbUser.getId() + ":base", 0);
        return itemList;
    }
}
