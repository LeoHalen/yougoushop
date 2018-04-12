package com.yougou.service;

import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.pojo.TbItem;

public interface ItemService {

    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);

    YougouResult addItem(TbItem item, String desc);
}
