package com.yougou.controller;

import com.yougou.common.pojo.EasyUITreeNode;
import com.yougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: 商品列表表现层
 * @Package: com.yougou.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/3/31 18:30
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/3/31 18:30
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id", defaultValue = "0") Long parentId){
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        return list;

    }
}
