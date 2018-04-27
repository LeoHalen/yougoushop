package com.yougou.mapper;

import com.yougou.pojo.TbItemParamExample;
import com.yougou.pojo.TbItemParamExtend;
import com.yougou.pojo.TbItemParamExtendExample;
import com.yougou.pojo.TbItemParamItemExample;

import java.util.List;

/**
 * 商品规格参数扩展
 *
 * @ProjectName: yougoushop
 * @Description: java类作用描述
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/4/26 15:28
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface TbItemParamExtendMapper {

    List<TbItemParamExtend> selectItemParamExtendList();
}
