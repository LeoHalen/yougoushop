package com.yougou.order.pojo;

import com.yougou.pojo.TbOrder;
import com.yougou.pojo.TbOrderItem;
import com.yougou.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * 订单pojo
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.order.pojo
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/21 19:50
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class OrderInfo extends TbOrder implements Serializable {

    private List<TbOrderItem> orderItems;//订单商品集合
    private TbOrderShipping orderShipping;//订单详情

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
