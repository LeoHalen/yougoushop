package com.yougou.search.service.impl;

import com.yougou.common.pojo.SearchItem;
import com.yougou.common.pojo.YougouResult;
import com.yougou.search.mapper.SearchItemMapper;
import com.yougou.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.search.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/14 17:17
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SolrServer solrServer;

    public YougouResult importItemsToIndex() {
        try {
            //1、先查询所有商品数据
            List<SearchItem> list = searchItemMapper.getItemList();
            //2、遍历商品数据添加到索引库
            for (SearchItem searchItem : list) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title",searchItem.getTitle());
                document.addField("item_sell_point",searchItem.getSell_point());
                document.addField("item_price",searchItem.getPrice());
                document.addField("item_image",searchItem.getImage());
                document.addField("item_category_name",searchItem.getCategory_name());
                document.addField("item_desc",searchItem.getItem_desc());
                //把文档写入到索引库
                solrServer.add(document);
            }
            //3、提交
            solrServer.commit();
        } catch (Exception e) {
            return YougouResult.build(500, "solr索引库数据导入失败");
        }
        //4、返回添加成功
        return YougouResult.ok();
    }
}
