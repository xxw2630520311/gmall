package com.atguigu.gmall.managerweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.service.ManagerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class SpuController {

    @Reference
    private ManagerService managerService;

    @RequestMapping("spuImageList")
    public List<SpuImage> spuImageList(String spuId){

        return managerService.spuImageList(spuId);
    }


    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(String catlog3Id){
        return managerService.attrInfoList(catlog3Id);
    }


    @RequestMapping("spuSaleAttrList")
    public List<SpuSaleAttr> spuSaleAttrList(String spuId){

       return managerService.selectSpuSaleAttrList(spuId);
    }

    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){

          managerService.saveSkuInfo(skuInfo);
    }
}
