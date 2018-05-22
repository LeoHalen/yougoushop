package com.yougou.order.service;

import com.yougou.common.pojo.YougouResult;
import com.yougou.order.pojo.OrderInfo;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbUser;
import sun.net.www.protocol.http.HttpURLConnection;

import java.util.List;

/**
 * 订单处理服务层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.order.service
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/21 20:05
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface OrderService {

    YougouResult createOrder(OrderInfo orderInfo);
    List<TbItem> getCartItemList(TbUser tbUser);
}
