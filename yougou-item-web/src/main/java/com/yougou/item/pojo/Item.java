package com.yougou.item.pojo;

import com.yougou.pojo.TbItem;

/**
 * 商品详情页实体类
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.item.pojo
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 19:43
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class Item extends TbItem {

	public Item(TbItem tbItem) {
		//初始化属性
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}

	public String[] getImages() {
		if (this.getImage()!=null && !"".equals(this.getImage())){
			String images = this.getImage();
			String[] strs = images.split(",");
			return strs;
		}
		return null;
	}

}
