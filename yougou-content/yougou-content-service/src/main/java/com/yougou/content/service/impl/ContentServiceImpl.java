package com.yougou.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yougou.common.pojo.EasyUIDataGridResult;
import com.yougou.common.pojo.YougouResult;
import com.yougou.content.service.ContentService;
import com.yougou.mapper.TbContentMapper;
import com.yougou.pojo.TbContent;
import com.yougou.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougou
 * @Package: com.yougou.content.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/4/10 19:56
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/10 19:56
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;


    public YougouResult addContent(TbContent content) {
        //补全pojo属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入到内容表
        tbContentMapper.insert(content);
        return YougouResult.ok();
    }


    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //获取查询结果
        List<TbContent> list = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        //返回结果
        return result;
    }


    public YougouResult updateContent(TbContent content) {
        //填充属性
        content.setUpdated(new Date());
        //更新内容
        tbContentMapper.updateByPrimaryKey(content);
        //返回结果
        return YougouResult.ok();
    }

    public YougouResult deleteContent(String ids){
        String[] idArr = ids.split(",");
        for(String id : idArr){
            //删除内容
            tbContentMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        //返回结果
        return YougouResult.ok();
    }


    public YougouResult getContent(long id) {
        TbContent content = tbContentMapper.selectByPrimaryKey(id);
        return YougouResult.ok(content);
    }


    public List<TbContent> getContentByCid(long cid) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        //执行查询结果
        List<TbContent> list = tbContentMapper.selectByExample(example);
        return list;
    }
}
