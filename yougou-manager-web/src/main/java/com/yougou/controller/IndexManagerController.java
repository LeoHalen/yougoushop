package com.yougou.controller;

import com.yougou.common.pojo.SearchItem;
import com.yougou.common.pojo.YougouResult;
import com.yougou.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引管理控制层
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/14 20:53
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class IndexManagerController {

    @Autowired
    private SearchItemService searchItemService;

    /**
     * 导入索引库
     * @return
     */
    @RequestMapping("index/import")
    @ResponseBody
    public YougouResult importIndex() {
        YougouResult result = searchItemService.importItemsToIndex();
        return result;
    }
}
