package com.yougou.search.service;

import com.yougou.common.pojo.YougouResult;

/**
 * 导入商品到solr索引库
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.service
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/14 17:15
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface SearchItemService {

    YougouResult importItemsToIndex();
}
