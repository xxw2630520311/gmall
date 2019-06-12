package com.atguigu.gmall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class BaseAttrInfo implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 获取主键自增 oracle GenerationType.AUTO
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;


    @Transient   // 表示 希望该属性不会在数据表中产生字段，但又可以在程序中使用它。实体类有一个表中没有但可以使用的字段
    private List<BaseAttrValue> attrValueList;


}