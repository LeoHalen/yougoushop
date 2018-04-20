package com.yougou.sso.service.impl;

import com.yougou.common.pojo.YougouResult;
import com.yougou.common.utils.JsonUtils;
import com.yougou.mapper.TbUserMapper;
import com.yougou.pojo.TbUser;
import com.yougou.pojo.TbUserExample;
import com.yougou.sso.jedis.JedisClient;
import com.yougou.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户处理服务层接口实现类
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.service.impl
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/19 15:57
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_SESSION}")
    private String USER_SESSION;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public YougouResult checkData(String data, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        //1-判断用户名是否可用
        if ( type == 1 ) {
            criteria.andUsernameEqualTo(data);
        } else if ( type == 2 ) {//2-判断手机号是否可用
            criteria.andPhoneEqualTo(data);
        } else if ( type == 3 ) {//3-判断邮箱是否可用
            criteria.andEmailEqualTo(data);
        }else {
            return YougouResult.build(400, "请求参数包含非法数据");
        }
        //执行查询
        List<TbUser> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            //查询到数据，返回false
            return YougouResult.ok(false);
        }
        //数据可以使用
        return YougouResult.ok(true);
    }

    @Override
    public YougouResult register(TbUser user) {
        //检查数据的有效性
        if ( StringUtils.isBlank( user.getUsername() ) ) {
            return YougouResult.build(400, "用户名不能为空！");
        }
        //判断用户是否重复
        YougouResult result = this.checkData(user.getUsername(), 1);
        if ( !(boolean)result.getData() ) {
            return YougouResult.build(400, "用户名已存在！");
        }
        //判断密码是否为空
        if ( StringUtils.isBlank( user.getPassword() ) ) {
            return YougouResult.build(400, "密码不能为空");
        }
        //如果手机号码不为空的话进行是否重复校验
        if ( StringUtils.isNotBlank( user.getPhone() ) ) {
            //是否重复校验
            result = checkData( user.getPhone(), 2);
            if ( !(boolean) result.getData() ) {
                return YougouResult.build(400, "电话号码已存在！");
            }
        }
        //如果Email不为空的话进行是否重复校验
        if ( StringUtils.isNotBlank( user.getEmail() ) ) {
            //是否重复校验
            result = checkData( user.getEmail(), 2);
            if ( !(boolean) result.getData() ) {
                return YougouResult.build(400, "邮箱已存在！");
            }
        }
        //补全pojo的属性
        user.setCreated( new Date() );
        user.setUpdated( new Date() );
        //密码进行md5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        //插入数据
        userMapper.insert(user);
        //返回成功
        return YougouResult.ok();
    }

    @Override
    public YougouResult login(String username, String password) {
        //判断用户名和密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if ( tbUsers == null || tbUsers.size() == 0 ) {
            //返回登录失败
            return YougouResult.build(400, "用户名或密码不正确!");
        }
        TbUser tbUser = tbUsers.get(0);
        //密码要进行md5加密然后再校验
        if ( !DigestUtils.md5DigestAsHex(password.getBytes())
                .equals(tbUser.getPassword()) ) {
            //返回登录失败
            return YougouResult.build(400, "用户名或密码不正确!" );
        }
        //生成token，使用uuid
        String token = UUID.randomUUID().toString();
        //清空密码
        tbUser.setPassword(null);
        //把用户信息保存到redis,key就是token,value就是用户信息
        jedisClient.set( USER_SESSION + ":" + token,JsonUtils.objectToJson(tbUser) );
        //设置key的过期时间
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        //返回登录成功,其中要把token返回
        return YougouResult.ok(token);
    }

    @Override
    public YougouResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if ( StringUtils.isBlank(json) ) {
            return YougouResult.build(400, "用户登录已经过期！");
        }
        //重置token过期时间
        jedisClient.expire(USER_SESSION + ":" + token, 1800);
        //把json转换成User对象
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return YougouResult.ok(tbUser);
//        return YougouResult.ok(json);
    }

    @Override
    public YougouResult logout(String token) {
        jedisClient.expire(USER_SESSION + ":" + token,0);
        return YougouResult.ok();
    }

}
