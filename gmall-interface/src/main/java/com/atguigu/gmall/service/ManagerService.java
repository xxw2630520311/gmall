package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface ManagerService {
    /**
     * 获取所有一级分类
     * @return
     */
    List<BaseCatalog1> getCataLog1();

    /**
     * 获取所有二级分类
     * @return
     */
    List<BaseCatalog2> getCataLog2(String catalogId);

    /**
     * 获取所有三级分类
     * @return
     */
    List<BaseCatalog3> getCataLog3(String catalog2Id);

    /**
     * 获取三级分类所有属性
     * @return
     */
    List<BaseAttrInfo> getAttrInfo(String catalog3Id);

    /**
     * 获取属性值 已废弃
     * @return
     */
    List<BaseAttrValue> getAttrValue(String attrId);


    /**
     * 添加一级分类
     * @param baseCatalog1
     * @return
     */
    int addBaseCataLog1(BaseCatalog1 baseCatalog1);


    /**
     * 设置属性
     * @param baseAttrInfo
     */
    void setAttrInfo(BaseAttrInfo baseAttrInfo);


    /**
     *
     * 根据ID获取属性一级属性值
     * @param attrId
     * @return
     */
    BaseAttrInfo getAttrInfoAndValue(String attrId);


    /**
     * 获取所有的spuinfo
     * @param catalog3Id
     * @return
     */
    List<SpuInfo> getSpuInfoList(String catalog3Id);

    List<SpuInfo> getSpuInfoAll();
    /**
     *获取属性
     */
    List<BaseSaleAttr> getSaleAttr();

    /**
     * 保存spu
     * @param spuInfo
     */
    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 添加sku时的图片列表
     * @param spuId
     * @return
     */
    List<SpuImage> spuImageList(String spuId);

    /**
     * 获取平台属性
     * @param cataLog3Id
     * @return
     */
    List<BaseAttrInfo> attrInfoList(String cataLog3Id);

    /**
     * 获取销售属性
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> selectSpuSaleAttrList(String spuId);

    void saveSkuInfo(SkuInfo skuInfo);
}
