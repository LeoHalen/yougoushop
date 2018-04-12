package com.yougou.content.service;

import com.yougou.common.pojo.EasyUITreeNode;
import com.yougou.common.pojo.YougouResult;

import java.util.List;

/**
 * 内容分类服务层接口
 *
 * @ProjectName: yougou
 * @Package: com.yougou.service
 * @ClassName: ${TYPE_NAME}
 * @Description: 内容展示服务层接口
 * @Author: HALEN
 * @CreateDate: 2018/4/9 9:59
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/9 9:59
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCategoryList(long parentId);

    YougouResult addContentCategory(Long parentId, String name);
    YougouResult updateContentCategory(Long id, String name);
    YougouResult deleteContentCategory(Long id);
}
