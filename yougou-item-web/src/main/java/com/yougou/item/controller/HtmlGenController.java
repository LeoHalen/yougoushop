package com.yougou.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * freemarker网页静态化控制层接口
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.item.controller
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/18 11:00
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/gethtml")
    @ResponseBody
    public String genHtml() {

        try {
            //生成静态页面
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("hello.ftl");
            Map data = new HashMap();
            data.put("hello", "spring freemarker test");
            File htmlFile = new File("E:\\SourceCode\\IdeaWorkspace\\yougoushop\\yougou-item-web\\src\\main\\webapp\\out\\test.html");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8"));
            template.process(data, out);
            out.close();
            //返回结果
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "exception";
    }
}
