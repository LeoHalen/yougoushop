package com.yougou.sso.service;

import com.yougou.common.pojo.YougouResult;
import com.yougou.pojo.TbUser;

/**
 * 用户处理服务层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.sso.service
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/19 15:48
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface UserService {

    YougouResult checkData(String data, int type);
    YougouResult register(TbUser user);
    YougouResult login(String username, String password);
    YougouResult getUserByToken(String token);
    YougouResult logout(String token);
}
