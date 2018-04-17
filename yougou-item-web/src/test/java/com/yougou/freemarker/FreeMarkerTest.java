package com.yougou.freemarker;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * freemarker测试类
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.freemarker
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/17 22:02
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class FreeMarkerTest {


    @Test
    public void testFreemarker() throws Exception {
        //1、创建一个模板文件
        //2、创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3、设置模板所在的路径
        configuration.setDirectoryForTemplateLoading(
                new File("E:\\SourceCode\\IdeaWorkspace\\yougoushop\\yougou-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4、设置模板的字符集，一般utf-8
        configuration.setDefaultEncoding("utf-8");
        //5、使用configuration对象加载一个模板文件，需要指定模板文件的文件名
        Template template = configuration.getTemplate("hello.ftl");
        //6、创建一个数据集，可以是pojo也可以是map,推荐使用map
        Map data = new HashMap();
        data.put("hello", "hello freemarker!");
        //7、创建一个Writer对象，指定输出文件的路径及文件名
        Writer out = new FileWriter(
                new File("E:\\SourceCode\\IdeaWorkspace\\yougoushop\\yougou-item-web\\src\\main\\webapp\\out\\hello.txt"));
        //8、使用模板对象的process方法输出文件
        template.process(data, out);
        //9、关闭流
        out.close();

    }
}
