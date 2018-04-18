package com.yougou.item.listener;

import com.yougou.item.pojo.Item;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbItemDesc;
import com.yougou.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.item.listener
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/18 11:28
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @Override
    public void onMessage(Message message) {

        try {
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String strId = textMessage.getText();
            long itemId = Long.parseLong(strId);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品id查询商品信息及商品描述
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //使用freemarker生成静态页面
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            //1、创建模板
            //2、加载模板对象
            Template template = configuration.getTemplate("item.ftl");
            //3、准备模板需要的数据
            Map data = new HashMap();
            data.put("item",item);
            data.put("itemDesc",itemDesc);
            //4、指定输出的目录及文件名
            File htmlFile = new File(HTML_OUT_PATH + strId + ".html");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8"));
            //5、生成静态页面
            template.process(data, out);
            //6、关闭流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
