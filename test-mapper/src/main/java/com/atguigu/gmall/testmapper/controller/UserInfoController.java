package com.atguigu.gmall.testmapper.controller;

import com.atguigu.gmall.testmapper.bean.UserInfo;
import com.atguigu.gmall.testmapper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping("findall")
    public List<UserInfo> findall(){
        return userService.findAll();
    }

    @RequestMapping("addUserInfo")
    public String addUser(){
        UserInfo userInfo = new UserInfo();
        userInfo.setName("测试添加");
        userInfo.setEmail("qq@qq.com");
        userInfo.setLoginName("测试");
        userInfo.setNickName("添加");
        boolean b = userService.addUserInfo(userInfo);
        if(b)
            return "ok";
        else
            return "error";
    }

    @RequestMapping("updUserById")
    public String updUserById(String id){
        boolean b = userService.UpdateUserById(id);
        if(b)
            return "ok";
        else
            return "error";
    }

    @RequestMapping("updUserByUserInfo")
    public String updUserByUserInfo(UserInfo userInfo){
        boolean b = userService.UpdateUserById(userInfo);
        if(b)
            return "ok";
        else
            return "error";
    }


    @RequestMapping("delUserById")
    public String delUserById(String id){
        boolean b = userService.delUserById(id);
        if(b)
            return "ok";
        else
            return "error";
    }

    @RequestMapping("delUserByUserInfo")
    public String delUserByUserInfo(UserInfo userInfo){
        boolean b = userService.delUserById(userInfo);
        if(b)
            return "ok";
        else
            return "error";
    }
}
