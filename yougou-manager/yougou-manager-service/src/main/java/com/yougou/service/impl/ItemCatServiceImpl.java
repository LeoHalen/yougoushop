package com.yougou.service.impl;

import com.yougou.common.pojo.EasyUITreeNode;
import com.yougou.mapper.TbItemCatMapper;
import com.yougou.pojo.TbItemCat;
import com.yougou.pojo.TbItemCatExample;
import com.yougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougou
 * @Package: com.yougou.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/3/31 18:19
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/3/31 18:19
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据父节点id查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        //设置查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //设置parentid
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for(TbItemCat tbItemCat : list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            //如果节点下有子节点"closed",如果没有没有子节点"open"
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");
            //添加到节点列表
            resultList.add(node);
        }
        return resultList;
    }
}
