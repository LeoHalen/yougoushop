package com.yougou.search.service;

import com.yougou.common.pojo.SearchResult;

/**
 * 搜索服务功能接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.service
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/15 9:37
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface SearchService {

    SearchResult search(String queryString, int page, int rows) throws Exception;
}
