package com.yougou.controller;

import com.yougou.common.pojo.EasyUITreeNode;
import com.yougou.common.pojo.YougouResult;
import com.yougou.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理控制层接口
 *
 * @ProjectName: yougou
 * @Package: com.yougou.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: 内容分类管理控制层
 * @Author: HALEN
 * @CreateDate: 2018/4/9 10:33
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/9 10:33
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 内容分类节点列表展示
     * @param parentId
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(
            @RequestParam(value = "id", defaultValue = "0")Long parentId){
        List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
        return list;
    }

    /**
     * 内容分类节点创建
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public YougouResult addContentCategory(Long parentId, String name){
        YougouResult result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }

    /**
     * 内容分类节点更新
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public YougouResult updateContentCategory(Long id, String name){
        YougouResult result = contentCategoryService.updateContentCategory(id, name);
        return result;
    }

    /**
     * 内容分类节点删除
     * @param id
     * @return
     */
    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public YougouResult deleteContentCategory(Long id){
        YougouResult result = contentCategoryService.deleteContentCategory(id);
        return result;
    }
}
