package com.atguigu.gmall.bean;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserAddress implements Serializable {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 获取主键自增 oracle GenerationType.AUTO
    private String id;
    @Column
    private String userAddress;
    @Column
    private String userId;
    @Column
    private String consignee;
    @Column
    private String phoneNum;
    @Column
    private String isDefault;

}
