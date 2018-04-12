package com.yougou.content.service.impl;

import com.yougou.common.pojo.EasyUITreeNode;
import com.yougou.common.pojo.YougouResult;
import com.yougou.content.service.ContentCategoryService;
import com.yougou.mapper.TbContentCategoryMapper;
import com.yougou.pojo.TbContentCategory;
import com.yougou.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类服务层接口
 *
 * @ProjectName: yougou
 * @Package: com.yougou.content.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: HALEN
 * @CreateDate: 2018/4/9 10:07
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/9 10:07
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 获取内容分类列表
     * @param parentId
     * @return
     */
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> result = new ArrayList<>();
        EasyUITreeNode node;
        for(TbContentCategory tbContentCategory : list){
            node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node .setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            //添加到结果列表
            result.add(node);
        }
        return result;
    }

    /**
     * 添加
     * @param parentId
     * @param name
     * @return
     */
    public YougouResult addContentCategory(Long parentId, String name) {
        //创建一个pojo对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        //补全对象的属性
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        //状态，可选值：1（正常），2（删除）
        tbContentCategory.setStatus(1);
        //排序，默认为1
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        //插入到数据库
        tbContentCategoryMapper.insert(tbContentCategory);
        //判断父节点的状态
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()){
            //如果父节点为叶子节点应该改为父节点
            parent.setIsParent(true);
            //更新父节点
            this.updateParentStatus(parent);
        }
        //返回结果
        return YougouResult.ok(tbContentCategory);
    }

    /**
     * 更新
     * @param id
     * @param name
     * @return
     */
    public YougouResult updateContentCategory(Long id, String name) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        if(tbContentCategory != null){
            tbContentCategory.setName(name);
            try {
                tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
                return YougouResult.ok();
            }catch (Exception e){
                e.printStackTrace();
                this.getReturnResult(500,"后台报错：数据库操作异常!",null);
            }
        }
        return this.getReturnResult(500,"后台报错：空指针异常!",null);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public YougouResult deleteContentCategory(Long id){
        try {
            TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
            if(tbContentCategory != null){
                //删除当前节点
                tbContentCategoryMapper.deleteByPrimaryKey(id);
                //递归删除子节点
                this.recursiveDeleteCategory(id);
                //判断父节点状态，修改父节点状态
                this.updateParentNodeStatus(tbContentCategory.getParentId());
            }
        }catch (Exception e){
            return this.getReturnResult(500,"后台报错：数据库操作异常！",null);
        }
        return YougouResult.ok();
    }

    private void updateParentNodeStatus(Long parentId){
        //查询父id是否还有子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if(list.size()==0){
            //修改父节点状态为叶子节点（因为删除此节点后，父节点再无子节点）
            TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
            if(parent.getIsParent()){
                parent.setIsParent(false);
                //修改父节点状态
                this.updateParentStatus(parent);
            }
        }
    }

    private void recursiveDeleteCategory(Long parentId){
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        try {
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            for(TbContentCategory contentCategory : list){
                if(contentCategory.getIsParent()){
                    tbContentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
                    //递归循坏
                    this.recursiveDeleteCategory(contentCategory.getId());
                }else {
                    //删除
                    tbContentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private YougouResult getReturnResult(Integer status, String msg, Object obj){
        YougouResult result = new YougouResult(status,msg,obj);
        return result;
    }

    private void updateParentStatus(TbContentCategory parent){
        tbContentCategoryMapper.updateByPrimaryKey(parent);
    }
}
