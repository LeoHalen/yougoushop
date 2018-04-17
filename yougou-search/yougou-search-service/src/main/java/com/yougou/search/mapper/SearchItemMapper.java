package com.yougou.search.mapper;

import com.yougou.common.pojo.SearchItem;

import java.util.List;

/**
 * 同步索引库数据库操作接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.mapper
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/14 16:25
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface SearchItemMapper {

    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
