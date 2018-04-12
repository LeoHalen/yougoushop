package com.yougou.controller;

import com.yougou.common.utils.JsonUtils;
import com.yougou.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.xml.bind.PrintConversionEvent;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * java类简单作用描述
 *
 * @ProjectName: yougou
 * @Package: com.yougou.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: 图片上传接口
 * @Author: HALEN
 * @CreateDate: 2018/4/8 10:31
 * @UpdateUser: HALEN
 * @UpdateDate: 2018/4/8 10:31
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile){
        try {
            //接收上传的文件
            //取到扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            url = IMAGE_SERVER_URL + url;
            //响应上传图片的url
            Map result = new HashMap();
            result.put("error", 0);
            result.put("url", url);
            return JsonUtils.objectToJson(result);
        }catch (Exception e){
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }
    }
}
