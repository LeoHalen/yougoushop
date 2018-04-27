package com.yougou.pojo;

/**
 * 规格参数扩展类
 *
 * @ProjectName: yougoushop
 * @Description: java类作用描述
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/4/26 15:20
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class TbItemParamExtend extends TbItemParam{
    private String  itemCatName;//商品类目名

    public String getItemCatName() {
        return itemCatName;
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }
}
