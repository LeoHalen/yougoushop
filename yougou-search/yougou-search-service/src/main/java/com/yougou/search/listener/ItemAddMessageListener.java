package com.yougou.search.listener;

import com.yougou.common.pojo.SearchItem;
import com.yougou.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听商品添加事件，同步索引库
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.listener
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 11:45
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {

		try {
			//从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			//等待service层事物提交
			Thread.sleep(1000);
			//根据商品id查询数据，取商品信息
			SearchItem searchItem = searchItemMapper.getItemById(itemId);
			//创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			//向文档对象中添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title",searchItem.getTitle());
			document.addField("item_sell_point",searchItem.getSell_point());
			document.addField("item_price",searchItem.getPrice());
			document.addField("item_image",searchItem.getImage());
			document.addField("item_category_name",searchItem.getCategory_name());
			document.addField("item_desc",searchItem.getItem_desc());
			//把文档写入到索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
