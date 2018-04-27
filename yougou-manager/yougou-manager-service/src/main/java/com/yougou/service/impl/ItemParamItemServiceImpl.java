package com.yougou.service.impl;

import com.yougou.common.utils.JsonUtils;
import com.yougou.mapper.TbItemParamItemMapper;
import com.yougou.pojo.TbItemParamItem;
import com.yougou.pojo.TbItemParamItemExample;
import com.yougou.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品规格参数服务层接口实现
 *
 * @ProjectName: yougoushop
 * @Description: java类作用描述
 * @Author: HALEN(李智刚)
 * @CreateDate: 2018/4/23 21:33
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;


    @Override
    public String getItemParamItemByItemId(long itemId) {
        //根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem> itemParamItemList = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (itemParamItemList == null || itemParamItemList.isEmpty()) {
            return "";
        }
        //获取规格参数
        TbItemParamItem itemParamItem = itemParamItemList.get(0);
        //获取规格参数json数据
        String paramData = itemParamItem.getParamData();
        //转换为实体对象
        List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
        //遍历list生成html
        StringBuffer sb = new StringBuffer();
        /*sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("  <tbody>\n");
        if (mapList != null && mapList.size() != 0){
            for(Map map : mapList){
                sb.append("    <tr>\n");
                sb.append("      <th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
                sb.append("    </tr>\n");
                List<Map> mapList2 = (List<Map>) map.get("params");
                for(Map map2 : mapList2){
                    sb.append("     <tr>\n");
                    sb.append("      <td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
                    sb.append("      <td>"+map2.get("v")+"</td>\n");
                    sb.append("    </tr>\n");
                }
            }
        }
        sb.append("  </tbody>\n");
        sb.append("</table>");*/
        if (mapList != null && mapList.size() != 0) {
            for (Map map : mapList) {
                sb.append("<div class=\"Ptable-item\">");
                sb.append("<h3>" + map.get("group") + "</h3>\n");
                sb.append("<div class=\"Ptable-div\">");
                List<Map> mapList2 = (List<Map>) map.get("params");
                sb.append("     <dl>\n");
                for (Map map2 : mapList2) {
                    sb.append("      <dt>" + map2.get("k") + "</dt>\n");
                    sb.append("      <dd>" + map2.get("v") + "</dd>\n");
                }
                sb.append("    </dl>\n");
                sb.append("</div>");
                sb.append("</div>");
            }
        }
        return sb.toString();
    }
}
