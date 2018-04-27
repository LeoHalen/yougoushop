package com.yougou.controller;

import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.content.service.ContentService;
import com.yougou.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理控制层接口
 *
 * @ProjectName: yougou
 * @Package: com.yougou.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/4/10 20:02
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/10 20:02
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public YougouResult addContent(TbContent content){
        YougouResult result = contentService.addContent(content);
        return result;
    }

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows){
        EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }

    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public YougouResult updateContent(TbContent content){
        YougouResult result = contentService.updateContent(content);
        return result;
    }

    @RequestMapping("/content/delete")
    @ResponseBody
    public YougouResult deleteContent(String ids){
        YougouResult result = contentService.deleteContent(ids);
        return result;
    }

    @RequestMapping("/content/getContent")
    @ResponseBody
    public YougouResult getContent(Long id){
        YougouResult result = contentService.getContent(id);
        return result;
    }
}
