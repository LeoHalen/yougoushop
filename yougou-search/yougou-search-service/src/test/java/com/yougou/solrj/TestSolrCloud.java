package com.yougou.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 集群solr测试类
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.solrj
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/16 14:11
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class TestSolrCloud {

	@Test
	public void testSolrCloudAddDocument() throws Exception{
		//创建一个CloudSolrServer对象，构造方法中需要制定zookeeper的地址列表
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.58.145:2181,192.168.58.145:2182,192.168.58.145:2183");
		//需要设置默认的Collection
		cloudSolrServer.setDefaultCollection("cores");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", "test001");
		document.addField("item_title","测试商品名称");
		document.addField("item_price",100);
		//把文档写入索引库
		cloudSolrServer.add(document);
		//提交
		cloudSolrServer.commit();
	}

}
