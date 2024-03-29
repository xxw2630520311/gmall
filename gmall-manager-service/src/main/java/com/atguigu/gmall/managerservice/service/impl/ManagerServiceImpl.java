package com.atguigu.gmall.managerservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.managerservice.mapper.*;
import com.atguigu.gmall.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    /** spu */
    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    /** sku */
    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Override
    public List<BaseCatalog1> getCataLog1() {
        List<BaseCatalog1> baseCatalog1List = baseCatalog1Mapper.selectAll();
        return baseCatalog1List;
    }

    @Override
    public List<BaseCatalog2> getCataLog2(String catalogId) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalogId);
        List<BaseCatalog2> baseCatalog2s = baseCatalog2Mapper.select(baseCatalog2);
        return baseCatalog2s;
    }

    @Override
    public List<BaseCatalog3> getCataLog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        List<BaseCatalog3> baseCatalog3s = baseCatalog3Mapper.select(baseCatalog3);
        return baseCatalog3s;
    }

    @Override
    public List<BaseAttrInfo> getAttrInfo(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);

        List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.select(baseAttrInfo);
        return baseAttrInfos;
    }

    @Override
    public List<BaseAttrValue> getAttrValue(String attrId) {
//        Example example = new Example(BaseAttrValue.class);
//        example.createCriteria().andEqualTo("attrId",attrId);
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);

        List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(baseAttrValue);
        return baseAttrValues;
    }

    @Override
    public int addBaseCataLog1(BaseCatalog1 baseCatalog1) {
        int insert = baseCatalog1Mapper.insert(baseCatalog1);
        System.out.println("添加成功 " + insert);
        System.out.println("主键" + baseCatalog1.getId());
        return insert;
    }

    /**
     * 设置属性以及属性值
     * @param baseAttrInfo
     */
    @Override
    public void setAttrInfo(BaseAttrInfo baseAttrInfo) {
        //有主键 则更新
        if (  baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0) {
            int i = baseAttrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        } else {
            //防止添加主键
            baseAttrInfo.setId(null);
            //执行添加
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }
        /** 清空原有属性值 */
        Example example = new Example(BaseAttrValue.class);
        example.createCriteria().andEqualTo("attrId",baseAttrInfo.getId());
        baseAttrValueMapper.deleteByExample(example);

        /** 将新属性添加进去 */
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue attrValue: attrValueList
             ) {
            /** 设置 attrId */
            attrValue.setAttrId(baseAttrInfo.getId());
            /** 因为ID自增，所以任何时候都要设置为null */
            attrValue.setId(null);
            baseAttrValueMapper.insertSelective(attrValue);
            
        }
    }

    /**
     * 根据属性ID获取属性值
     * @param attrId
     * @return
     */
    @Override
    public BaseAttrInfo getAttrInfoAndValue(String attrId) {

        /** 根据主键查出来单个attrInfo */
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);


        /** 查出来该属性拥有的属性值 */
        BaseAttrValue attrValue = new BaseAttrValue();
        attrValue.setAttrId(attrId);

        List<BaseAttrValue> attrValueList = baseAttrValueMapper.select(attrValue);

        /** 将属性值放入要返回的bean里 */
        baseAttrInfo.setAttrValueList(attrValueList);
        return baseAttrInfo;


    }

    @Override
    public List<SpuInfo> getSpuInfoList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<SpuInfo> getSpuInfoAll() {
        return spuInfoMapper.selectAll();
    }

    @Override
    public List<BaseSaleAttr> getSaleAttr() {
        List<BaseSaleAttr> baseSaleAttrs = baseSaleAttrMapper.selectAll();
        return baseSaleAttrs;
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        /** 更新 */
        if(spuInfo.getId() != null && spuInfo.getId().length() > 0){
            spuInfoMapper.updateByPrimaryKey(spuInfo);
        }else{
            /** 添加 */
            spuInfo.setId(null);
            spuInfoMapper.insertSelective(spuInfo);
        }

        /** 更新图片 */

        /** 删除原来图片 */
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuInfo.getId());

        spuImageMapper.delete(spuImage);

        /** 重新添加图片  */

        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if(spuImageList != null && spuImageList.size() > 0) {
            for (SpuImage spuImageToadd : spuImageList) {
                spuImageToadd.setSpuId(spuInfo.getId());
                spuImageMapper.insert(spuImageToadd);
            }
        }
        /** 删除原有属性 */
        SpuSaleAttr spuSaleAttrForDelete = new SpuSaleAttr();
        spuSaleAttrForDelete.setSpuId(spuInfo.getId());

        spuSaleAttrMapper.delete(spuSaleAttrForDelete);

        /** 删除原有属性值 */
        SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();
        spuSaleAttrValue.setSpuId(spuInfo.getId());

        spuSaleAttrValueMapper.delete(spuSaleAttrValue);
        /** 更新属性 */
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList != null && spuSaleAttrList.size() > 0) {
            for (SpuSaleAttr spuSaleAttrForAdd : spuSaleAttrList) {
                spuSaleAttrForAdd.setId(null);
                spuSaleAttrForAdd.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttrForAdd);

                /** 属性值 */
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttrForAdd.getSpuSaleAttrValueList();
                if(spuSaleAttrValueList != null && spuSaleAttrValueList.size() > 0){
                    for (SpuSaleAttrValue spuSaleAttrValueForAdd : spuSaleAttrValueList){
                        spuSaleAttrValueForAdd.setId(null);
                        spuSaleAttrValueForAdd.setSpuId(spuInfo.getId());
                        log.debug(spuSaleAttrForAdd.getId());

                     //   spuSaleAttrValueForAdd.setSaleAttrId(spuSaleAttrForAdd.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValueForAdd);
                    }
                }
            }
        }

    }

    @Override
    public List<SpuImage> spuImageList(String spuId) {

        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
       return spuImageMapper.select(spuImage);
    }

    @Override
    public List<BaseAttrInfo> attrInfoList(String cataLog3Id) {

        List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.attrInfoList(cataLog3Id);
        return baseAttrInfos;
    }

    @Override
    public List<SpuSaleAttr> selectSpuSaleAttrList(String spuId) {

           return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);

    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {

        if(skuInfo.getId() != null && skuInfo.getId().length() > 0){
            skuInfoMapper.updateByPrimaryKey(skuInfo);
        }else{
            skuInfo.setId(null);
            skuInfoMapper.insert(skuInfo);
        }

        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if(skuImageList != null && skuImageList.size() > 0){
            for (SkuImage skuImage : skuImageList){
                if(skuImage.getId() != null && skuImage.getId().length() == 0){
                    skuImage.setId(null);
                }
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insert(skuImage);
            }
        }

        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();

        if(skuAttrValueList != null && skuAttrValueList.size() > 0){
            for (SkuAttrValue skuAttrValue : skuAttrValueList){
                if(skuAttrValue.getId() != null && skuAttrValue.getId().length() == 0){
                    skuAttrValue.setId(null);
                }
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insert(skuAttrValue);

                List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
                if (skuSaleAttrValueList!=null && skuSaleAttrValueList.size()>0) {
                    for (SkuSaleAttrValue saleAttrValue : skuSaleAttrValueList) {
                        if (saleAttrValue.getId() != null && saleAttrValue.getId().length() == 0) {
                            saleAttrValue.setId(null);
                        }
                        // skuId
                        saleAttrValue.setSkuId(skuInfo.getId());
                        skuSaleAttrValueMapper.insertSelective(saleAttrValue);
                    }
                }
            }
        }
    }
}
