package com.atguigu.gmall.managerweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.ManagerService;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.List;

@RestController
@CrossOrigin
public class ManagerController {

    @Value("${fileServer.utl}")
    String FileUrl;


    @Reference
    private ManagerService managerService;

    @RequestMapping(value = "index" )
    public String index(){
        return "index";
    }

    @RequestMapping("getBaseCatalog1List")
    public List<BaseCatalog1> getBaseCatalog1List(){

        return managerService.getCataLog1();
    }

    @RequestMapping("getBaseCatalog2List")
    public List<BaseCatalog2> getBaseCatalog2List(String baseCatalog1Id){

        return managerService.getCataLog2(baseCatalog1Id);
    }

    @RequestMapping("getBaseCatalog3List")
    public List<BaseCatalog3> getBaseCatalog3List(String baseCatalog2Id){

        return managerService.getCataLog3(baseCatalog2Id);
    }

    @RequestMapping("getAttrInfoList")
    public List<BaseAttrInfo> getAttrInfoList(String baseCatalog3Id){

        return managerService.getAttrInfo(baseCatalog3Id);
    }

    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String BaseAttrId){

        return managerService.getAttrValue(BaseAttrId);
    }

    @RequestMapping("addCatalog1")
    public String addCatalog1(BaseCatalog1 baseCatalog1){
        int i = managerService.addBaseCataLog1(baseCatalog1);

        if(i > 0)
            return "ok";
        else
            return "error";
    }
    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        managerService.setAttrInfo(baseAttrInfo);
        return "ok";
    }

    @RequestMapping("getAttrInfoAndValueList")
    public BaseAttrInfo getAttrInfoAndValueList(String attrId){
        BaseAttrInfo attrInfoAndValue = managerService.getAttrInfoAndValue(attrId);
        return attrInfoAndValue;
    }


    @RequestMapping("spuList")
    public List<SpuInfo> getspuList(String catalog3Id){

        List<SpuInfo> spuInfoList = managerService.getSpuInfoList(catalog3Id);
        return spuInfoList;
    }


    @RequestMapping(value = "fileUpload",method = RequestMethod.POST)
    public String uploadFdfs(@RequestParam("file") MultipartFile file) throws Exception{
        String imgUrl = FileUrl;

        //如果文件不为空
        if(file != null){
            System.out.println(file.getName() + "|" + file.getSize());

            /**获取配置文件信息*/
            String ConfigFile = this.getClass().getResource("/tracker.conf").getFile();

            /** 调用fdfs 获取 配置信息 */
            ClientGlobal.init(ConfigFile);

            /** 获取客户端 */
            TrackerClient trackerClient = new TrackerClient();

            /** 获取服务端 */
            TrackerServer connection = trackerClient.getConnection();

            StorageClient storageClient = new StorageClient(connection, null);

            /** 获取文件名称 */
            String originalFilename = file.getOriginalFilename();

            /** 获取文件格式 */
            String extName = StringUtils.substringAfterLast(originalFilename, ".");

            /** 执行上传 */
            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);

            /** 将域名赋给要返回的imgUrl */
            imgUrl = FileUrl;

            for (int i = 0; i < upload_file.length;i++){
                String path = upload_file[i];
                imgUrl += "/" + path;
            }

        }

        return imgUrl;
    }
}
