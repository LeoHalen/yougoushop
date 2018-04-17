package com.yougou.item.controller;

import com.yougou.item.pojo.Item;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbItemDesc;
import com.yougou.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品详情控制层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.item.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 20:07
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId,
                           Model model) {
        //取商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //取商品详情
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        //把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        //返回逻辑视图
        return "item";
    }
}
