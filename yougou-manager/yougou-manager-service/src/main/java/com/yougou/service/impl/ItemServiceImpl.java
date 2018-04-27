package com.yougou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.common.utils.IDUtils;
import com.yougou.common.utils.JsonUtils;
import com.yougou.jedis.JedisClient;
import com.yougou.mapper.TbItemDescMapper;
import com.yougou.mapper.TbItemMapper;
import com.yougou.mapper.TbItemParamItemMapper;
import com.yougou.mapper.TbItemParamMapper;
import com.yougou.pojo.TbItem;
import com.yougou.pojo.TbItemDesc;
import com.yougou.pojo.TbItemExample;
import com.yougou.pojo.TbItemParamItem;
import com.yougou.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
	private JmsTemplate jmsTemplate;
   	@Resource(name = "itemAddTopic")
	private Destination destination;
   	@Autowired
    private JedisClient jedisClient;
   	@Value("${ITEM_INFO}")
    private String ITEM_INFO;
   	@Value("${ITEM_EXPIRE}")
    private Integer ITEM_EXPIRE;

    public TbItem getItemById(long itemId) {
        try {
            //查询数据库之前先查询缓存
            String json = jedisClient.get(ITEM_INFO);
            if (StringUtils.isNotBlank(json)) {
                //把json数据转换成pojo
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存中没有查询数据库
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        try {
            //把结果添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId +":BASE",JsonUtils.objectToJson(tbItem));
            //设置过期时间，提高缓存利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId +":BASE", ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItem;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //转化格式
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        //返回结果
        return result;
    }

    @Override
    public YougouResult addItem(TbItem item, String desc, String itemParam) {
        //生成商品id
        final long itemId = IDUtils.genItemId();
        //补全item的属性
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(item);
        //创建一个商品描述表对应的pojo
        TbItemDesc itemDesc = new TbItemDesc();
        //补全pojo的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDesc.setCreated(new Date());
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);

        //构造商品规格参数对象
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向商品规格参数表插入数据
        itemParamItemMapper.insert(itemParamItem);
        //向ActiveMQ发送商品添加消息
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
            //发送商品id
            TextMessage textMessage = session.createTextMessage(itemId + "");
            return textMessage;
			}
		});
        //返回结果
        return YougouResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        try {
            //查询数据库之前先查询缓存
            String json = jedisClient.get(ITEM_INFO);
            if (StringUtils.isNotBlank(json)) {
                //把json数据转换成pojo
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存中没有查询数据库
        TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            //把结果添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId +":DESC",JsonUtils.objectToJson(tbItemDesc));
            //设置过期时间，提高缓存利用率
            jedisClient.expire(ITEM_INFO, ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }


}
