package com.yougou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.mapper.TbItemParamExtendMapper;
import com.yougou.mapper.TbItemParamMapper;
import com.yougou.pojo.TbItemParam;
import com.yougou.pojo.TbItemParamExample;
import com.yougou.pojo.TbItemParamExtend;
import com.yougou.pojo.TbItemParamExtendExample;
import com.yougou.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品规格参数模板服务层接口实现
 *
 * @ProjectName: yougoushop
 * @Description: java类作用描述
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/4/23 17:47
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;
    @Autowired
    private TbItemParamExtendMapper itemParamExtendMapper;

    @Override
    public YougouResult getItemParamByCid(long cid) {
        //根据cid查询规格参数模板
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        //执行查询
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if(list != null && list.size() > 0){
            TbItemParam itemParam = list.get(0);
            return YougouResult.ok(itemParam);
        }

        return YougouResult.ok();
    }

    @Override
    public YougouResult insertItemParam(long cid, String paramData) {
        //创建一个pojo
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入记录
        itemParamMapper.insert(itemParam);

        return YougouResult.ok();
    }

    @Override
    public EasyUIDataGridResult getItemParamList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<TbItemParamExtend> list = itemParamExtendMapper.selectItemParamExtendList();
        //转化格式
        PageInfo<TbItemParamExtend> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        //返回结果
        return result;
    }
}
