package com.yougou.portal.controller;

import com.yougou.common.utils.JsonUtils;
import com.yougou.content.service.ContentService;
import com.yougou.pojo.TbContent;
import com.yougou.portal.pojo.AdPositionNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougou
 * @Package: com.yougou.portal.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: 主页跳转接口
 * @Author: HALEN
 * @CreateDate: 2018/4/8 17:53
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/8 17:53
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class IndexController {

    @Value("${ADP_CATEGORY_ID}")
    private Long ADP_CATEGORY_ID;

    @Value("${ADP_WIDTH}")
    private Integer ADP_WIDTH;

    @Value("${ADP_WIDTH_B}")
    private Integer ADP_WIDTH_B;

    @Value("${ADP_HEIGHT}")
    private Integer ADP_HEIGHT;

    @Value("${ADP_HEIGHT_B}")
    private Integer ADP_HEIGHT_B;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        //根据cid查询轮播图的内容列表(从属性文件读出首页大广告位id)
        List<TbContent> contentList = contentService.getContentByCid(ADP_CATEGORY_ID);
        //把列表转换为AdpositionNode列表
        List<Object> adpNodes = new ArrayList<>();
        for (TbContent tbContent : contentList){
            AdPositionNode node = new AdPositionNode();
            node.setAlt(tbContent.getTitle());
            node.setHeight(ADP_HEIGHT);
            node.setHeightB(ADP_HEIGHT_B);
            node.setWidth(ADP_WIDTH);
            node.setHeightB(ADP_WIDTH_B);
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setHref(tbContent.getUrl());
            //添加节点列表
            adpNodes.add(node);
        }
        //把列表转换成json数据
        String adpJson = JsonUtils.objectToJson(adpNodes);
        //把json数据传递给页面
        model.addAttribute("adp", adpJson);
        return "index";
    }
}
