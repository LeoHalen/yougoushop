package com.yougou;


import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Test FastDFS图片上传
 */
public class FastDFSTest{

    @Test
    public void uploadFile() throws Exception{
        //1、向工程中添加jar包
        //2、创建一个配置文件。配置tracker服务器地址
        //3、加载配置文件
        ClientGlobal.init("E:\\SourceCode\\IdeaWorkspace\\yougou\\yougou-manager-web\\src\\main\\resources\\conf\\client.conf");
        //4、创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //5、使用TrackerCilent对象获得TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //6、创建一个StorageServer的引用null就可以
        StorageServer storageServer = null;
        //7、创建一个StorageServer对象。trackerserver、storageServer两个参数
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //8、使用StorageClient对象上传文件
        String[] jpgs = storageClient.upload_file("C:\\Users\\HALEN\\Desktop\\照片\\微信图片_20170411115840.jpg", "jpg", null);

        for (String str : jpgs){
            System.out.println(str);
        }
    }
}
