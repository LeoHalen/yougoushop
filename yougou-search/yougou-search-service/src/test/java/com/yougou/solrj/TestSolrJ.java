package com.yougou.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.solrj
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/14 16:40
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class TestSolrJ {

    @Test
    public void testAddDocument() throws Exception {
        //创建一个SolrServer对象。创建要给HttpSolrServer对象
        //需要指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.58.145:8080/solr/collection01");
        //创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加一个域，必须有id域，域的名称必须在managed-schema.xml中定义
        document.addField("id","test002");
        document.addField("item_title", "测试商品2");
        document.addField("item_price",1000);
        //把文档对象写入到索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void deleteDocumnetById() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.58.145:8080/solr/collection01");
        solrServer.deleteById("test001");
        solrServer.commit();
    }

    @Test
    public void deleteDocumentByQuery() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.58.145:8080/solr/collection01");
        solrServer.deleteByQuery("item_title:商品");//item_title分词了，所以只要有商品这个词的全部删除
        solrServer.commit();
    }

    @Test
    public void searchDocument() throws Exception {
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.58.145:8080/solr/collection01");
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件、过滤条件、分页条件、排序条件、高亮
//        query.set("q","*:*");
        query.setQuery("手机");
        //分页条件
        query.setStart(0);
        query.setRows(10);
        //设置默认搜索域
        query.set("df", "item_keywords");
        //设置高亮
        query.setHighlight(true);
        //高亮显示域
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<div>");
        query.setHighlightSimplePost("</div>");
        //执行查询，得到一个Response对象
        QueryResponse response = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        //取查询结果总记录数
        System.out.println("查询结果总记录数："+solrDocumentList.getNumFound());
        for (SolrDocument solrDocument : solrDocumentList){
            System.out.println(solrDocument.get("id"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if(list != null && list.size() > 0){
                itemTitle = list.get(0);
            }else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            System.out.println(itemTitle);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println(("==========================="));
        }
    }


}
