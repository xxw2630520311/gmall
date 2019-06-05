package com.atguigu.gmall.testmapper.service;

import com.atguigu.gmall.testmapper.bean.UserInfo;

import java.util.List;

public interface UserService {

    /**
     * 查询所有
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    boolean addUserInfo(UserInfo userInfo);

    /**
     * 修改用户根据主键
     * @param id
     * @return
     */
    boolean UpdateUserById(String id);

    /**
     * 修改用户
     * @param userInfo
     * @return
     */
    boolean UpdateUserById(UserInfo userInfo);

    /**
     * 删除用户根据主键
     * @param id
     * @return
     */
    boolean delUserById(String id);

    /**
     * 删除用户
     * @param userInfo
     * @return
     */
    boolean delUserById(UserInfo userInfo);
}
