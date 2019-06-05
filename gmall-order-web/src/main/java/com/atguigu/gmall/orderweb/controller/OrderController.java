package com.atguigu.gmall.orderweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Reference
    private UserAddressService userAddressService;

    @RequestMapping("getAddressListByUserId")
    public List<UserAddress> getAddressListByUserId(String userId){
        List<UserAddress> allAddressByUserId = userAddressService.findAllAddressByUserId(userId);
        return allAddressByUserId;

    }
}
