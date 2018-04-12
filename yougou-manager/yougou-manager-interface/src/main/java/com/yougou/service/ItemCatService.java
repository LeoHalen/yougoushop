package com.yougou.service;

import com.yougou.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: 商品列表服务层接口
 * @Package: com.yougou.service
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/3/31 18:17
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/3/31 18:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
