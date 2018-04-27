package com.yougou.service;

import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbItemDesc;

public interface ItemService {

    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);

    YougouResult addItem(TbItem item, String desc, String itemParam);

    TbItemDesc getItemDescById(long itemId);
}
