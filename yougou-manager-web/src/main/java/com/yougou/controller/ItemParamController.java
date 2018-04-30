package com.yougou.controller;

import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规格参数模板控制层接口
 *
 * @ProjectName: yougoushop
 * @Description: java类作用描述
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/4/23 17:50
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    /**
     * 通过商品类目id获取规格参数模板
     * @param cid
     * @return
     */
    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public YougouResult getItemCatByCid(@PathVariable Long cid) {
        YougouResult result = itemParamService.getItemParamByCid(cid);
        return result;
    }

    /**
     * 插入规格参数模板
     * @param cid
     * @param paramData
     * @return
     */
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public YougouResult insertItemParam(@PathVariable Long cid, String paramData) {
        YougouResult result = itemParamService.insertItemParam(cid, paramData);
        return result;
    }

    /**
     * 获得规格参数模板列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
        return result;
    }
}
