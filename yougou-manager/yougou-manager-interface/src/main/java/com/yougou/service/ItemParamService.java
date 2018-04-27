package com.yougou.service;

import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;

/**
 * 商品规格参数模板服务层接口
 *
 * @ProjectName: yougoushop
 * @Description: java类作用描述
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/4/23 17:27
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ItemParamService {

    YougouResult getItemParamByCid(long cid);

    YougouResult insertItemParam(long cid, String paramData);

    EasyUIDataGridResult getItemParamList(int page, int rows);
}
