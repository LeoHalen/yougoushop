package com.yougou.content.service;

import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.pojo.TbContent;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougou
 * @Package: com.yougou.content.service
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/4/10 19:55
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/10 19:55
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ContentService {

    YougouResult addContent(TbContent content);
    EasyUIDataGridResult getContentList(long categoryId, int page, int rows);
    YougouResult updateContent(TbContent content);
    YougouResult deleteContent(String ids);
    YougouResult getContent(long id);
    List<TbContent> getContentByCid(long cid);
}
